package com.sf.service.impl;

import com.sf.dao.impl.VideoDaoimpl;
import com.sf.entity.VideoCheckEntity;
import com.sf.entity.videoEntity;
import com.sf.entity.videoPushEntity;
import com.sf.service.Videoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class Videoserviceimpl  implements Videoservice{
    @Autowired
    VideoDaoimpl videoDaoimpl;

    @Override
    public boolean insertVideo(videoEntity video) {
        boolean result= videoDaoimpl.InsertVideo(video);
        return result;

    }

    @Override
    public List<videoEntity> readVideoByVid(int vid) {
        List<videoEntity> videoList= videoDaoimpl.selectVideo_by_vid(vid);
        return videoList;
    }

    @Override
    public List<videoEntity> searchVideoByName(String name) {
        List<videoEntity> videoList =videoDaoimpl.selectVideo_by_name(name);
        return videoList;
    }

    @Override
    public boolean UpdateVideo(videoEntity video) {
        return videoDaoimpl.UpdateVideo(video);
    }

    @Override
    public List<videoEntity> selectVideo_by_user_id(String user_id) {
        return videoDaoimpl.selectVideo_by_user_id(user_id);
    }

    @Override
    public boolean supportPlusOne(int video_id) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            video.setSupportcount(video.getSupportcount()+1);
            UpdateVideo(video);
        }else{
            return false;
        }
        return true;
    }

    @Override
    public boolean supportSubOne(int video_id) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            video.setSupportcount(video.getSupportcount()-1);
            UpdateVideo(video);
        }else{
            return false;
        }
        return true;
    }

    @Override
    public boolean videoIscheck(int video_id) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            if(video.getIscheck()==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean videoIspass(int video_id) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            if(video.getIscheck()==1&&video.getIspass()==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean videocheek(int video_id,int pass_state) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            //video.setSupportcount(video.getSupportcount()+1);
            video.setIspass(pass_state);
            video.setIscheck(1);
            UpdateVideo(video);


        }else{
            return false;
        }
        return true;
    }

    @Override
    public List<videoEntity> selectCheckVideo() {
      List<videoEntity> videoEntityList=  videoDaoimpl.readCheckVideo();

        return videoEntityList;
    }

    @Override
    public boolean UpdateVideoCheck(int state, int video_id) {

        return videoDaoimpl.UpdateVideoCheck(state,video_id);
    }

    @Override
    public List<videoEntity> searchVideotj(String videocAtegory, int num) {
        return videoDaoimpl.searchVideotj(videocAtegory,num);
    }

    @Override
    public List<videoEntity> searchVideonew(String videocAtegory,int start,int end ) {
        return videoDaoimpl.searchVideonew(videocAtegory, start, end);
    }

    @Override
    public List<videoEntity> searchVidetoptj(int start, int end) {
        return videoDaoimpl.searchVidetoptj(start,end);
    }

    @Override
    public int videotypecount(int videotype) {
        return videoDaoimpl.videotypecount(videotype);
    }

    @Override
    public boolean lookTimesPlusOne(int video_id) {
        List<videoEntity> videoEntityList=readVideoByVid(video_id);
        if(videoEntityList!=null&&videoEntityList.size()==1&&videoEntityList.get(0)!=null){
            videoEntity video=videoEntityList.get(0);
            video.setVideolookTime(video.getVideolookTime()+1);
            UpdateVideo(video);
        }else{
            return false;
        }
        return true;
    }


}
