package com.ryx.tdreeb.data.model.api.livecoursesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveCourseAddModel {
    @SerializedName("liveCourseId")
    @Expose
    private Integer liveCourseId;
    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("courseTitle")
    @Expose
    private String courseTitle;
    @SerializedName("courseSubject")
    @Expose
    private String courseSubject;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("liveDate")
    @Expose
    private String liveDate;
    @SerializedName("stramingLiveDate")
    @Expose
    private String stramingLiveDate;
    @SerializedName("liveTime")
    @Expose
    private String liveTime;
    @SerializedName("meetingLink")
    @Expose
    private String meetingLink;
    @SerializedName("meetingPassword")
    @Expose
    private String meetingPassword;
    @SerializedName("meetingDetails")
    @Expose
    private String meetingDetails;
    @SerializedName("meetingDescription")
    @Expose
    private String meetingDescription;
    @SerializedName("uploadLink")
    @Expose
    private String uploadLink;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("liveCourseDateRangeRequests")
    @Expose
    private List<AddDateTimeModel> liveCourseDateRangeRequests = null;

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public String getStramingLiveDate() {
        return stramingLiveDate;
    }

    public void setStramingLiveDate(String stramingLiveDate) {
        this.stramingLiveDate = stramingLiveDate;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getMeetingPassword() {
        return meetingPassword;
    }

    public void setMeetingPassword(String meetingPassword) {
        this.meetingPassword = meetingPassword;
    }

    public String getMeetingDetails() {
        return meetingDetails;
    }

    public void setMeetingDetails(String meetingDetails) {
        this.meetingDetails = meetingDetails;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public String getUploadLink() {
        return uploadLink;
    }

    public void setUploadLink(String uploadLink) {
        this.uploadLink = uploadLink;
    }

    public Integer getLiveCourseId() {
        return liveCourseId;
    }

    public void setLiveCourseId(Integer liveCourseId) {
        this.liveCourseId = liveCourseId;
    }

    public List<AddDateTimeModel> getLiveCourseDateRangeRequests() {
        return liveCourseDateRangeRequests;
    }

    public void setLiveCourseDateRangeRequests(List<AddDateTimeModel> liveCourseDateRangeRequests) {
        this.liveCourseDateRangeRequests = liveCourseDateRangeRequests;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "{" +
                "liveCourseId=" + liveCourseId +
                ", trainerId=" + trainerId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseSubject='" + courseSubject + '\'' +
                ", price='" + price + '\'' +
                ", liveDate='" + liveDate + '\'' +
                ", stramingLiveDate='" + stramingLiveDate + '\'' +
                ", liveTime='" + liveTime + '\'' +
                ", meetingLink='" + meetingLink + '\'' +
                ", meetingPassword='" + meetingPassword + '\'' +
                ", meetingDetails='" + meetingDetails + '\'' +
                ", meetingDescription='" + meetingDescription + '\'' +
                ", uploadLink='" + uploadLink + '\'' +
                ", subjectId=" + subjectId +
                ", liveCourseDateRangeRequests=" + liveCourseDateRangeRequests +
                '}';
    }
}