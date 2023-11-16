package com.ryx.tdreeb.data.model.api.bookingmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionLogsModel implements Parcelable {

    @SerializedName("sessionLogId")
    @Expose
    private Integer sessionLogId;
    @SerializedName("sessionId")
    @Expose
    private Integer sessionId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lastModified")
    @Expose
    private Long lastModified;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("minutes")
    @Expose
    private String minutes;
    @SerializedName("isUpdated")
    @Expose
    private boolean isUpdated;

    protected SessionLogsModel(Parcel in) {
        if (in.readByte() == 0) {
            sessionLogId = null;
        } else {
            sessionLogId = in.readInt();
        }
        if (in.readByte() == 0) {
            sessionId = null;
        } else {
            sessionId = in.readInt();
        }
        status = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            lastModified = null;
        } else {
            lastModified = in.readLong();
        }
        time = in.readString();
        hours = in.readString();
        minutes = in.readString();
        isUpdated = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sessionLogId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sessionLogId);
        }
        if (sessionId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sessionId);
        }
        dest.writeString(status);
        dest.writeString(description);
        if (lastModified == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(lastModified);
        }
        dest.writeString(time);
        dest.writeString(hours);
        dest.writeString(minutes);
        dest.writeByte((byte) (isUpdated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SessionLogsModel> CREATOR = new Creator<SessionLogsModel>() {
        @Override
        public SessionLogsModel createFromParcel(Parcel in) {
            return new SessionLogsModel(in);
        }

        @Override
        public SessionLogsModel[] newArray(int size) {
            return new SessionLogsModel[size];
        }
    };

    public Integer getSessionLogId() {
        return sessionLogId;
    }

    public void setSessionLogId(Integer sessionLogId) {
        this.sessionLogId = sessionLogId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}