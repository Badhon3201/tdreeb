package com.ryx.tdreeb.data.model.api.getcoursemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCourseModel {
    @SerializedName("trainerCourseId")
    @Expose
    private Integer trainerCourseId;
    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("curriculumIds")
    @Expose
    private List<Integer> curriculumIds = null;
    @SerializedName("gradeIds")
    @Expose
    private List<Integer> gradeIds = null;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("allowGroup")
    @Expose
    private Boolean allowGroup;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public List<Integer> getCurriculumIds() {
        return curriculumIds;
    }

    public void setCurriculumIds(List<Integer> curriculumIds) {
        this.curriculumIds = curriculumIds;
    }

    public List<Integer> getGradeIds() {
        return gradeIds;
    }

    public void setGradeIds(List<Integer> gradeIds) {
        this.gradeIds = gradeIds;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getAllowGroup() {
        return allowGroup;
    }

    public void setAllowGroup(Boolean allowGroup) {
        this.allowGroup = allowGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTrainerCourseId() {
        return trainerCourseId;
    }

    public void setTrainerCourseId(Integer trainerCourseId) {
        this.trainerCourseId = trainerCourseId;
    }

    @Override
    public String toString() {
        return "{" +
                "trainerCourseId=" + trainerCourseId +
                ", trainerId=" + trainerId +
                ", curriculumIds=" + curriculumIds +
                ", gradeIds=" + gradeIds +
                ", subjectId=" + subjectId +
                ", price='" + price + '\'' +
                ", rating=" + rating +
                ", allowGroup=" + allowGroup +
                ", description='" + description + '\'' +
                '}';
    }
}