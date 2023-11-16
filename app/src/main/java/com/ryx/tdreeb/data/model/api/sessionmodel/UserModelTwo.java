package com.ryx.tdreeb.data.model.api.sessionmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;

public class UserModelTwo implements Parcelable {

    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("userType")
    @Expose
    private Object userType;
    @SerializedName("mobileVerified")
    @Expose
    private Boolean mobileVerified;
    @SerializedName("verifiedCode")
    @Expose
    private Object verifiedCode;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("location")
    @Expose
    private LocationModel location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("spokenLanguageId")
    @Expose
    private Integer spokenLanguageId;
    @SerializedName("spokenLanguageIds")
    @Expose
    private Object spokenLanguageIds;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("getCountry")
    @Expose
    private Object getCountry;
    @SerializedName("getLanguage")
    @Expose
    private LanguageModel getLanguage;
    @SerializedName("nationality")
    @Expose
    private String nationality;

    protected UserModelTwo(Parcel in) {
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        image = in.readString();
        byte tmpMobileVerified = in.readByte();
        mobileVerified = tmpMobileVerified == 0 ? null : tmpMobileVerified == 1;
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        gender = in.readString();
        dob = in.readString();
        if (in.readByte() == 0) {
            countryId = null;
        } else {
            countryId = in.readInt();
        }
        location = in.readParcelable(LocationModel.class.getClassLoader());
        latitude = in.readString();
        longitude = in.readString();
        if (in.readByte() == 0) {
            spokenLanguageId = null;
        } else {
            spokenLanguageId = in.readInt();
        }
        password = in.readString();
        phoneNo = in.readString();
        getLanguage = in.readParcelable(LanguageModel.class.getClassLoader());
        nationality = in.readString();
    }

    public static final Creator<UserModelTwo> CREATOR = new Creator<UserModelTwo>() {
        @Override
        public UserModelTwo createFromParcel(Parcel in) {
            return new UserModelTwo(in);
        }

        @Override
        public UserModelTwo[] newArray(int size) {
            return new UserModelTwo[size];
        }
    };

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getUserType() {
        return userType;
    }

    public void setUserType(Object userType) {
        this.userType = userType;
    }

    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public Object getVerifiedCode() {
        return verifiedCode;
    }

    public void setVerifiedCode(Object verifiedCode) {
        this.verifiedCode = verifiedCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getSpokenLanguageId() {
        return spokenLanguageId;
    }

    public void setSpokenLanguageId(Integer spokenLanguageId) {
        this.spokenLanguageId = spokenLanguageId;
    }

    public Object getSpokenLanguageIds() {
        return spokenLanguageIds;
    }

    public void setSpokenLanguageIds(Object spokenLanguageIds) {
        this.spokenLanguageIds = spokenLanguageIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Object getGetCountry() {
        return getCountry;
    }

    public void setGetCountry(Object getCountry) {
        this.getCountry = getCountry;
    }

    public LanguageModel getGetLanguage() {
        return getLanguage;
    }

    public void setGetLanguage(LanguageModel getLanguage) {
        this.getLanguage = getLanguage;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public String toString() {
        return "{" +
                "trainerId=" + trainerId +
                ", parentId=" + parentId +
                ", id=" + id +
                ", name=" + name +
                ", image='" + image + '\'' +
                ", userType=" + userType +
                ", mobileVerified=" + mobileVerified +
                ", verifiedCode=" + verifiedCode +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", countryId=" + countryId +
                ", location='" + location + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", spokenLanguageId=" + spokenLanguageId +
                ", spokenLanguageIds=" + spokenLanguageIds +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", getCountry=" + getCountry +
                ", getLanguage=" + getLanguage +
                ", nationality='" + nationality + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(image);
        dest.writeByte((byte) (mobileVerified == null ? 0 : mobileVerified ? 1 : 2));
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(dob);
        if (countryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(countryId);
        }
        dest.writeParcelable(location, flags);
        dest.writeString(latitude);
        dest.writeString(longitude);
        if (spokenLanguageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(spokenLanguageId);
        }
        dest.writeString(password);
        dest.writeString(phoneNo);
        dest.writeParcelable(getLanguage, flags);
        dest.writeString(nationality);
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}