package com.sf.service;



import com.sf.entity.VideoCheckEntity;
import com.sf.entity.videoEntity;
import com.sf.entity.videoPushEntity;

import java.util.List;

public interface Videoservice {
    public  boolean insertVideo(videoEntity video);
    public List<videoEntity> readVideoByVid(int vid);
    public List<videoEntity> searchVideoByName(String name);
    public  boolean UpdateVideo(videoEntity video);
    public List<videoEntity> selectVideo_by_user_id(String user_id);
    public List<videoEntity> selectVideo_by_user_id_and_name(String user_id, String videoName);
    public boolean supportPlusOne(int video_id);
    public boolean supportSubOne(int video_id);
    public boolean videoIscheck(int video_id);
    public boolean videoIspass(int video_id);
    public boolean videocheek(int video_id,int pass_state);
    public List<videoEntity> selectCheckVideo();
    public  boolean UpdateVideoCheck(int state,int video_id);
    public List<videoEntity> searchVideotj(String videocAtegory,int num);
    public List<videoEntity> searchVideonew(String videocAtegory,int start,int end);
    public List<videoEntity> searchVidetoptj(int start, int end);
    public int videotypecount(int videotype);
    public boolean lookTimesPlusOne(int video_id);
    public List<videoEntity> selectAllVideos();
}
