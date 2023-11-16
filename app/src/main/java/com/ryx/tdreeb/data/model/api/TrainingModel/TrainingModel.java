package com.ryx.tdreeb.data.model.api.TrainingModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainingModel implements Parcelable {
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("courseKey")
    @Expose
    private String courseKey;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;

    protected TrainingModel(Parcel in) {
        if (in.readByte() == 0) {
            courseId = null;
        } else {
            courseId = in.readInt();
        }
        courseKey = in.readString();
        courseName = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (courseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(courseId);
        }
        dest.writeString(courseKey);
        dest.writeString(courseName);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrainingModel> CREATOR = new Creator<TrainingModel>() {
        @Override
        public TrainingModel createFromParcel(Parcel in) {
            return new TrainingModel(in);
        }

        @Override
        public TrainingModel[] newArray(int size) {
            return new TrainingModel[size];
        }
    };

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    @Override
    public String toString() {
        return "{" +
                "courseId=" + courseId +
                ", courseKey='" + courseKey + '\'' +
                ", courseName='" + courseName + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}