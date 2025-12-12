package com.sf.service.impl;

import com.sf.dao.impl.FollowDaoimpl;
import com.sf.entity.FollowEntity;
import com.sf.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowServiceimpl implements FollowService{
    @Autowired
    FollowDaoimpl followDaoimpl;
    @Override
    public boolean InsertFollow(FollowEntity followEntity) {
        return followDaoimpl.InsertFollow(followEntity);
    }

    @Override
    public List<FollowEntity> selectFollow(String follower, String followee) {
        return followDaoimpl.selectFollow(follower,followee);
    }

    @Override
    public List<FollowEntity> selectFollow_byFollower(String follower) {
        return followDaoimpl.selectFollow_byFollower(follower);
    }

    @Override
    public List<FollowEntity> selectFollow_byFollowee(String followee) {
        return followDaoimpl.selectFollow_byFollowee(followee);
    }

    @Override
    public boolean isFollowed(String follower, String followee) {
        List<FollowEntity> followEntityList=selectFollow(follower,followee);
        if(followEntityList!=null&&followEntityList.size()!=0&&followEntityList.get(0)!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean undoFollow(String follower, String followee) {
        return followDaoimpl.deletFollow(follower,followee);
    }
}
