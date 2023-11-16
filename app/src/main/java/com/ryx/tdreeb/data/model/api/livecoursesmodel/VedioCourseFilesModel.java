package com.ryx.tdreeb.data.model.api.livecoursesmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VedioCourseFilesModel implements Parcelable {

    @SerializedName("vedioCourseFileId")
    @Expose
    private Integer vedioCourseFileId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("liveCourseId")
    @Expose
    private Integer liveCourseId;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("isDelete")
    @Expose
    private Boolean isDelete;
    @SerializedName("title")
    @Expose
    private String title;

    protected VedioCourseFilesModel(Parcel in) {
        if (in.readByte() == 0) {
            vedioCourseFileId = null;
        } else {
            vedioCourseFileId = in.readInt();
        }
        key = in.readString();
        if (in.readByte() == 0) {
            liveCourseId = null;
        } else {
            liveCourseId = in.readInt();
        }
        filePath = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        if (in.readByte() == 0) {
            updatedBy = null;
        } else {
            updatedBy = in.readInt();
        }
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        byte tmpIsDelete = in.readByte();
        isDelete = tmpIsDelete == 0 ? null : tmpIsDelete == 1;
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (vedioCourseFileId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(vedioCourseFileId);
        }
        dest.writeString(key);
        if (liveCourseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(liveCourseId);
        }
        dest.writeString(filePath);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        if (updatedBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(updatedBy);
        }
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeByte((byte) (isDelete == null ? 0 : isDelete ? 1 : 2));
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VedioCourseFilesModel> CREATOR = new Creator<VedioCourseFilesModel>() {
        @Override
        public VedioCourseFilesModel createFromParcel(Parcel in) {
            return new VedioCourseFilesModel(in);
        }

        @Override
        public VedioCourseFilesModel[] newArray(int size) {
            return new VedioCourseFilesModel[size];
        }
    };

    public Integer getVedioCourseFileId() {
        return vedioCourseFileId;
    }

    public void setVedioCourseFileId(Integer vedioCourseFileId) {
        this.vedioCourseFileId = vedioCourseFileId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLiveCourseId() {
        return liveCourseId;
    }

    public void setLiveCourseId(Integer liveCourseId) {
        this.liveCourseId = liveCourseId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}