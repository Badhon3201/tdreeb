package com.ryx.tdreeb.data.model.api.subjectmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectResponse {
    @SerializedName("data")
    @Expose
    private SubjectDataModel data;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    public SubjectDataModel getData() {
        return data;
    }

    public void setData(SubjectDataModel data) {
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

}