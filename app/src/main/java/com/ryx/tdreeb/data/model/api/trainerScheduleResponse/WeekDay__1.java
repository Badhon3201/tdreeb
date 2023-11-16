
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeekDay__1 implements Parcelable {

    @SerializedName("weekDay")
    @Expose
    public String weekDay;
    @SerializedName("isHoliday")
    @Expose
    public Boolean isHoliday;
    @SerializedName("weekDayNumber")
    @Expose
    public Integer weekDayNumber;
    @SerializedName("dailySchedules")
    @Expose
    public List<DailySchedule__2> dailySchedules = null;

    protected WeekDay__1(Parcel in) {
        weekDay = in.readString();
        byte tmpIsHoliday = in.readByte();
        isHoliday = tmpIsHoliday == 0 ? null : tmpIsHoliday == 1;
        if (in.readByte() == 0) {
            weekDayNumber = null;
        } else {
            weekDayNumber = in.readInt();
        }
    }

    public static final Creator<WeekDay__1> CREATOR = new Creator<WeekDay__1>() {
        @Override
        public WeekDay__1 createFromParcel(Parcel in) {
            return new WeekDay__1(in);
        }

        @Override
        public WeekDay__1[] newArray(int size) {
            return new WeekDay__1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weekDay);
        dest.writeByte((byte) (isHoliday == null ? 0 : isHoliday ? 1 : 2));
        if (weekDayNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weekDayNumber);
        }
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Boolean getHoliday() {
        return isHoliday;
    }

    public void setHoliday(Boolean holiday) {
        isHoliday = holiday;
    }

    public Integer getWeekDayNumber() {
        return weekDayNumber;
    }

    public void setWeekDayNumber(Integer weekDayNumber) {
        this.weekDayNumber = weekDayNumber;
    }

    public List<DailySchedule__2> getDailySchedules() {
        return dailySchedules;
    }

    public void setDailySchedules(List<DailySchedule__2> dailySchedules) {
        this.dailySchedules = dailySchedules;
    }

    public static Creator<WeekDay__1> getCREATOR() {
        return CREATOR;
    }
}
