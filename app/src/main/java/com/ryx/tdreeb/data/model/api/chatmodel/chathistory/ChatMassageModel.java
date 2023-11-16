package com.ryx.tdreeb.data.model.api.chatmodel.chathistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMassageModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isMe")
    @Expose
    private Boolean isMe;
    @SerializedName("lastModified")
    @Expose
    private Long lastModified;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsMe() {
        return isMe;
    }

    public void setIsMe(Boolean isMe) {
        this.isMe = isMe;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

}