DROP TABLE IF EXISTS `audio`;
CREATE TABLE `audio`  (
                          `audio_id` int NOT NULL AUTO_INCREMENT,
                          `video_id` bigint NULL DEFAULT NULL COMMENT '对应的视频ID',
                          `audio_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '音频文件名',
                          `audio_path` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '音频文件路径',
                          `audio_size` bigint NULL DEFAULT NULL COMMENT '音频文件大小',
                          `audio_duration` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '音频时长',
                          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          PRIMARY KEY (`audio_id`) USING BTREE,
                          INDEX `idx_video_id`(`video_id` ASC) USING BTREE,
                          CONSTRAINT `fk_audio_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`videoID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '音频表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;