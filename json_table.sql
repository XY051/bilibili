CREATE TABLE `json` (
  `json_id` int(11) NOT NULL AUTO_INCREMENT,
  `audio_id` int(11) DEFAULT NULL COMMENT '对应的音频ID',
  `json_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'JSON文件名',
  `json_path` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'JSON文件路径',
  `json_size` bigint(20) DEFAULT NULL COMMENT 'JSON文件大小',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`json_id`),
  KEY `idx_audio_id` (`audio_id`),
  CONSTRAINT `fk_json_audio` FOREIGN KEY (`audio_id`) REFERENCES `audio` (`audio_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='JSON转录文件表';