package com.sf.dao.impl;

import com.sf.dao.SupportDao;
import com.sf.db.Data_jdbcTemplate;
import com.sf.entity.SupportEntity;
import com.sf.tool.SupportRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SupportDaoimpl implements SupportDao {
    @Autowired
    Data_jdbcTemplate jdbcTemplate;
    @Override
    public boolean InsertSupport(SupportEntity supportEntity) {
        String insert_sql="insert into support(user_id,video_id) values(?,?)";
        int result=jdbcTemplate.getJdbcTemplate().update(insert_sql,new Object[]{supportEntity.getUser_id(),supportEntity.getVideo_id()});
        if(result==0)return  false;
        return true;

    }

    @Override
    public List<SupportEntity> selectSupport(String user_id, int video_id) {
        String select_sql="select * from  support where user_id=? && video_id=?";
        List<SupportEntity> supportList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{user_id,video_id},new SupportRowMapperEntity());
        return supportList;
    }

    @Override
    public boolean deleteSupport(String user_id,int video_id) {
        String sql = "delete from support where user_id = ? && video_id=?";
        int num = jdbcTemplate.getJdbcTemplate().update(sql,new Object[]{user_id,video_id});
        if(num>0)return true;
        return false;
    }


}
