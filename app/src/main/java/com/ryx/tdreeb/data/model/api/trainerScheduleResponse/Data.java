
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("dailySchedule")
    @Expose
    public List<DailySchedule> dailySchedule = null;
    @SerializedName("onlineSchedule")
    @Expose
    public List<DailySchedule> onlineSchedule = null;

}
