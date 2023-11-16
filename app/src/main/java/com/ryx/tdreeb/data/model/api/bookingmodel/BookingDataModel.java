package com.ryx.tdreeb.data.model.api.bookingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDataModel {
    @SerializedName("session")
    @Expose
    private SessionModel session;

    public SessionModel getSession() {
        return session;
    }

    public void setSession(SessionModel session) {
        this.session = session;
    }

}