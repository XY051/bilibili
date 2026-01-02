package com.sf.service.impl;

import com.sf.dao.AudioDao;
import com.sf.entity.audioEntity;
import com.sf.service.BatchAudioService;
import com.sf.tool.FFmpegChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchAudioServiceImpl implements BatchAudioService {

    @Autowired
    private AudioDao audioDao;

    // 支持的视频文件扩展名
    private static final String[] VIDEO_EXTENSIONS = {
        ".mp4", ".avi", ".mov", ".wmv", ".flv", ".mkv", ".webm", ".m4v", ".3gp", ".mpg", ".mpeg"
    };

    @Override
    public int batchExtractAudioFromVideos() {
        System.out.println("开始批量提取音频...");
        FFmpegChecker.printSystemInfo();

        // 检查FFmpeg可用性
        boolean isAvailable = FFmpegChecker.checkFFmpegAvailability();
        if (!isAvailable) {
            System.out.println("FFmpeg不可用，请检查安装和环境配置。");
            return 0;
        }

        // 定义视频目录和音频输出目录
        File videoDir = new File("src/main/webapp/static/videolook");
        File audioDir = new File("src/main/webapp/static/audio");

        // 检查视频目录是否存在
        if (!videoDir.exists() || !videoDir.isDirectory()) {
            System.out.println("视频目录不存在: " + videoDir.getAbsolutePath());
            // 尝试其他可能的路径
            videoDir = new File("webapp/static/videolook");
            if (!videoDir.exists() || !videoDir.isDirectory()) {
                System.out.println("备选视频目录也不存在: " + videoDir.getAbsolutePath());
                return 0;
            }
        }

        // 创建音频输出目录（如果不存在）
        if (!audioDir.exists()) {
            if (audioDir.mkdirs()) {
                System.out.println("创建音频输出目录成功: " + audioDir.getAbsolutePath());
            } else {
                System.out.println("创建音频输出目录失败: " + audioDir.getAbsolutePath());
                return 0;
            }
        }

        System.out.println("视频目录: " + videoDir.getAbsolutePath());
        System.out.println("音频输出目录: " + audioDir.getAbsolutePath());

        // 获取目录中的所有视频文件
        File[] videoFiles = getVideoFiles(videoDir);

        if (videoFiles.length == 0) {
            System.out.println("在视频目录中未找到视频文件");
            return 0;
        }

        System.out.println("找到 " + videoFiles.length + " 个视频文件，开始转换...");

        int successCount = 0;
        // 转换每个视频文件
        for (File videoFile : videoFiles) {
            System.out.println("正在转换: " + videoFile.getName());
            if (convertVideoToAudio(videoFile, audioDir)) {
                successCount++;
            }
        }

        System.out.println("批量转换完成！成功转换 " + successCount + " 个文件");
        return successCount;
    }

    /**
     * 获取目录中的所有视频文件
     */
    private File[] getVideoFiles(File directory) {
        File[] allFiles = directory.listFiles();
        if (allFiles == null) {
            return new File[0];
        }

        List<File> videoFiles = new ArrayList<>();
        for (File file : allFiles) {
            if (isVideoFile(file)) {
                videoFiles.add(file);
            }
        }

        return videoFiles.toArray(new File[0]);
    }

    /**
     * 判断文件是否为视频文件
     */
    private boolean isVideoFile(File file) {
        if (!file.isFile()) {
            return false;
        }

        String fileName = file.getName().toLowerCase();
        for (String extension : VIDEO_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将单个视频文件转换为音频
     */
    private boolean convertVideoToAudio(File videoFile, File audioDir) {
        try {
            // 创建Encoder对象
            Encoder encoder = new Encoder();

            // 创建多媒体对象
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);

            // 配置音频属性
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000); // 128 kbps
            audio.setChannels(2);     // 立体声
            audio.setSamplingRate(44100); // 44.1 kHz

            // 配置编码属性
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setAudioAttributes(audio);
            attrs.setOutputFormat("mp3");

            // 生成输出音频文件名（与视频文件同名，扩展名改为.mp3）
            String audioFileName = getFileNameWithoutExtension(videoFile.getName()) + ".mp3";
            File audioFile = new File(audioDir, audioFileName);

            System.out.println("输出音频文件: " + audioFile.getAbsolutePath());

            // 执行转换
            encoder.encode(multimediaObject, audioFile, attrs);

            System.out.println("转换成功: " + videoFile.getName() + " -> " + audioFileName);
            System.out.println("输出文件大小: " + audioFile.length() + " 字节");

            // 检查音频文件是否成功生成
            if (!audioFile.exists() || audioFile.length() == 0) {
                System.out.println("音频文件未成功生成或文件大小为0: " + audioFile.getAbsolutePath());
                return false;
            }
            
            // 保存音频记录到数据库
            audioEntity audioEntity = new audioEntity();
            audioEntity.setVideo_id(0); // 没有对应的视频ID，因为这是批量处理
            audioEntity.setAudio_name(audioFileName);
            // 使用相对于web应用的路径存储到数据库
            audioEntity.setAudio_path("/static/audio/" + audioFileName);
            audioEntity.setAudio_size(audioFile.length());
            audioEntity.setCreate_time(new java.util.Date());

            boolean saved = audioDao.saveAudioInfo(audioEntity);
            System.out.println("音频信息保存到数据库: " + (saved ? "成功" : "失败"));

            return saved;

        } catch (Exception e) {
            System.out.println("转换失败: " + videoFile.getName() + " - " + e.getMessage());
            System.out.println("错误类型: " + e.getClass().getName());

            // 获取更详细的错误信息
            Throwable cause = e.getCause();
            if (cause != null) {
                System.out.println("根本原因: " + cause.getMessage());
            }

            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件名（不含扩展名）
     */
    private String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}