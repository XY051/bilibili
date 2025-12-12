package com.sf.service.impl;

import com.sf.dao.impl.CollectVideoDaoimpl;
import com.sf.entity.CollectVideoEnity;
import com.sf.service.CollectVideoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CollectVideoServiceimpl implements CollectVideoservice {

    @Autowired
    private CollectVideoDaoimpl collectVideoDaoimpl;

    @Override
    public boolean InsertCollected(CollectVideoEnity collectVideoEnity) {
        return collectVideoDaoimpl.InsertCollectVideo(collectVideoEnity);
    }

    @Override
    public List<CollectVideoEnity> selectCollectVideo(String user_id, int video_id) {
        return collectVideoDaoimpl.selectCollectVideo(user_id,video_id);
    }

    @Override
    public boolean isCollected(String user_id, int video_id) {
        List<CollectVideoEnity> collectVideoEnities =selectCollectVideo(user_id, video_id);
        if (collectVideoEnities != null && collectVideoEnities.size() == 1 && collectVideoEnities.get(0) != null) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean undoCollect(String user_id, int video_id) {
        if(collectVideoDaoimpl.deleteCollectVideo(user_id,video_id)){
            return true;
        }
        return false;
    }

    @Override
    public List<CollectVideoEnity> selectFollow_byUser_id(String user_id) {
        return  collectVideoDaoimpl.selectFollow_byUser_id(user_id);
    }
}
