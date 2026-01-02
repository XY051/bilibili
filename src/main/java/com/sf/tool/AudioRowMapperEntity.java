package com.sf.tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.sf.entity.audioEntity;

//音频信息映射
public class AudioRowMapperEntity implements RowMapper<audioEntity> {

    @Override
    public audioEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        audioEntity audio = new audioEntity();
        audio.setAudio_id(rs.getInt("audio_id"));
        audio.setVideo_id(rs.getInt("video_id"));
        audio.setAudio_name(rs.getString("audio_name"));
        audio.setAudio_path(rs.getString("audio_path"));
        audio.setAudio_size(rs.getLong("audio_size"));
        audio.setAudio_duration(rs.getString("audio_duration"));
        audio.setCreate_time(new Date(rs.getTimestamp("create_time").getTime()));
        return audio;
    }
}