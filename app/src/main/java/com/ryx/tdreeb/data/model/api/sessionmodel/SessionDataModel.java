package com.ryx.tdreeb.data.model.api.sessionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;

import java.util.List;

public class SessionDataModel {
    @SerializedName("sessions")
    @Expose
    private List<SessionModel> sessions = null;

    public List<SessionModel> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionModel> sessions) {
        this.sessions = sessions;
    }
}