package com.ryx.tdreeb.data.model.api.gradesmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GradeModel implements Parcelable {

    @SerializedName("gradeId")
    @Expose
    private Integer gradeId;
    @SerializedName("gradeName")
    @Expose
    private String gradeName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;

    public GradeModel() {
    }

    protected GradeModel(Parcel in) {
        if (in.readByte() == 0) {
            gradeId = null;
        } else {
            gradeId = in.readInt();
        }
        gradeName = in.readString();
        description = in.readString();
        image = in.readString();
        status = in.readString();
    }

    public static final Creator<GradeModel> CREATOR = new Creator<GradeModel>() {
        @Override
        public GradeModel createFromParcel(Parcel in) {
            return new GradeModel(in);
        }

        @Override
        public GradeModel[] newArray(int size) {
            return new GradeModel[size];
        }
    };

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (gradeId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gradeId);
        }
        dest.writeString(gradeName);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(status);
    }

    @Override
    public String toString() {
        return "{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}