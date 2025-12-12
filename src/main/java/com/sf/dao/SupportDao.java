package com.sf.dao;

import com.sf.entity.SupportEntity;

import java.util.List;

public interface SupportDao {
    public  boolean  InsertSupport(SupportEntity supportEntity);
    public List<SupportEntity> selectSupport(String user_id,int video_id );
    public boolean deleteSupport(String user_id,int video_id);
}
