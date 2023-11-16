package com.ryx.tdreeb.data.model.api.laguagemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageModel implements Parcelable {
    @SerializedName("languageId")
    @Expose
    private Integer languageId;
    @SerializedName("languageCode")
    @Expose
    private String languageCode;
    @SerializedName("languageName")
    @Expose
    private String languageName;

    public LanguageModel(){

    }
    protected LanguageModel(Parcel in) {
        if (in.readByte() == 0) {
            languageId = null;
        } else {
            languageId = in.readInt();
        }
        languageCode = in.readString();
        languageName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (languageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(languageId);
        }
        dest.writeString(languageCode);
        dest.writeString(languageName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LanguageModel> CREATOR = new Creator<LanguageModel>() {
        @Override
        public LanguageModel createFromParcel(Parcel in) {
            return new LanguageModel(in);
        }

        @Override
        public LanguageModel[] newArray(int size) {
            return new LanguageModel[size];
        }
    };

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return "{" +
                "languageId=" + languageId +
                ", languageCode='" + languageCode + '\'' +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}