
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DailySchedule implements Parcelable {

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
    @SerializedName("availableforDirectTraining")
    @Expose
    public boolean availableforDirectTraining;
    @SerializedName("availableforDirectGroupSession")
    @Expose
    public boolean availableforDirectGroupSession;
    @SerializedName("availableforOnlineTraining")
    @Expose
    public boolean availableforOnlineTraining;
    @SerializedName("availableforOnlineGroupSession")
    @Expose
    public boolean availableforOnlineGroupSession;

    @SerializedName("weekDays")
    @Expose
    public List<WeekDay> weekDays = null;


    protected DailySchedule(Parcel in) {
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
        availableforDirectTraining = in.readByte() != 0;
        availableforDirectGroupSession = in.readByte() != 0;
        availableforOnlineTraining = in.readByte() != 0;
        availableforOnlineGroupSession = in.readByte() != 0;
        weekDays = in.createTypedArrayList(WeekDay.CREATOR);
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
        dest.writeByte((byte) (availableforDirectTraining ? 1 : 0));
        dest.writeByte((byte) (availableforDirectGroupSession ? 1 : 0));
        dest.writeByte((byte) (availableforOnlineTraining ? 1 : 0));
        dest.writeByte((byte) (availableforOnlineGroupSession ? 1 : 0));
        dest.writeTypedList(weekDays);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailySchedule> CREATOR = new Creator<DailySchedule>() {
        @Override
        public DailySchedule createFromParcel(Parcel in) {
            return new DailySchedule(in);
        }

        @Override
        public DailySchedule[] newArray(int size) {
            return new DailySchedule[size];
        }
    };

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

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<WeekDay> weekDays) {
        this.weekDays = weekDays;
    }

    public boolean isAvailableforDirectTraining() {
        return availableforDirectTraining;
    }

    public void setAvailableforDirectTraining(boolean availableforDirectTraining) {
        this.availableforDirectTraining = availableforDirectTraining;
    }

    public boolean isAvailableforDirectGroupSession() {
        return availableforDirectGroupSession;
    }

    public void setAvailableforDirectGroupSession(boolean availableforDirectGroupSession) {
        this.availableforDirectGroupSession = availableforDirectGroupSession;
    }

    public boolean isAvailableforOnlineTraining() {
        return availableforOnlineTraining;
    }

    public void setAvailableforOnlineTraining(boolean availableforOnlineTraining) {
        this.availableforOnlineTraining = availableforOnlineTraining;
    }

    public boolean isAvailableforOnlineGroupSession() {
        return availableforOnlineGroupSession;
    }

    public void setAvailableforOnlineGroupSession(boolean availableforOnlineGroupSession) {
        this.availableforOnlineGroupSession = availableforOnlineGroupSession;
    }
}
