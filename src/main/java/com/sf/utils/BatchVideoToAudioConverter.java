package com.sf.utils;

import com.sf.tool.FFmpegChecker;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量视频转音频转换器
 * 用于将videolook目录下的所有视频文件转换为音频并保存到audio目录下
 */
public class BatchVideoToAudioConverter {
    
    // 支持的视频文件扩展名
    private static final String[] VIDEO_EXTENSIONS = {
        ".mp4", ".avi", ".mov", ".wmv", ".flv", ".mkv", ".webm", ".m4v", ".3gp", ".mpg", ".mpeg"
    };
    
    public static void main(String[] args) {
        System.out.println("开始批量视频转音频...");
        FFmpegChecker.printSystemInfo();
        
        // 检查FFmpeg可用性
        boolean isAvailable = FFmpegChecker.checkFFmpegAvailability();
        if (!isAvailable) {
            System.out.println("FFmpeg不可用，请检查安装和环境配置。");
            return;
        }
        
        // 执行批量转换
        convertAllVideosInDirectory();
    }
    
    /**
     * 转换videolook目录下的所有视频文件为音频
     */
    public static void convertAllVideosInDirectory() {
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
                return;
            }
        }
        
        // 创建音频输出目录（如果不存在）
        if (!audioDir.exists()) {
            if (audioDir.mkdirs()) {
                System.out.println("创建音频输出目录成功: " + audioDir.getAbsolutePath());
            } else {
                System.out.println("创建音频输出目录失败: " + audioDir.getAbsolutePath());
                return;
            }
        }
        
        System.out.println("视频目录: " + videoDir.getAbsolutePath());
        System.out.println("音频输出目录: " + audioDir.getAbsolutePath());
        
        // 获取目录中的所有视频文件
        File[] videoFiles = getVideoFiles(videoDir);
        
        if (videoFiles.length == 0) {
            System.out.println("在视频目录中未找到视频文件");
            return;
        }
        
        System.out.println("找到 " + videoFiles.length + " 个视频文件，开始转换...");
        
        // 转换每个视频文件
        for (File videoFile : videoFiles) {
            System.out.println("正在转换: " + videoFile.getName());
            convertVideoToAudio(videoFile, audioDir);
        }
        
        System.out.println("批量转换完成！");
    }
    
    /**
     * 获取目录中的所有视频文件
     */
    private static File[] getVideoFiles(File directory) {
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
    private static boolean isVideoFile(File file) {
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
    private static void convertVideoToAudio(File videoFile, File audioDir) {
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
            
        } catch (Exception e) {
            System.out.println("转换失败: " + videoFile.getName() + " - " + e.getMessage());
            System.out.println("错误类型: " + e.getClass().getName());
            
            // 获取更详细的错误信息
            Throwable cause = e.getCause();
            if (cause != null) {
                System.out.println("根本原因: " + cause.getMessage());
            }
            
            e.printStackTrace();
        }
    }
    
    /**
     * 获取文件名（不含扩展名）
     */
    private static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}