package com.ryx.tdreeb.data.model.api.addchildmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildenModel implements Parcelable {

    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("myChaildId")
    @Expose
    private Integer myChaildId;
    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
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
    @SerializedName("nationality")
    @Expose
    private String nationality;
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
    @SerializedName("spokenLanguage")
    @Expose
    private String spokenLanguage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("travelAnotherArea")
    @Expose
    private boolean travelAnotherArea;
    @SerializedName("streetAddress")
    @Expose
    private String streetAddress;
    @SerializedName("iban")
    @Expose
    private String iban;
    @SerializedName("bankName")
    @Expose
    private String bankName;

    private boolean isSelect;

    protected ChildenModel(Parcel in) {
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        if (in.readByte() == 0) {
            myChaildId = null;
        } else {
            myChaildId = in.readInt();
        }
        if (in.readByte() == 0) {
            studentId = null;
        } else {
            studentId = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        imagePath = in.readString();
        email = in.readString();
        gender = in.readString();
        dob = in.readString();
        if (in.readByte() == 0) {
            countryId = null;
        } else {
            countryId = in.readInt();
        }
        nationality = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        if (in.readByte() == 0) {
            spokenLanguageId = null;
        } else {
            spokenLanguageId = in.readInt();
        }
        spokenLanguage = in.readString();
        status = in.readString();
        userName = in.readString();
        password = in.readString();
        travelAnotherArea = in.readByte() != 0;
        streetAddress = in.readString();
        iban = in.readString();
        bankName = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        if (myChaildId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(myChaildId);
        }
        if (studentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(studentId);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(image);
        dest.writeString(imagePath);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(dob);
        if (countryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(countryId);
        }
        dest.writeString(nationality);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(longitude);
        if (spokenLanguageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(spokenLanguageId);
        }
        dest.writeString(spokenLanguage);
        dest.writeString(status);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeByte((byte) (travelAnotherArea ? 1 : 0));
        dest.writeString(streetAddress);
        dest.writeString(iban);
        dest.writeString(bankName);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChildenModel> CREATOR = new Creator<ChildenModel>() {
        @Override
        public ChildenModel createFromParcel(Parcel in) {
            return new ChildenModel(in);
        }

        @Override
        public ChildenModel[] newArray(int size) {
            return new ChildenModel[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public ChildenModel(){

    }

    public Integer getMyChaildId() {
        return myChaildId;
    }

    public void setMyChaildId(Integer myChaildId) {
        this.myChaildId = myChaildId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTravelAnotherArea() {
        return travelAnotherArea;
    }

    public void setTravelAnotherArea(boolean travelAnotherArea) {
        this.travelAnotherArea = travelAnotherArea;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "{" +
                "trainerId=" + trainerId +
                ", myChaildId=" + myChaildId +
                ", studentId=" + studentId +
                ", parentId=" + parentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", countryId=" + countryId +
                ", nationality='" + nationality + '\'' +
                ", location='" + location + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", spokenLanguageId=" + spokenLanguageId +
                ", spokenLanguage='" + spokenLanguage + '\'' +
                ", status='" + status + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", travelAnotherArea=" + travelAnotherArea +
                ", streetAddress='" + streetAddress + '\'' +
                ", iban='" + iban + '\'' +
                ", bankName='" + bankName + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}