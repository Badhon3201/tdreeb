package com.ryx.tdreeb.data.model.api.chatmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryModel {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("receiverId")
    @Expose
    private Integer receiverId;
    @SerializedName("receiverType")
    @Expose
    private String receiverType;
    @SerializedName("receiver")
    @Expose
    private ChatReceiverModel receiver;
    @SerializedName("lastModified")
    @Expose
    private Long lastModified;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public ChatReceiverModel getReceiver() {
        return receiver;
    }

    public void setReceiver(ChatReceiverModel receiver) {
        this.receiver = receiver;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

}