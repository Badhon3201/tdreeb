package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.Model;

public class SheduleModel {
    String toTime,fromTime;

    public SheduleModel(String toTime, String fromTime) {
        this.toTime = toTime;
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
}
