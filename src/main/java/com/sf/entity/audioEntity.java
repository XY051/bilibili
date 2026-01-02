package com.sf.entity;

import java.util.Date;

public class audioEntity {
    private int audio_id; // 音频ID
    private int video_id; // 对应的视频ID
    private String audio_name; // 音频文件名
    private String audio_path; // 音频文件路径
    private long audio_size; // 音频文件大小
    private String audio_duration; // 音频时长
    private Date create_time; // 创建时间

    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getAudio_name() {
        return audio_name;
    }

    public void setAudio_name(String audio_name) {
        this.audio_name = audio_name;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }

    public long getAudio_size() {
        return audio_size;
    }

    public void setAudio_size(long audio_size) {
        this.audio_size = audio_size;
    }

    public String getAudio_duration() {
        return audio_duration;
    }

    public void setAudio_duration(String audio_duration) {
        this.audio_duration = audio_duration;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}