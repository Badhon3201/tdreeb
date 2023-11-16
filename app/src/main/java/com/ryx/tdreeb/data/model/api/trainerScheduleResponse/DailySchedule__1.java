
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailySchedule__1 implements Parcelable {

    @SerializedName("availableWeekDayId")
    @Expose
    public Integer availableWeekDayId;
    @SerializedName("trainerId")
    @Expose
    public Integer trainerId;
    @SerializedName("availableDateId")
    @Expose
    public Integer availableDateId;
    @SerializedName("fromTime")
    @Expose
    public String fromTime;
    @SerializedName("toTime")
    @Expose
    public String toTime;

    public DailySchedule__1() {
    }

    public DailySchedule__1(Integer availableDateId, Integer availableWeekDayId, String fromTime, String toTime, Integer trainerId) {
        this.availableWeekDayId = this.availableWeekDayId;
        this.trainerId = this.trainerId;
        this.availableDateId = this.availableDateId;
        this.fromTime = this.fromTime;
        this.toTime = this.toTime;
    }

    public Integer getAvailableWeekDayId() {
        return availableWeekDayId;
    }

    public void setAvailableWeekDayId(Integer availableWeekDayId) {
        this.availableWeekDayId = availableWeekDayId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getAvailableDateId() {
        return availableDateId;
    }

    public void setAvailableDateId(Integer availableDateId) {
        this.availableDateId = availableDateId;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    protected DailySchedule__1(Parcel in) {
        if (in.readByte() == 0) {
            availableWeekDayId = null;
        } else {
            availableWeekDayId = in.readInt();
        }
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        if (in.readByte() == 0) {
            availableDateId = null;
        } else {
            availableDateId = in.readInt();
        }
        fromTime = in.readString();
        toTime = in.readString();
    }

    public static final Creator<DailySchedule__1> CREATOR = new Creator<DailySchedule__1>() {
        @Override
        public DailySchedule__1 createFromParcel(Parcel in) {
            return new DailySchedule__1(in);
        }

        @Override
        public DailySchedule__1[] newArray(int size) {
            return new DailySchedule__1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (availableWeekDayId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableWeekDayId);
        }
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        if (availableDateId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableDateId);
        }
        dest.writeString(fromTime);
        dest.writeString(toTime);
    }
}
