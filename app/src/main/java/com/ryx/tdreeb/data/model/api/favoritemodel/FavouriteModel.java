package com.ryx.tdreeb.data.model.api.favoritemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.UserModelTwo;

public class FavouriteModel {

    @SerializedName("favoriteListId")
    @Expose
    private Integer favoriteListId;
    @SerializedName("favorite")
    @Expose
    private String favorite;
    @SerializedName("favoriteId")
    @Expose
    private Integer favoriteId;
    @SerializedName("favoriteBy")
    @Expose
    private String favoriteBy;
    @SerializedName("favoriteById")
    @Expose
    private Integer favoriteById;
    @SerializedName("trainerCourse")
    @Expose
    private Object trainerCourse;
    @SerializedName("liveorVideoCourse")
    @Expose
    private Object liveorVideoCourse;
    @SerializedName("trainerResourcesRequest")
    @Expose
    private ResourceModel trainerResourcesRequest;
    @SerializedName("trainer")
    @Expose
    private Object trainer;
    @SerializedName("favoriteByStudent")
    @Expose
    private Object favoriteByStudent;
    @SerializedName("favoriteByParents")
    @Expose
    private UserModelTwo favoriteByParents;

    public Integer getFavoriteListId() {
        return favoriteListId;
    }

    public void setFavoriteListId(Integer favoriteListId) {
        this.favoriteListId = favoriteListId;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteBy() {
        return favoriteBy;
    }

    public void setFavoriteBy(String favoriteBy) {
        this.favoriteBy = favoriteBy;
    }

    public Integer getFavoriteById() {
        return favoriteById;
    }

    public void setFavoriteById(Integer favoriteById) {
        this.favoriteById = favoriteById;
    }

    public Object getTrainerCourse() {
        return trainerCourse;
    }

    public void setTrainerCourse(Object trainerCourse) {
        this.trainerCourse = trainerCourse;
    }

    public Object getLiveorVideoCourse() {
        return liveorVideoCourse;
    }

    public void setLiveorVideoCourse(Object liveorVideoCourse) {
        this.liveorVideoCourse = liveorVideoCourse;
    }

    public ResourceModel getTrainerResourcesRequest() {
        return trainerResourcesRequest;
    }

    public void setTrainerResourcesRequest(ResourceModel trainerResourcesRequest) {
        this.trainerResourcesRequest = trainerResourcesRequest;
    }

    public Object getTrainer() {
        return trainer;
    }

    public void setTrainer(Object trainer) {
        this.trainer = trainer;
    }

    public Object getFavoriteByStudent() {
        return favoriteByStudent;
    }

    public void setFavoriteByStudent(Object favoriteByStudent) {
        this.favoriteByStudent = favoriteByStudent;
    }

    public UserModelTwo getFavoriteByParents() {
        return favoriteByParents;
    }

    public void setFavoriteByParents(UserModelTwo favoriteByParents) {
        this.favoriteByParents = favoriteByParents;
    }

}