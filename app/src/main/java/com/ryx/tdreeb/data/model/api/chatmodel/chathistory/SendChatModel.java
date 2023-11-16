package com.ryx.tdreeb.data.model.api.chatmodel.chathistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendChatModel {
    @SerializedName("senderId")
    @Expose
    private Integer senderId;
    @SerializedName("senderType")
    @Expose
    private String senderType;
    @SerializedName("receiverId")
    @Expose
    private Integer receiverId;
    @SerializedName("receiverType")
    @Expose
    private String receiverType;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "senderId=" + senderId +
                ", senderType='" + senderType + '\'' +
                ", receiverId=" + receiverId +
                ", receiverType='" + receiverType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}