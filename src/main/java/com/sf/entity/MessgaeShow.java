package com.sf.entity;

public class MessgaeShow {
    private String messageID; //留言ID
    private String messagevideoID;//留言视频ID
    private String messageuserID;//留言用户ID
    private String messageuserName;//留言用户名字
    private String message;//留言内容
    private String messageTime;//留言时间
    private String messageHand;//用户头像
    private String userName;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    private String address;
    private String videoName;

    public String getMessageID() {
        return messageID;
    }

    public String getMessagevideoID() {
        return messagevideoID;
    }

    public String getMessageuserID() {
        return messageuserID;
    }

    public String getMessageuserName() {
        return messageuserName;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getMessageHand() {
        return messageHand;
    }

    public String getUserName() {
        return userName;
    }



    public String getVideoName() {
        return videoName;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setMessagevideoID(String messagevideoID) {
        this.messagevideoID = messagevideoID;
    }

    public void setMessageuserID(String messageuserID) {
        this.messageuserID = messageuserID;
    }

    public void setMessageuserName(String messageuserName) {
        this.messageuserName = messageuserName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setMessageHand(String messageHand) {
        this.messageHand = messageHand;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
