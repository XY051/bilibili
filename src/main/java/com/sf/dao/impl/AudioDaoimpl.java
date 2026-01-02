package com.sf.dao.impl;

import com.sf.dao.AudioDao;
import com.sf.entity.audioEntity;
import com.sf.tool.AudioRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AudioDaoimpl implements AudioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public audioEntity getAudioByVideoId(int videoId) {
        String sql = "SELECT * FROM audio WHERE video_id = ?";
        List<audioEntity> audioList = jdbcTemplate.query(sql, new Object[]{videoId}, new AudioRowMapperEntity());
        if (audioList != null && !audioList.isEmpty()) {
            return audioList.get(0);
        }
        return null;
    }

    @Override
    public boolean saveAudioInfo(audioEntity audio) {
        String sql = "INSERT INTO audio (video_id, audio_name, audio_path, audio_size, audio_duration, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            int result = jdbcTemplate.update(sql, 
                audio.getVideo_id(), 
                audio.getAudio_name(), 
                audio.getAudio_path(), 
                audio.getAudio_size(), 
                audio.getAudio_duration(), 
                audio.getCreate_time());
            return result > 0;
        } catch (Exception e) {
            System.err.println("保存音频信息失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hasAudioForVideo(int videoId) {
        String sql = "SELECT COUNT(*) FROM audio WHERE video_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, videoId);
        return count > 0;
    }
}