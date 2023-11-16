package com.ryx.tdreeb.data.model.api.favoritemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteSubmitDataModel {
    @SerializedName("favourite")
    @Expose
    private FavouriteModel favourite;

    @SerializedName("result")
    @Expose
    private boolean result;

    public FavouriteModel getFavourite() {
        return favourite;
    }

    public void setFavourite(FavouriteModel favourite) {
        this.favourite = favourite;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}