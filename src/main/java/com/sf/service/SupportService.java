package com.sf.service;

import com.sf.entity.SupportEntity;

import java.util.List;

public interface SupportService {
    public  boolean  InsertSupport(SupportEntity supportEntity);
    public List<SupportEntity> selectSupport(String user_id, int video_id );
    public boolean isSupported(String user_id,int video_id);
    public boolean undosupport(String user_id, int video_id);

}
