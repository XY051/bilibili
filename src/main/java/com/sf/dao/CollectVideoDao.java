package com.sf.dao;

import com.sf.entity.CollectVideoEnity;

import java.util.List;

public interface CollectVideoDao {
    public  boolean  InsertCollectVideo(CollectVideoEnity collectVideoEnity);
    public List<CollectVideoEnity> selectCollectVideo(String user_id, int video_id );
    public boolean deleteCollectVideo(String user_id,int video_id);
    public List<CollectVideoEnity> selectFollow_byUser_id(String user_id);
}
