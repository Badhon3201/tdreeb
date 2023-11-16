package com.ryx.tdreeb.data.model.api.gradesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GradesDataModel {
    @SerializedName("grades")
    @Expose
    private List<GradeModel> grades = null;

    public List<GradeModel> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeModel> grades) {
        this.grades = grades;
    }

}