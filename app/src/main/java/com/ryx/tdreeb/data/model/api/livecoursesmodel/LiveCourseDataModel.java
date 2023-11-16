package com.ryx.tdreeb.data.model.api.livecoursesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveCourseDataModel {
    @SerializedName("result")
    @Expose
    private List<LiveCourseModel> result = null;

    public List<LiveCourseModel> getResult() {
        return result;
    }

    public void setResult(List<LiveCourseModel> result) {
        this.result = result;
    }

}