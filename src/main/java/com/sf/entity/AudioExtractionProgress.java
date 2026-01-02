package com.sf.entity;

public class AudioExtractionProgress {
    private int total;
    private int processed;
    private int success;
    private String status;
    private String currentVideoName;

    public AudioExtractionProgress() {
        this.total = 0;
        this.processed = 0;
        this.success = 0;
        this.status = "初始化";
        this.currentVideoName = "";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentVideoName() {
        return currentVideoName;
    }

    public void setCurrentVideoName(String currentVideoName) {
        this.currentVideoName = currentVideoName;
    }

    public int getPercentage() {
        if (total <= 0) return 0;
        return (int) ((processed * 100.0) / total);
    }
}