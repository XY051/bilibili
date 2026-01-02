package com.sf.tool;

import ws.schild.jave.Encoder;

public class FFmpegChecker {
    public static void main(String[] args) {
        checkFFmpegAvailability();
    }
    
    public static boolean checkFFmpegAvailability() {
        try {
            Encoder encoder = new Encoder();
            System.out.println("FFmpeg可用: 是");
            return true;
        } catch (Exception e) {
            System.out.println("FFmpeg不可用，错误: " + e.getMessage());
            System.out.println("FFmpeg可用: 否");
            e.printStackTrace();
            return false;
        }
    }
    
    public static void printSystemInfo() {
        System.out.println("操作系统: " + System.getProperty("os.name"));
        System.out.println("架构: " + System.getProperty("os.arch"));
        System.out.println("版本: " + System.getProperty("os.version"));
        System.out.println("Java版本: " + System.getProperty("java.version"));
        System.out.println("用户目录: " + System.getProperty("user.dir"));
        System.out.println("Java库路径: " + System.getProperty("java.library.path"));
    }
}