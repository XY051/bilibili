package com.sf.service.impl;

import com.sf.dao.impl.SupportDaoimpl;
import com.sf.dao.impl.VideoDaoimpl;
import com.sf.entity.SupportEntity;
import com.sf.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupportServiceimpl implements SupportService {
    @Autowired
    SupportDaoimpl supportDaoimpl;
    @Autowired
    VideoDaoimpl videoDaoimpl;

    @Override
    public boolean InsertSupport(SupportEntity supportEntity) {

        return supportDaoimpl.InsertSupport(supportEntity);
    }

    @Override
    public List<SupportEntity> selectSupport(String user_id, int video_id) {
        return supportDaoimpl.selectSupport(user_id, video_id);
    }

    @Override
    public boolean isSupported(String user_id, int video_id) {
        List<SupportEntity> supportEntities = selectSupport(user_id, video_id);
        if (supportEntities != null && supportEntities.size() == 1 && supportEntities.get(0) != null) {
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean undosupport(String user_id, int video_id) {
       if(supportDaoimpl.deleteSupport(user_id,video_id)){
           return true;
       }
        return false;
    }

}
