package com.ryx.tdreeb.data.model.api.registration;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendRegistrationData implements Parcelable {

    @SerializedName("parentId")
    @Expose
    private int parentId;
    @SerializedName("studentId")
    @Expose
    private int studentId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userName")
    @Expose
    private String userName;
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
    private String location;
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
    private List<Integer> spokenLanguageIds = null;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("securityToken")
    @Expose
    private String securityToken;
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public SendRegistrationData() {
    }

    protected SendRegistrationData(Parcel in) {
        parentId = in.readInt();
        studentId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        email = in.readString();
        userName = in.readString();
        gender = in.readString();
        dob = in.readString();
        if (in.readByte() == 0) {
            countryId = null;
        } else {
            countryId = in.readInt();
        }
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        if (in.readByte() == 0) {
            spokenLanguageId = null;
        } else {
            spokenLanguageId = in.readInt();
        }
        password = in.readString();
        phoneNo = in.readString();
        securityToken = in.readString();
        firebaseToken = in.readString();
        status = in.readString();
        deviceId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parentId);
        dest.writeInt(studentId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(image);
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(gender);
        dest.writeString(dob);
        if (countryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(countryId);
        }
        dest.writeString(location);
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
        dest.writeString(securityToken);
        dest.writeString(firebaseToken);
        dest.writeString(status);
        dest.writeString(deviceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SendRegistrationData> CREATOR = new Creator<SendRegistrationData>() {
        @Override
        public SendRegistrationData createFromParcel(Parcel in) {
            return new SendRegistrationData(in);
        }

        @Override
        public SendRegistrationData[] newArray(int size) {
            return new SendRegistrationData[size];
        }
    };

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<Integer> getSpokenLanguageIds() {
        return spokenLanguageIds;
    }

    public void setSpokenLanguageIds(List<Integer> spokenLanguageIds) {
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

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "{" +
                "parentId=" + parentId +
                ", studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
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
                ", securityToken='" + securityToken + '\'' +
                ", firebaseToken='" + firebaseToken + '\'' +
                ", status='" + status + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}