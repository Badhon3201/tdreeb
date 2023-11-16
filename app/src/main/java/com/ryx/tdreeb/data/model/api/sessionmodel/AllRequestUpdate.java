package com.ryx.tdreeb.data.model.api.sessionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRequestUpdate {

    @SerializedName("data")
    @Expose
    private AllRequestData data;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    public AllRequestData getData() {
        return data;
    }

    public void setData(AllRequestData data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public class AllRequestData {

        @SerializedName("sessions")
        @Expose
        private String sessions;

        public String getSessions() {
            return sessions;
        }

        public void setSessions(String sessions) {
            this.sessions = sessions;
        }

    }

}