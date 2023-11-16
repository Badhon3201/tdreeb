package com.ryx.tdreeb.data.model.api.allvideocourse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;

import java.util.List;

public class AllVideoCourseDataModel {
    @SerializedName("result")
    @Expose
    private List<AllVideoCourseResult> result;

    public List<AllVideoCourseResult> getResult() {
        return result;
    }

    public void setResult(List<AllVideoCourseResult> result) {
        this.result = result;
    }
}