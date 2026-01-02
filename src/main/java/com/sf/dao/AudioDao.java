package com.sf.dao;

import com.sf.entity.audioEntity;

public interface AudioDao {
    // 根据视频ID查询音频信息
    audioEntity getAudioByVideoId(int videoId);
    
    // 保存音频信息到数据库
    boolean saveAudioInfo(audioEntity audio);
    
    // 检查视频是否已经有音频文件
    boolean hasAudioForVideo(int videoId);
}