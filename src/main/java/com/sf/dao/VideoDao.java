package com.sf.dao;



import com.sf.entity.videoEntity;

import java.util.List;

public interface VideoDao {
    public  boolean  InsertVideo(videoEntity video);
    public List<videoEntity> selectVideo_by_vid(int vid);
    public List<videoEntity> selectVideo_by_name(String name);
    public List<videoEntity> selectVideo_by_user_id(String user_id);
    public List<videoEntity> selectVideo_by_user_id_and_name(String user_id, String videoName);
    public  boolean UpdateVideo(videoEntity video);
    public List<videoEntity> readCheckVideo();
    public  boolean UpdateVideoCheck(int state,int video_id);
    public List<videoEntity> searchVideotj(String videocAtegory,int num);
    public List<videoEntity> searchVideonew(String videocAtegory,int start,int end );
    public List<videoEntity> searchVidetoptj(int start,int end );
    public int videotypecount(int videotype);
    public List<videoEntity> selectAllVideos();
    public List<videoEntity> selectAllVideosWithoutAudio();
    public videoEntity getVideoById(int videoId);
}
