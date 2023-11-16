package com.ryx.tdreeb.data.model.api.chatmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatDataModel {
    @SerializedName("histories")
    @Expose
    private List<ChatHistoryModel> histories = null;

    public List<ChatHistoryModel> getHistories() {
        return histories;
    }

    public void setHistories(List<ChatHistoryModel> histories) {
        this.histories = histories;
    }

}