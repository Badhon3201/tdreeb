package com.ryx.tdreeb.data.model.api.TrainingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainingDataModel {
    @SerializedName("subjects")
    @Expose
    private List<TrainingModel> subjects = null;

    public List<TrainingModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TrainingModel> subjects) {
        this.subjects = subjects;
    }

}