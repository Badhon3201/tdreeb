package com.ryx.tdreeb.data.model.api.nationalitiesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NationalitiesDataModel {
    @SerializedName("nationalities")
    @Expose
    private List<NationalityModel> nationalities = null;

    public List<NationalityModel> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<NationalityModel> nationalities) {
        this.nationalities = nationalities;
    }

}