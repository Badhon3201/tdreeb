package com.ryx.tdreeb.data.model.api.subjectmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectModel implements Parcelable {

    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("subSubject")
    @Expose
    private List<SubjectModel> subSubject;

    @SerializedName("subSubjectResponse")
    @Expose
    private SubjectModel subSubjectResponse;

    public SubjectModel() {
    }

    protected SubjectModel(Parcel in) {
        if (in.readByte() == 0) {
            subjectId = null;
        } else {
            subjectId = in.readInt();
        }
        subjectName = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        image = in.readString();
        status = in.readString();
        subSubject = in.createTypedArrayList(SubjectModel.CREATOR);
        subSubjectResponse = in.readParcelable(SubjectModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (subjectId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subjectId);
        }
        dest.writeString(subjectName);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(status);
        dest.writeTypedList(subSubject);
        dest.writeParcelable(subSubjectResponse, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubjectModel> CREATOR = new Creator<SubjectModel>() {
        @Override
        public SubjectModel createFromParcel(Parcel in) {
            return new SubjectModel(in);
        }

        @Override
        public SubjectModel[] newArray(int size) {
            return new SubjectModel[size];
        }
    };

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubjectModel> getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(List<SubjectModel> subSubject) {
        this.subSubject = subSubject;
    }

    public SubjectModel getSubSubjectResponse() {
        return subSubjectResponse;
    }

    public void setSubSubjectResponse(SubjectModel subSubjectResponse) {
        this.subSubjectResponse = subSubjectResponse;
    }

    @Override
    public String toString() {
        return "{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", subSubject=" + subSubject +
                ", subSubjectResponse=" + subSubjectResponse +
                '}';
    }
}