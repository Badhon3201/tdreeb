package com.ryx.tdreeb.data.model.api.livecoursesmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileModel;

import java.util.List;

public class LiveCourseModel implements Parcelable {

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
    private Double price;
    @SerializedName("liveDate")
    @Expose
    private String liveDate;
    @SerializedName("stramingLiveDate")
    @Expose
    private Object stramingLiveDate;
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
    @SerializedName("remainingTime")
    @Expose
    private String remainingTime;

    @SerializedName("trainerResponse")
    @Expose
    private TrainerProfileModel trainerResponse;

    @SerializedName("courseType")
    @Expose
    private String courseType;

    @SerializedName("vedioCourseFiles")
    @Expose
    private List<VedioCourseFilesModel> vedioCourseFiles;
    @SerializedName("vedioCourseFilesResponse")
    @Expose
    private List<VedioCourseFilesModel> vedioCourseFilesResponse;
    @SerializedName("liveCourseDateRangeRequests")
    @Expose
    private List<AddDateTimeModel> liveCourseDateRangeRequests;

    @SerializedName("subject")
    @Expose
    private SubjectModel subject;
    @SerializedName("overviewVideo")
    @Expose
    private String overviewVideo;

    protected LiveCourseModel(Parcel in) {
        if (in.readByte() == 0) {
            liveCourseId = null;
        } else {
            liveCourseId = in.readInt();
        }
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        courseTitle = in.readString();
        courseSubject = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        liveDate = in.readString();
        liveTime = in.readString();
        meetingLink = in.readString();
        meetingPassword = in.readString();
        meetingDetails = in.readString();
        meetingDescription = in.readString();
        uploadLink = in.readString();
        remainingTime = in.readString();
        trainerResponse = in.readParcelable(TrainerProfileModel.class.getClassLoader());
        courseType = in.readString();
        vedioCourseFiles = in.createTypedArrayList(VedioCourseFilesModel.CREATOR);
        vedioCourseFilesResponse = in.createTypedArrayList(VedioCourseFilesModel.CREATOR);
        liveCourseDateRangeRequests = in.createTypedArrayList(AddDateTimeModel.CREATOR);
        subject = in.readParcelable(SubjectModel.class.getClassLoader());
        overviewVideo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (liveCourseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(liveCourseId);
        }
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        dest.writeString(courseTitle);
        dest.writeString(courseSubject);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeString(liveDate);
        dest.writeString(liveTime);
        dest.writeString(meetingLink);
        dest.writeString(meetingPassword);
        dest.writeString(meetingDetails);
        dest.writeString(meetingDescription);
        dest.writeString(uploadLink);
        dest.writeString(remainingTime);
        dest.writeParcelable(trainerResponse, flags);
        dest.writeString(courseType);
        dest.writeTypedList(vedioCourseFiles);
        dest.writeTypedList(vedioCourseFilesResponse);
        dest.writeTypedList(liveCourseDateRangeRequests);
        dest.writeParcelable(subject, flags);
        dest.writeString(overviewVideo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveCourseModel> CREATOR = new Creator<LiveCourseModel>() {
        @Override
        public LiveCourseModel createFromParcel(Parcel in) {
            return new LiveCourseModel(in);
        }

        @Override
        public LiveCourseModel[] newArray(int size) {
            return new LiveCourseModel[size];
        }
    };

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public Object getStramingLiveDate() {
        return stramingLiveDate;
    }

    public void setStramingLiveDate(Object stramingLiveDate) {
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

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Integer getLiveCourseId() {
        return liveCourseId;
    }

    public void setLiveCourseId(Integer liveCourseId) {
        this.liveCourseId = liveCourseId;
    }

    public List<VedioCourseFilesModel> getVedioCourseFiles() {
        return vedioCourseFiles;
    }

    public void setVedioCourseFiles(List<VedioCourseFilesModel> vedioCourseFiles) {
        this.vedioCourseFiles = vedioCourseFiles;
    }

    public TrainerProfileModel getTrainerResponse() {
        return trainerResponse;
    }

    public void setTrainerResponse(TrainerProfileModel trainerResponse) {
        this.trainerResponse = trainerResponse;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public List<AddDateTimeModel> getLiveCourseDateRangeRequests() {
        return liveCourseDateRangeRequests;
    }

    public void setLiveCourseDateRangeRequests(List<AddDateTimeModel> liveCourseDateRangeRequests) {
        this.liveCourseDateRangeRequests = liveCourseDateRangeRequests;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    public String getOverviewVideo() {
        return overviewVideo;
    }

    public void setOverviewVideo(String overviewVideo) {
        this.overviewVideo = overviewVideo;
    }

    public List<VedioCourseFilesModel> getVedioCourseFilesResponse() {
        return vedioCourseFilesResponse;
    }

    public void setVedioCourseFilesResponse(List<VedioCourseFilesModel> vedioCourseFilesResponse) {
        this.vedioCourseFilesResponse = vedioCourseFilesResponse;
    }

    @Override
    public String toString() {
        return "{" +
                "liveCourseId=" + liveCourseId +
                ", trainerId=" + trainerId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseSubject='" + courseSubject + '\'' +
                ", price=" + price +
                ", liveDate='" + liveDate + '\'' +
                ", stramingLiveDate=" + stramingLiveDate +
                ", liveTime='" + liveTime + '\'' +
                ", meetingLink='" + meetingLink + '\'' +
                ", meetingPassword='" + meetingPassword + '\'' +
                ", meetingDetails='" + meetingDetails + '\'' +
                ", meetingDescription='" + meetingDescription + '\'' +
                ", uploadLink='" + uploadLink + '\'' +
                ", remainingTime='" + remainingTime + '\'' +
                ", trainerResponse=" + trainerResponse +
                ", courseType='" + courseType + '\'' +
                ", vedioCourseFiles=" + vedioCourseFiles +
                ", vedioCourseFilesResponse=" + vedioCourseFilesResponse +
                ", liveCourseDateRangeRequests=" + liveCourseDateRangeRequests +
                ", subject=" + subject +
                ", overviewVideo='" + overviewVideo + '\'' +
                '}';
    }
}