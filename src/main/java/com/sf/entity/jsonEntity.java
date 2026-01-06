package com.sf.entity;

public class jsonEntity {
    private int jsonId;
    private int audioId;
    private String jsonName;
    private String jsonPath;
    private Long jsonSize;
    private String createTime;

    // Getters and Setters
    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public Long getJsonSize() {
        return jsonSize;
    }

    public void setJsonSize(Long jsonSize) {
        this.jsonSize = jsonSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}