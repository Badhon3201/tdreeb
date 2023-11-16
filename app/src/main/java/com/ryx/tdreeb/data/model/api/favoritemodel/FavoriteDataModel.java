package com.ryx.tdreeb.data.model.api.favoritemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteDataModel {
    @SerializedName("favourite")
    @Expose
    private List<FavouriteModel> favourite = null;

    public List<FavouriteModel> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<FavouriteModel> favourite) {
        this.favourite = favourite;
    }

}