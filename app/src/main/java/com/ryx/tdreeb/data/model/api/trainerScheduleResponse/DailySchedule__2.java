
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DailySchedule__2 {

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

}
