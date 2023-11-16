package com.ryx.tdreeb.data.model.api.subjectmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectDataModel {
    @SerializedName("subjects")
    @Expose
    private List<SubjectModel> subjects = null;

    public List<SubjectModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectModel> subjects) {
        this.subjects = subjects;
    }

}