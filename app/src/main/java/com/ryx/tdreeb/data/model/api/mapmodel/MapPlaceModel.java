package com.ryx.tdreeb.data.model.api.mapmodel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapPlaceModel {
    @SerializedName("place_id")
    @Expose
    private String place_id;
    @SerializedName("key")
    @Expose
    private String key;

    public MapPlaceModel(String place_id, String key) {
        this.place_id = place_id;
        this.key = key;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "place_id='" + place_id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
