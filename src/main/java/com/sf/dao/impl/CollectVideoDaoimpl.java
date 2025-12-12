package com.sf.dao.impl;

import com.sf.dao.CollectVideoDao;
import com.sf.db.Data_jdbcTemplate;
import com.sf.entity.CollectVideoEnity;
import com.sf.tool.CollectVideoRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CollectVideoDaoimpl implements CollectVideoDao{

    @Autowired
    Data_jdbcTemplate jdbcTemplate;
    @Override
    public boolean InsertCollectVideo(CollectVideoEnity collectVideoEnity) {
        String insert_sql="insert into collectvideo(user_id,video_id) values(?,?)";
        int result=jdbcTemplate.getJdbcTemplate().update(insert_sql,new Object[]{collectVideoEnity.getUser_id(),collectVideoEnity.getVideo_id()});
        if(result==0)return  false;
        return true;
    }

    @Override
    public List<CollectVideoEnity> selectCollectVideo(String user_id, int video_id) {
        String select_sql="select * from  collectvideo where user_id=? && video_id=?";
        List<CollectVideoEnity> collectVideoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{user_id,video_id},new CollectVideoRowMapperEntity());
        return collectVideoList;
    }

    @Override
    public boolean deleteCollectVideo(String user_id, int video_id) {
        String sql = "delete from collectvideo where user_id = ? && video_id=?";
        int num = jdbcTemplate.getJdbcTemplate().update(sql,new Object[]{user_id,video_id});
        if(num>0)return true;
        return false;
    }

    @Override
    public List<CollectVideoEnity> selectFollow_byUser_id(String user_id) {
        String select_sql="select * from  collectvideo where user_id=?";
        List<CollectVideoEnity> followList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{user_id},new CollectVideoRowMapperEntity());
        return followList;
    }
}
