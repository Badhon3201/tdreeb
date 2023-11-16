package com.ryx.tdreeb.data.model.api.laguagemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguagesDataModel {
    @SerializedName("languages")
    @Expose
    private List<LanguageModel> languages = null;

    public List<LanguageModel> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageModel> languages) {
        this.languages = languages;
    }

}
