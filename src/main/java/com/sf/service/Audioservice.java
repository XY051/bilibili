package com.sf.service;

import com.sf.entity.audioEntity;

public interface Audioservice {
    // 根据视频ID查询音频信息
    audioEntity getAudioByVideoId(int videoId);
    
    // 保存音频信息到数据库
    boolean saveAudioInfo(audioEntity audio);
    
    // 检查视频是否已经有音频文件
    boolean hasAudioForVideo(int videoId);
    
    // 提取视频音频
    boolean extractAudio(int videoId, String videoPath);
}