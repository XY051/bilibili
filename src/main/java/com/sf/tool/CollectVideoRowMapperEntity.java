package com.sf.tool;

import com.sf.entity.CollectVideoEnity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectVideoRowMapperEntity implements RowMapper<CollectVideoEnity> {

    @Override
    public CollectVideoEnity mapRow(ResultSet resultSet, int i) throws SQLException {
        CollectVideoEnity collectVideoEnity=new CollectVideoEnity();
        collectVideoEnity.setId(resultSet.getInt("id"));
        collectVideoEnity.setUser_id(resultSet.getString("user_id"));
        collectVideoEnity.setVideo_id(resultSet.getInt("video_id"));
        return collectVideoEnity;
    }
}
