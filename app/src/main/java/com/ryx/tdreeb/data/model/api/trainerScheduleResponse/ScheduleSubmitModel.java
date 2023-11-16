package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleSubmitModel {

    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("dailySchedule")
    @Expose
    private String dailySchedule;
    @SerializedName("weekdayScheduleId")
    @Expose
    private Integer weekdayScheduleId;
    @SerializedName("scheduleType")
    @Expose
    private String scheduleType;

    @SerializedName("availableforDirectTraining")
    @Expose
    private boolean availableforDirectTraining;
    @SerializedName("availableforDirectGroupSession")
    @Expose
    private boolean availableforDirectGroupSession;
    @SerializedName("availableforOnlineTraining")
    @Expose
    private boolean availableforOnlineTraining;
    @SerializedName("availableforOnlineGroupSession")
    @Expose
    private boolean availableforOnlineGroupSession;

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

    public String getDailySchedule() {
        return dailySchedule;
    }

    public void setDailySchedule(String dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    public Integer getWeekdayScheduleId() {
        return weekdayScheduleId;
    }

    public void setWeekdayScheduleId(Integer weekdayScheduleId) {
        this.weekdayScheduleId = weekdayScheduleId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
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

    @Override
    public String toString() {
        return "{" +
                "trainerId=" + trainerId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", dailySchedule=" + dailySchedule +
                ", weekdayScheduleId=" + weekdayScheduleId +
                ", scheduleType='" + scheduleType + '\'' +
                ", availableforDirectTraining=" + availableforDirectTraining +
                ", availableforDirectGroupSession=" + availableforDirectGroupSession +
                ", availableforOnlineTraining=" + availableforOnlineTraining +
                ", availableforOnlineGroupSession=" + availableforOnlineGroupSession +
                '}';
    }

    public static class DailySchedule {

        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("IsHoliday")
        @Expose
        private boolean IsHoliday;
        @SerializedName("timeData")
        @Expose
        private List<TimeDatum> timeData = null;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public List<TimeDatum> getTimeData() {
            return timeData;
        }

        public void setTimeData(List<TimeDatum> timeData) {
            this.timeData = timeData;
        }

        public boolean isHoliday() {
            return IsHoliday;
        }

        public void setHoliday(boolean holiday) {
            IsHoliday = holiday;
        }

        @Override
        public String toString() {
            return "{" +
                    "day:'" + day + '\'' +
                    ", IsHoliday:" + IsHoliday +
                    ", timeData:" + timeData +
                    '}';
        }
    }

    public static class TimeDatum {

        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("fromTimeStamp")
        @Expose
        private String fromTimeStamp;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("toTimeStamp")
        @Expose
        private String toTimeStamp;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromTimeStamp() {
            return fromTimeStamp;
        }

        public void setFromTimeStamp(String fromTimeStamp) {
            this.fromTimeStamp = fromTimeStamp;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getToTimeStamp() {
            return toTimeStamp;
        }

        public void setToTimeStamp(String toTimeStamp) {
            this.toTimeStamp = toTimeStamp;
        }


        @Override
        public String toString() {
            return "{" +
                    "from:'" + from + '\'' +
                    ", fromTimeStamp:'" + fromTimeStamp + '\'' +
                    ", to:'" + to + '\'' +
                    ", toTimeStamp:'" + toTimeStamp + '\'' +
                    '}';
        }
    }
}