
package com.ryx.tdreeb.data.model.api.ParentAllVideoList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Result {

    @SerializedName("liveCourseId")
    @Expose
    public Integer liveCourseId;
    @SerializedName("trainerId")
    @Expose
    public Integer trainerId;
    @SerializedName("courseTitle")
    @Expose
    public String courseTitle;
    @SerializedName("courseSubject")
    @Expose
    public String courseSubject;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("liveDate")
    @Expose
    public String liveDate;
    @SerializedName("stramingLiveDate")
    @Expose
    public Object stramingLiveDate;
    @SerializedName("liveTime")
    @Expose
    public Object liveTime;
    @SerializedName("meetingLink")
    @Expose
    public Object meetingLink;
    @SerializedName("meetingPassword")
    @Expose
    public Object meetingPassword;
    @SerializedName("meetingDetails")
    @Expose
    public String meetingDetails;
    @SerializedName("meetingDescription")
    @Expose
    public String meetingDescription;
    @SerializedName("uploadLink")
    @Expose
    public String uploadLink;
    @SerializedName("remainingTime")
    @Expose
    public String remainingTime;
    @SerializedName("courseType")
    @Expose
    public String courseType;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("vedioCourseFiles")
    @Expose
    public List<VedioCourseFile> vedioCourseFiles = null;
    @SerializedName("trainerResponse")
    @Expose
    public TrainerResponse trainerResponse;

}
