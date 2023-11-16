package com.ryx.tdreeb.data.model.api.allvideocourse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;

import java.util.List;

public class AllVideoCourseResult {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("course")
    @Expose
    private List<LiveCourseModel> result;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<LiveCourseModel> getResult() {
        return result;
    }

    public void setResult(List<LiveCourseModel> result) {
        this.result = result;
    }
}
