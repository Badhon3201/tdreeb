package com.ryx.tdreeb.data.model.api.chatmodel.chathistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatHistoryDataModel {

    @SerializedName("messages")
    @Expose
    private List<ChatMassageModel> messages = null;

    public List<ChatMassageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMassageModel> messages) {
        this.messages = messages;
    }

}