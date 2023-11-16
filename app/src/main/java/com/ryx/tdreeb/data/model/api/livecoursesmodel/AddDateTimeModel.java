package com.ryx.tdreeb.data.model.api.livecoursesmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDateTimeModel implements Parcelable {

    @SerializedName("liveDate")
    @Expose
    private String liveDate;
    @SerializedName("liveTime")
    @Expose
    private String liveTime;

    public AddDateTimeModel() {
    }

    protected AddDateTimeModel(Parcel in) {
        liveDate = in.readString();
        liveTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(liveDate);
        dest.writeString(liveTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddDateTimeModel> CREATOR = new Creator<AddDateTimeModel>() {
        @Override
        public AddDateTimeModel createFromParcel(Parcel in) {
            return new AddDateTimeModel(in);
        }

        @Override
        public AddDateTimeModel[] newArray(int size) {
            return new AddDateTimeModel[size];
        }
    };

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public String toString() {
        return "{" +
                "liveDate='" + liveDate + '\'' +
                ", liveTime='" + liveTime + '\'' +
                '}';
    }
}
