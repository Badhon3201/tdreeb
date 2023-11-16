package com.ryx.tdreeb.data.model.api.curriculamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurriculaResponse {
    @SerializedName("data")
    @Expose
    private CurriculaDataModel data;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    public CurriculaDataModel getData() {
        return data;
    }

    public void setData(CurriculaDataModel data) {
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