package com.ryx.tdreeb.data.model.api.mapmodel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapSuggestionModel {
    @SerializedName("input")
    @Expose
    private String input;
    @SerializedName("components")
    @Expose
    private String components;
    @SerializedName("key")
    @Expose
    private String key;

    public MapSuggestionModel(String input, String components, String key) {
        this.input = input;
        this.components = components;
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "input='" + input + '\'' +
                ", components='" + components + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
