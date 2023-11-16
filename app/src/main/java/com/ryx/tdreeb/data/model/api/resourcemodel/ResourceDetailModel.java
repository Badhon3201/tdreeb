package com.ryx.tdreeb.data.model.api.resourcemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceDetailModel implements Parcelable {
    @SerializedName("trainerResourecDetailsId")
    @Expose
    private Integer trainerResourecDetailsId;
    @SerializedName("filePath")
    @Expose
    private String filePath;

    protected ResourceDetailModel(Parcel in) {
        if (in.readByte() == 0) {
            trainerResourecDetailsId = null;
        } else {
            trainerResourecDetailsId = in.readInt();
        }
        filePath = in.readString();
    }

    public static final Creator<ResourceDetailModel> CREATOR = new Creator<ResourceDetailModel>() {
        @Override
        public ResourceDetailModel createFromParcel(Parcel in) {
            return new ResourceDetailModel(in);
        }

        @Override
        public ResourceDetailModel[] newArray(int size) {
            return new ResourceDetailModel[size];
        }
    };

    public Integer getTrainerResourecDetailsId() {
        return trainerResourecDetailsId;
    }

    public void setTrainerResourecDetailsId(Integer trainerResourecDetailsId) {
        this.trainerResourecDetailsId = trainerResourecDetailsId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (trainerResourecDetailsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerResourecDetailsId);
        }
        dest.writeString(filePath);
    }

    public ResourceDetailModel() {

    }

    public ResourceDetailModel(Integer trainerResourecDetailsId, String filePath) {
        this.trainerResourecDetailsId = trainerResourecDetailsId;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ResourceDetailModel{" +
                "trainerResourecDetailsId=" + trainerResourecDetailsId +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}