package com.sf.tool;

import com.sf.entity.SupportEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupportRowMapperEntity implements RowMapper<SupportEntity> {
    @Override
    public SupportEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        SupportEntity supportEntity=new SupportEntity();
        supportEntity.setId(resultSet.getInt("id"));
        supportEntity.setUser_id(resultSet.getString("user_id"));
        supportEntity.setVideo_id(resultSet.getInt("video_id"));
        return supportEntity;

    }
}
