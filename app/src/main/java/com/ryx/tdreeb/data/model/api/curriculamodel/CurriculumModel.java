package com.ryx.tdreeb.data.model.api.curriculamodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurriculumModel implements Parcelable {

    @SerializedName("curriculumId")
    @Expose
    private Integer curriculumId;
    @SerializedName("curriculumName")
    @Expose
    private String curriculumName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;

    public CurriculumModel() {
    }

    protected CurriculumModel(Parcel in) {
        if (in.readByte() == 0) {
            curriculumId = null;
        } else {
            curriculumId = in.readInt();
        }
        curriculumName = in.readString();
        description = in.readString();
        image = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (curriculumId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(curriculumId);
        }
        dest.writeString(curriculumName);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurriculumModel> CREATOR = new Creator<CurriculumModel>() {
        @Override
        public CurriculumModel createFromParcel(Parcel in) {
            return new CurriculumModel(in);
        }

        @Override
        public CurriculumModel[] newArray(int size) {
            return new CurriculumModel[size];
        }
    };

    public Integer getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Integer curriculumId) {
        this.curriculumId = curriculumId;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
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

}