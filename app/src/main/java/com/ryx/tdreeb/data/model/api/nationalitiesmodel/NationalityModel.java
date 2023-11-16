package com.ryx.tdreeb.data.model.api.nationalitiesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NationalityModel {

    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("countryTitle")
    @Expose
    private String countryTitle;
    @SerializedName("nationalityTitle")
    @Expose
    private String nationalityTitle;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryTitle() {
        return countryTitle;
    }

    public void setCountryTitle(String countryTitle) {
        this.countryTitle = countryTitle;
    }

    public String getNationalityTitle() {
        return nationalityTitle;
    }

    public void setNationalityTitle(String nationalityTitle) {
        this.nationalityTitle = nationalityTitle;
    }

}