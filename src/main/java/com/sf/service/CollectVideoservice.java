package com.sf.service;

import com.sf.entity.CollectVideoEnity;

import java.util.List;

public interface CollectVideoservice {

    public  boolean  InsertCollected(CollectVideoEnity collectVideoEnity);
    public List<CollectVideoEnity> selectCollectVideo(String user_id, int video_id );
    public boolean isCollected(String user_id,int video_id);
    public boolean undoCollect(String user_id, int video_id);
    public List<CollectVideoEnity> selectFollow_byUser_id(String user_id);
}
