package com.ryx.tdreeb.data.model.api.bookingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionStatusUpdateData {
    @SerializedName("session")
    @Expose
    private List<SessionLogsModel> session = null;

    public List<SessionLogsModel> getSession() {
        return session;
    }

    public void setSession(List<SessionLogsModel> session) {
        this.session = session;
    }

}