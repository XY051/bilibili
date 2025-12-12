package com.sf.dao.impl;

import com.sf.dao.FollowDao;
import com.sf.db.Data_jdbcTemplate;
import com.sf.entity.FollowEntity;
import com.sf.tool.FollowRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class FollowDaoimpl implements FollowDao {
    @Autowired
    Data_jdbcTemplate jdbcTemplate;
    @Override
    public boolean InsertFollow(FollowEntity followEntity) {
        String insert_sql="insert into follow(follower,followee) values(?,?)";
        int result=jdbcTemplate.getJdbcTemplate().update(insert_sql,new Object[]{followEntity.getFollower(),followEntity.getFollowee()});
        if(result==0)return  false;
        return true;
    }

    @Override
    public List<FollowEntity> selectFollow(String follower, String followee) {
        String select_sql="select * from  follow where follower=? && followee=?";
        List<FollowEntity> followList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{follower,followee},new FollowRowMapperEntity());
        return followList;
    }

    @Override
    public List<FollowEntity> selectFollow_byFollower(String follower) {
        String select_sql="select * from  follow where follower=?";
        List<FollowEntity> followList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{follower},new FollowRowMapperEntity());
        return followList;
    }

    @Override
    public List<FollowEntity> selectFollow_byFollowee(String followee) {
        String select_sql="select * from  follow where followee=?";
        List<FollowEntity> followList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{followee},new FollowRowMapperEntity());
        return followList;
    }

    @Override
    public boolean deletFollow(String follower, String followee) {

        String sql = "delete from follow where follower = ? && followee=?";
        int num = jdbcTemplate.getJdbcTemplate().update(sql,new Object[]{follower,followee});
        if(num>0)return true;
        return false;

    }
}
