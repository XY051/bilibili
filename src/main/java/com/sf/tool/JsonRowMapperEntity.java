package com.sf.tool;

import com.sf.entity.jsonEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonRowMapperEntity implements RowMapper<jsonEntity> {
    @Override
    public jsonEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        jsonEntity jsonEntity = new jsonEntity();
        jsonEntity.setJsonId(rs.getInt("json_id"));
        jsonEntity.setAudioId(rs.getInt("audio_id"));
        jsonEntity.setJsonName(rs.getString("json_name"));
        jsonEntity.setJsonPath(rs.getString("json_path"));
        jsonEntity.setJsonSize(rs.getLong("json_size"));
        jsonEntity.setCreateTime(rs.getString("create_time"));
        return jsonEntity;
    }
}