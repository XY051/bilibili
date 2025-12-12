package com.sf.entity;

import java.sql.Date;

public class videoEntity {
	
	private int videoID; //视频ID
	private String videoName;//视频名字
	private String videoImage;//视频图片
	private String videoAddress;//视频地址
	private int videolookTime;//视频时长
	private String videoCollection;//收藏
	private String videoLeaving;//视频留言
	private String videoTime;//视频上传时间
	private String videoState;//视频状态
	private String videocAtegory;//视频类别
	private int supportcount;//点赞数量
	private String user_id;//作者
	private int ispass;//审核是否通过
	private  int ischeck;//是否审核完

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	private Date uploadtime;


	public String getVideodetail() {
		return videodetail;
	}

	public void setVideodetail(String videodetail) {
		this.videodetail = videodetail;
	}

	private  String videodetail;



	public int getSupportcount() {
		return supportcount;
	}

	public void setSupportcount(int supportcount) {
		this.supportcount = supportcount;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getIspass() {
		return ispass;
	}

	public void setIspass(int ispass) {
		this.ispass = ispass;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}


	
	
	public int getVideoID() {
		return videoID;
	}
	public void setVideoID(int videoID) {
		this.videoID = videoID;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoImage() {
		return videoImage;
	}
	public void setVideoImage(String videoImage) {
		this.videoImage = videoImage;
	}
	public String getVideoAddress() {
		return videoAddress;
	}
	public void setVideoAddress(String videoAddress) {
		this.videoAddress = videoAddress;
	}
	public int getVideolookTime() {
		return videolookTime;
	}
	public void setVideolookTime(int videolookTime) {
		this.videolookTime = videolookTime;
	}
	public String getVideoCollection() {
		return videoCollection;
	}
	public void setVideoCollection(String videoCollection) {
		this.videoCollection = videoCollection;
	}
	public String getVideoLeaving() {
		return videoLeaving;
	}
	public void setVideoLeaving(String videoLeaving) {
		this.videoLeaving = videoLeaving;
	}
	public String getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}
	public String getVideoState() {
		return videoState;
	}
	public void setVideoState(String videoState) {
		this.videoState = videoState;
	}
	public String getVideocAtegory() {
		return videocAtegory;
	}
	public void setVideocAtegory(String videocAtegory) {
		this.videocAtegory = videocAtegory;
	}
	
	
	
	
	
	
	
}
