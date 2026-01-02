package com.sf.dao.impl;

import com.sf.dao.VideoDao;
import com.sf.entity.videoEntity;
import com.sf.db.Data_jdbcTemplate;
import com.sf.tool.VideoRowMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VideoDaoimpl implements VideoDao {
    @Override
    public boolean InsertVideo(videoEntity video) {
        String insert_sql="insert into video(videoName,videoImage,videoAddress,videocAtegory,user_id,videodetail,videoTime) values(?,?,?,?,?,?,?)";
        int result=jdbcTemplate.getJdbcTemplate().update(insert_sql,new Object[]{video.getVideoName(),video.
                getVideoImage(),video.getVideoAddress(),video.getVideocAtegory(),video.getUser_id(),video.getVideodetail(),video.getVideoTime()});
        if(result==0)return  false;
        return true;


    }
    @Override
    public  boolean UpdateVideo(videoEntity video){
        String updatesql="update video set ispass=? ,supportcount=?,ischeck=?,videolookTime=? where videoID="+video.getVideoID();
        int what=jdbcTemplate.getJdbcTemplate().update(updatesql,new Object[]{video.getIspass(),video.getSupportcount(),video.getIscheck(),video.getVideolookTime()});
        if(what==0)return false;
        return true;
    }

    @Override
    public List<videoEntity> readCheckVideo() {
        String select_sql="select * from  video where ischeck=?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{0},new VideoRowMapperEntity());
        return videoList;
    }

    @Override
    public boolean UpdateVideoCheck(int state,int video_id) {
        String updatesql="update video set ispass=?,ischeck=?  where videoID=?";
        int what=jdbcTemplate.getJdbcTemplate().update(updatesql,new Object[]{state,1,video_id});
        if(what==0)return false;
        return true;
    }

    @Override
    public List<videoEntity> searchVideotj(String videocAtegory,int num) {
        String select_sql="select * from  video where videocAtegory=? ORDER BY supportcount DESC LIMIT ?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{videocAtegory,num},new VideoRowMapperEntity());
        return videoList;
    }

    @Override
    public List<videoEntity> searchVideonew(String videocAtegory, int start,int end) {
        String select_sql="select * from  video where videocAtegory=? && ispass=1 ORDER BY uploadtime DESC LIMIT ? ,?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{videocAtegory,start,end},new VideoRowMapperEntity());
        return videoList;
    }

    @Override
    public List<videoEntity> searchVidetoptj(int start, int end) {
        String select_sql="select * from  video where ispass=1  ORDER BY supportcount DESC LIMIT ?,?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{start,end},new VideoRowMapperEntity());
        return videoList;
    }

    @Override
    public int videotypecount(int videotype) {
        String sql="select count(*) from video where videocAtegory=?";// where videocAtegory = ?
        int num = jdbcTemplate.getJdbcTemplate().queryForObject(sql, new Object[]{new String(videotype+"")},Integer.class);
        return num;
    }

    @Autowired
    Data_jdbcTemplate jdbcTemplate;
    @Override
    public List<videoEntity> selectVideo_by_vid(int vid) {
        String select_sql="select * from  video where videoID=?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{vid},new VideoRowMapperEntity());
        return videoList;
       // return null;
    }

    @Override
    public List<videoEntity> selectVideo_by_name(String name) {
        String select_sql="select * from  video where videoName like ?";
        List<videoEntity> danmuList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{"%"+name+"%"},new VideoRowMapperEntity());

        return danmuList;
    }
    public List<videoEntity> selectVideo_by_user_id(String user_id) {
        String select_sql="select * from  video where user_id = ?";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{ user_id },new VideoRowMapperEntity());
        return videoList;
    }
    
    @Override
    public List<videoEntity> selectVideo_by_user_id_and_name(String user_id, String videoName) {
        String select_sql="select * from video where user_id = ? and videoName = ? ORDER BY uploadtime DESC";
        List<videoEntity> videoList=jdbcTemplate.getJdbcTemplate().query(select_sql,new Object[]{ user_id, videoName },new VideoRowMapperEntity());
        return videoList;
    }
    
    @Override
    public List<videoEntity> selectAllVideosWithoutAudio() {
        String select_sql = "SELECT v.* FROM video v LEFT JOIN audio a ON v.videoID = a.video_id WHERE a.video_id IS NULL AND v.videoAddress LIKE '%.mp4' ORDER BY v.uploadtime DESC";
        List<videoEntity> videoList = jdbcTemplate.getJdbcTemplate().query(select_sql, new VideoRowMapperEntity());
        return videoList;
    }
    
    @Override
    public videoEntity getVideoById(int videoId) {
        String select_sql = "SELECT * FROM video WHERE videoID = ?";
        List<videoEntity> videoList = jdbcTemplate.getJdbcTemplate().query(select_sql, new Object[]{videoId}, new VideoRowMapperEntity());
        if (videoList != null && !videoList.isEmpty()) {
            return videoList.get(0);
        }
        return null;
    }
    
    @Override
    public List<videoEntity> selectAllVideos() {
        String select_sql = "SELECT * FROM video ORDER BY uploadtime DESC";
        List<videoEntity> videoList = jdbcTemplate.getJdbcTemplate().query(select_sql, new VideoRowMapperEntity());
        return videoList;
    }
}
