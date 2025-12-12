package com.sf.service;

import com.sf.entity.FollowEntity;

import java.util.List;

public interface FollowService {
    public  boolean  InsertFollow(FollowEntity followEntity);
    public List<FollowEntity> selectFollow(String follower, String followee );
    public List<FollowEntity> selectFollow_byFollower(String follower);
    public List<FollowEntity> selectFollow_byFollowee(String followee);
    public boolean isFollowed(String follower, String followee);
    public  boolean undoFollow(String follower, String followee);
}
