
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class OnlineSchedule implements Parcelable {

    @SerializedName("availableDateId")
    @Expose
    public Integer availableDateId;
    @SerializedName("trainerId")
    @Expose
    public Integer trainerId;
    @SerializedName("fromDate")
    @Expose
    public String fromDate;
    @SerializedName("toDate")
    @Expose
    public String toDate;
    @SerializedName("scheduleType")
    @Expose
    public String scheduleType;
    @SerializedName("weekDays")
    @Expose
    public List<WeekDay__1> weekDays = null;

    protected OnlineSchedule(Parcel in) {
        if (in.readByte() == 0) {
            availableDateId = null;
        } else {
            availableDateId = in.readInt();
        }
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        fromDate = in.readString();
        toDate = in.readString();
        scheduleType = in.readString();
    }

    public Integer getAvailableDateId() {
        return availableDateId;
    }

    public void setAvailableDateId(Integer availableDateId) {
        this.availableDateId = availableDateId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public List<WeekDay__1> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<WeekDay__1> weekDays) {
        this.weekDays = weekDays;
    }

    public static Creator<OnlineSchedule> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (availableDateId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableDateId);
        }
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeString(scheduleType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OnlineSchedule> CREATOR = new Creator<OnlineSchedule>() {
        @Override
        public OnlineSchedule createFromParcel(Parcel in) {
            return new OnlineSchedule(in);
        }

        @Override
        public OnlineSchedule[] newArray(int size) {
            return new OnlineSchedule[size];
        }
    };
}
