package com.sf.dao.impl;

import com.sf.dao.JsonDao;
import com.sf.db.Data_jdbcTemplate;
import com.sf.entity.jsonEntity;
import com.sf.tool.JsonRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JsonDaoimpl implements JsonDao {

    @Autowired
    private Data_jdbcTemplate data_jdbcTemplate;

    @Override
    public boolean saveJsonInfo(jsonEntity jsonEntity) {
        JdbcTemplate jdbcTemplate = data_jdbcTemplate.getJdbcTemplate();
        String sql = "INSERT INTO json (audio_id, json_name, json_path, json_size) VALUES (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, 
            jsonEntity.getAudioId(), 
            jsonEntity.getJsonName(), 
            jsonEntity.getJsonPath(), 
            jsonEntity.getJsonSize());
        return result > 0;
    }

    @Override
    public jsonEntity getJsonByAudioId(int audioId) {
        JdbcTemplate jdbcTemplate = data_jdbcTemplate.getJdbcTemplate();
        String sql = "SELECT * FROM json WHERE audio_id = ?";
        try {
            List<jsonEntity> jsonList = jdbcTemplate.query(sql, new JsonRowMapperEntity(), audioId);
            if (jsonList != null && !jsonList.isEmpty()) {
                return jsonList.get(0);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
    }

    @Override
    public jsonEntity getJsonByJsonPath(String jsonPath) {
        JdbcTemplate jdbcTemplate = data_jdbcTemplate.getJdbcTemplate();
        String sql = "SELECT * FROM json WHERE json_path = ?";
        try {
            List<jsonEntity> jsonList = jdbcTemplate.query(sql, new JsonRowMapperEntity(), jsonPath);
            if (jsonList != null && !jsonList.isEmpty()) {
                return jsonList.get(0);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
    }
}