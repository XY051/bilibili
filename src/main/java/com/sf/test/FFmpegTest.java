package com.sf.test;

import com.sf.tool.FFmpegChecker;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;

/**
 * FFmpeg功能测试类
 */
public class FFmpegTest {
    
    public static void main(String[] args) {
        System.out.println("开始测试FFmpeg功能...");
        FFmpegChecker.printSystemInfo();
        
        // 测试FFmpeg可用性
        boolean isAvailable = FFmpegChecker.checkFFmpegAvailability();
        if (!isAvailable) {
            System.out.println("FFmpeg不可用，请检查安装和环境配置。");
            return;
        }
        
        // 运行基本功能测试
        testBasicFFmpegFunctionality();
        
        // 测试音视频转换功能
        testVideoToAudioConversion();
        
        // 创建一个测试视频文件（如果不存在的话）
        createTestVideoIfNotExists();
        
        // 如果有测试视频文件，进行转换测试
        testVideoToAudioConversionWithTestFile();
    }
    
    /**
     * 测试FFmpeg基本功能
     */
    public static void testBasicFFmpegFunctionality() {
        System.out.println("\n--- 测试FFmpeg基本功能 ---");
        try {
            Encoder encoder = new Encoder();
            System.out.println("Encoder对象创建成功");
            
            // 尝试获取一些基本信息
            System.out.println("FFmpeg基本功能测试通过");
        } catch (Exception e) {
            System.out.println("FFmpeg基本功能测试失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 测试视频转音频功能（如果存在测试视频文件）
     */
    public static void testVideoToAudioConversion() {
        System.out.println("\n--- 测试视频转音频功能 ---");
        
        // 检查是否存在测试视频文件
        String testVideoPath = "test_video.mp4"; // 假设存在一个测试视频
        File testVideoFile = new File(testVideoPath);
        
        if (testVideoFile.exists()) {
            try {
                System.out.println("找到测试视频文件: " + testVideoFile.getAbsolutePath());
                
                // 创建Encoder对象
                Encoder encoder = new Encoder();
                
                // 创建多媒体对象
                MultimediaObject multimediaObject = new MultimediaObject(testVideoFile);
                
                // 配置音频属性
                AudioAttributes audio = new AudioAttributes();
                audio.setCodec("libmp3lame");
                audio.setBitRate(128000);
                audio.setChannels(2);
                audio.setSamplingRate(44100);
                
                // 配置编码属性
                EncodingAttributes attrs = new EncodingAttributes();
                attrs.setAudioAttributes(audio);
                attrs.setOutputFormat("mp3");
                
                // 输出文件
                File audioFile = new File("output_audio.mp3");
                
                // 执行转换
                encoder.encode(multimediaObject, audioFile, attrs);
                
                System.out.println("视频转音频成功: " + audioFile.getAbsolutePath());
                
            } catch (Exception e) {
                System.out.println("视频转音频失败: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("未找到测试视频文件 '" + testVideoPath + "', 跳过转换测试");
            System.out.println("您可以添加一个测试视频文件来完整测试转换功能");
        }
    }
    
    /**
     * 创建测试视频文件（如果不存在）
     */
    public static void createTestVideoIfNotExists() {
        System.out.println("\n--- 检查测试视频文件 ---");
        File testVideo = new File("test_video.mp4");
        if (!testVideo.exists()) {
            System.out.println("未找到测试视频文件 'test_video.mp4'");
            System.out.println("请添加一个测试视频文件以进行完整的转换测试");
        } else {
            System.out.println("测试视频文件存在，大小: " + testVideo.length() + " 字节");
        }
    }
    
    /**
     * 使用测试文件进行视频转音频转换
     */
    public static void testVideoToAudioConversionWithTestFile() {
        System.out.println("\n--- 使用测试文件进行视频转音频转换 ---");
        
        // 查找项目中的视频文件作为测试
        String[] possibleVideoFiles = {
            "test_video.mp4",
            "src/main/webapp/videos/test.mp4",
            "src/main/webapp/upload/test.mp4",
            "webapp/videos/test.mp4",
            "src/main/webapp/static/videolook/1765891112285_test7-1.mp4"  // 添加您遇到问题的视频文件路径
        };
        
        File videoFile = null;
        String videoPath = null;
        
        for (String path : possibleVideoFiles) {
            File file = new File(path);
            if (file.exists()) {
                videoFile = file;
                videoPath = path;
                break;
            }
        }
        
        if (videoFile != null) {
            try {
                System.out.println("使用视频文件: " + videoFile.getAbsolutePath());
                System.out.println("文件大小: " + videoFile.length() + " 字节");
                
                // 创建Encoder对象
                Encoder encoder = new Encoder();
                
                // 创建多媒体对象
                MultimediaObject multimediaObject = new MultimediaObject(videoFile);
                
                // 配置音频属性
                AudioAttributes audio = new AudioAttributes();
                audio.setCodec("libmp3lame");
                audio.setBitRate(128000);
                audio.setChannels(2);
                audio.setSamplingRate(44100);
                
                // 配置编码属性
                EncodingAttributes attrs = new EncodingAttributes();
                attrs.setAudioAttributes(audio);
                attrs.setOutputFormat("mp3");
                
                // 输出文件
                String outputFileName = "output_" + System.currentTimeMillis() + ".mp3";
                File audioFile = new File(outputFileName);
                
                System.out.println("开始转换视频到音频...");
                System.out.println("输出文件: " + audioFile.getAbsolutePath());
                
                // 执行转换
                encoder.encode(multimediaObject, audioFile, attrs);
                
                System.out.println("视频转音频成功: " + audioFile.getAbsolutePath());
                System.out.println("输出文件大小: " + audioFile.length() + " 字节");
                
            } catch (Exception e) {
                System.out.println("视频转音频失败: " + e.getMessage());
                System.out.println("错误类型: " + e.getClass().getName());
                
                // 获取更详细的错误信息
                Throwable cause = e.getCause();
                if (cause != null) {
                    System.out.println("根本原因: " + cause.getMessage());
                }
                
                // 检查视频文件是否包含音频流
                System.out.println("\n检查视频文件信息...");
                checkVideoInfo(videoFile);
                
                // 尝试使用更简单的编码参数
                System.out.println("\n尝试使用更简单的编码参数...");
                trySimpleEncoding(videoFile);
                
                e.printStackTrace();
            }
        } else {
            System.out.println("未找到任何可用的视频文件进行测试");
            System.out.println("请在项目中添加一个视频文件，例如: test_video.mp4");
        }
    }
    
    /**
     * 尝试使用更简单的编码参数进行转换
     */
    private static void trySimpleEncoding(File videoFile) {
        try {
            System.out.println("尝试使用简化参数进行转换...");
            
            // 创建Encoder对象
            Encoder encoder = new Encoder();
            
            // 创建多媒体对象
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);
            
            // 使用更简单的音频属性
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            
            // 配置编码属性
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setAudioAttributes(audio);
            attrs.setOutputFormat("mp3");
            
            // 输出文件
            String outputFileName = "simple_output_" + System.currentTimeMillis() + ".mp3";
            File audioFile = new File(outputFileName);
            
            System.out.println("使用简化参数转换: " + audioFile.getAbsolutePath());
            
            // 执行转换
            encoder.encode(multimediaObject, audioFile, attrs);
            
            System.out.println("简化参数转换成功: " + audioFile.getAbsolutePath());
            
        } catch (Exception e2) {
            System.out.println("简化参数转换也失败了: " + e2.getMessage());
            
            // 最后尝试检查视频文件是否可以被JAVE读取
            try {
                System.out.println("尝试获取视频信息...");
                MultimediaObject multimediaObject = new MultimediaObject(videoFile);
                System.out.println("视频文件可以被JAVE读取");
            } catch (Exception e3) {
                System.out.println("无法读取视频文件: " + e3.getMessage());
                System.out.println("可能是视频文件格式不支持或文件已损坏");
            }
        }
    }
    
    /**
     * 检查视频文件信息
     */
    private static void checkVideoInfo(File videoFile) {
        try {
            System.out.println("正在分析视频文件: " + videoFile.getName());
            System.out.println("文件路径: " + videoFile.getAbsolutePath());
            System.out.println("文件大小: " + videoFile.length() + " 字节");
            
            // 使用JAVE库尝试获取多媒体信息
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);
            // 注意：JAVE 3.x版本可能不直接提供获取流信息的方法
            System.out.println("视频文件可以被JAVE库读取");
            System.out.println("提示：如果视频没有音频轨道，无法提取音频");
            
        } catch (Exception e) {
            System.out.println("分析视频文件失败: " + e.getMessage());
        }
    }
}