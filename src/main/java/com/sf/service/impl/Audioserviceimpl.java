package com.sf.service.impl;

import com.sf.dao.AudioDao;
import com.sf.entity.audioEntity;
import com.sf.service.Audioservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;

@Service
public class Audioserviceimpl implements Audioservice {

    @Autowired
    private AudioDao audioDao;

    public void setAudioDao(AudioDao audioDao) {
        this.audioDao = audioDao;
    }

    @Override
    public boolean extractAudio(int videoId, String videoPath) {
        try {
            System.out.println("开始提取音频，视频ID: " + videoId + ", 视频路径: " + videoPath);
            
            // 检查输入参数
            if (videoPath == null || videoPath.isEmpty()) {
                System.out.println("视频路径为空，无法提取音频");
                return false;
            }
            
            // 检查FFmpeg是否可用
            try {
                Encoder encoder = new Encoder();
                System.out.println("FFmpeg可用，开始转换...");
            } catch (Exception e) {
                System.out.println("FFmpeg不可用，请检查JAVE库配置: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

            // 确定音频文件路径 - 使用绝对路径
            String videoFileName = new File(videoPath).getName();
            String audioFileName = videoFileName.substring(0, videoFileName.lastIndexOf('.')) + ".mp3";
            
            // 直接使用传入的完整路径，不再尝试构建其他路径
            File source = new File(videoPath);
            
            // 检查源视频文件是否存在
            if (!source.exists()) {
                System.out.println("视频文件不存在: " + source.getAbsolutePath());
                System.out.println("尝试的完整路径: " + videoPath);
                return false;
            }
            
            System.out.println("找到视频文件: " + source.getAbsolutePath());
            
            // 构建音频文件路径 - 使用项目绝对路径以确保可靠性
            String projectPath = "C:/Course/Third/JavaMvc/bilibili/bilibili"; // 项目绝对路径
            String audioPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + 
                       "webapp" + File.separator + "static" + File.separator + "audio" + File.separator + audioFileName;
            
            File target = new File(audioPath);

            // 确保目标目录存在
            File targetDir = target.getParentFile();
            if (targetDir != null && !targetDir.exists()) {
                boolean created = targetDir.mkdirs();
                if (!created) {
                    System.out.println("无法创建音频目录: " + targetDir.getAbsolutePath());
                    return false;
                }
                System.out.println("已创建音频目录: " + targetDir.getAbsolutePath());
            }
            
            System.out.println("视频源路径: " + source.getAbsolutePath());
            System.out.println("音频目标路径: " + target.getAbsolutePath());
            
            System.out.println("源文件大小: " + source.length() + " 字节");

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame"); // 使用MP3编码

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            
            System.out.println("音频转换完成，开始保存到数据库");

            // 检查音频文件是否成功生成
            if (!target.exists() || target.length() == 0) {
                System.out.println("音频文件未成功生成或文件大小为0: " + target.getAbsolutePath());
                return false;
            }
            
            // 保存音频记录到数据库
            audioEntity audioEntity = new audioEntity();
            audioEntity.setVideo_id(videoId);
            audioEntity.setAudio_name(audioFileName);
            // 使用相对于web应用的路径存储到数据库
            audioEntity.setAudio_path("/static/audio/" + audioFileName);
            audioEntity.setAudio_size(target.length());
            audioEntity.setCreate_time(new java.util.Date());

            boolean saved = audioDao.saveAudioInfo(audioEntity);
            if (saved) {
                System.out.println("音频提取和保存到数据库成功: " + target.getAbsolutePath());
            } else {
                System.out.println("音频提取成功但数据库保存失败: " + target.getAbsolutePath());
                // 如果数据库保存失败，删除已生成的音频文件
                if (target.exists()) {
                    target.delete();
                }
            }
            System.out.println("数据库保存结果: " + saved);
            return saved;
        } catch (EncoderException e) {
            System.out.println("音频转换失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("音频提取过程中发生未知错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hasAudioForVideo(int videoId) {
        audioEntity existingAudio = audioDao.getAudioByVideoId(videoId);
        System.out.println("检查视频 " + videoId + " 是否有音频: " + (existingAudio != null));
        return existingAudio != null;
    }

    public void extractAudio(String videoPath, String audioPath) throws EncoderException, IOException {
        // 检查FFmpeg是否可用
        try {
            Encoder encoder = new Encoder();
            System.out.println("FFmpeg可用，开始转换...");
        } catch (Exception e) {
            System.out.println("FFmpeg不可用，请检查JAVE库配置: " + e.getMessage());
            throw e;
        }

        File source = new File(videoPath);
        File target = new File(audioPath);

        // 确保目标目录存在
        File targetDir = target.getParentFile();
        if (targetDir != null && !targetDir.exists()) {
            targetDir.mkdirs();
        }

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame"); // 使用MP3编码

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp3");
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attrs);
    }

    public boolean hasAudioExtracted(int videoId) {
        audioEntity existingAudio = audioDao.getAudioByVideoId(videoId);
        return existingAudio != null;
    }

    public void saveAudioRecord(int videoId, String audioPath) {
        File audioFile = new File(audioPath);
        
        // 只有当音频文件实际存在时才更新或创建数据库记录
        if (!audioFile.exists()) {
            System.out.println("音频文件不存在，无法创建或更新数据库记录: " + audioPath);
            return;
        }
        
        // 检查是否已存在记录
        audioEntity existingAudio = audioDao.getAudioByVideoId(videoId);
        if (existingAudio != null) {
            // 更新现有记录
            existingAudio.setAudio_path(audioPath);
            existingAudio.setAudio_name(audioFile.getName());
            existingAudio.setAudio_size(audioFile.length());
            // 这里需要删除旧记录然后插入新记录，或者需要在AudioDao中添加更新方法
            // 由于没有update方法，我们暂时简单处理，实际上应该在AudioDao中添加更新方法
            audioDao.saveAudioInfo(existingAudio);
        } else {
            // 创建新记录
            audioEntity audio = new audioEntity();
            audio.setVideo_id(videoId);
            audio.setAudio_path(audioPath);
            audio.setAudio_name(audioFile.getName());
            audio.setAudio_size(audioFile.length());
            audio.setCreate_time(new java.util.Date());
            audioDao.saveAudioInfo(audio);
        }
    }

    @Override
    public audioEntity getAudioByVideoId(int videoId) {
        return audioDao.getAudioByVideoId(videoId);
    }

    @Override
    public boolean saveAudioInfo(audioEntity audio) {
        return audioDao.saveAudioInfo(audio);
    }
}