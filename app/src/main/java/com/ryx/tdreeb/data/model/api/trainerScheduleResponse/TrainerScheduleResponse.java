
package com.ryx.tdreeb.data.model.api.trainerScheduleResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainerScheduleResponse {

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;

}
