package com.sf.tool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sf.entity.videoEntity;

//视频ID区
public class VideoRowMapperEntity  implements RowMapper<videoEntity>{

	@Override
	public videoEntity mapRow(ResultSet arg0, int arg1) throws SQLException {
		 	videoEntity video = new videoEntity();
		 	video.setVideoAddress(arg0.getString("videoAddress"));
		 	video.setVideoCollection(arg0.getString("videoCollection"));
		 	video.setVideoID(arg0.getInt("videoID"));
		 	video.setVideoImage(arg0.getString("videoImage"));
		 	video.setVideoLeaving(arg0.getString("videoLeaving"));
		 	video.setVideoName(arg0.getString("videoName"));
		 	video.setVideolookTime(arg0.getInt("videolookTime"));
		 	video.setVideoState(arg0.getString("videoState"));
		 	video.setVideoTime(arg0.getString("videoTime"));
		    video.setUser_id(arg0.getString("user_id"));
		 	video.setIscheck(arg0.getInt("ischeck"));
		    video.setIspass(arg0.getInt("ispass"));
		    video.setSupportcount(arg0.getInt("supportcount"));
		    video.setVideodetail(arg0.getString("videodetail"));
		    video.setUploadtime(arg0.getDate("uploadtime"));
		    video.setVideocAtegory(arg0.getString("videocAtegory"));
		return video;
	}

}
