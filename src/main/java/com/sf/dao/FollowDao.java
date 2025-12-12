package com.sf.dao;

import com.sf.entity.FollowEntity;

import java.util.List;

public interface FollowDao {
    public  boolean  InsertFollow(FollowEntity followEntity);
    public List<FollowEntity> selectFollow(String follower, String followee );
    public List<FollowEntity> selectFollow_byFollower(String follower);
    public List<FollowEntity> selectFollow_byFollowee(String followee);
    public boolean deletFollow(String follower, String followee );
}
