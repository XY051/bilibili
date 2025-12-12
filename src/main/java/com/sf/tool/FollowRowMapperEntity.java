package com.sf.tool;

import com.sf.entity.FollowEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowRowMapperEntity implements RowMapper<FollowEntity> {
    @Override
    public FollowEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        FollowEntity followEntity=new FollowEntity();
        followEntity.setId(resultSet.getInt("id"));
        followEntity.setFollowee(resultSet.getString("followee"));
        followEntity.setFollower(resultSet.getString("follower"));
        return followEntity;
    }
}
