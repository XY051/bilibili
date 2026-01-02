CREATE TABLE `audio` (
  `audio_id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` int(11) DEFAULT NULL COMMENT '对应的视频ID',
  `audio_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '音频文件名',
  `audio_path` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '音频文件路径',
  `audio_size` bigint(20) DEFAULT NULL COMMENT '音频文件大小',
  `audio_duration` varchar(20) DEFAULT NULL COMMENT '音频时长',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`audio_id`),
  KEY `idx_video_id` (`video_id`),
  CONSTRAINT `fk_audio_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`videoID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='音频表';