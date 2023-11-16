package com.ryx.tdreeb.data.model.api.curriculamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurriculaDataModel {
    @SerializedName("curriculums")
    @Expose
    private List<CurriculumModel> curriculums = null;

    public List<CurriculumModel> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<CurriculumModel> curriculums) {
        this.curriculums = curriculums;
    }

}