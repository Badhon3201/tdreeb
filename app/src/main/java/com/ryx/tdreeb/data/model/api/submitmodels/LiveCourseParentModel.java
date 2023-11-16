package com.ryx.tdreeb.data.model.api.submitmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveCourseParentModel implements Parcelable {

    @SerializedName("sessionMasterId")
    @Expose
    private Integer sessionMasterId;
    @SerializedName("sessionType")
    @Expose
    private String sessionType;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("subTotal")
    @Expose
    private Double subTotal;
    @SerializedName("vat")
    @Expose
    private Double vat;
    @SerializedName("grandTotal")
    @Expose
    private Double grandTotal;
    @SerializedName("acceptedBy")
    @Expose
    private Integer acceptedBy;
    @SerializedName("bookByType")
    @Expose
    private String bookByType;
    @SerializedName("bookById")
    @Expose
    private Integer bookById;
    @SerializedName("bookForType")
    @Expose
    private String bookForType;
    @SerializedName("bookForId")
    @Expose
    private Integer bookForId;
    @SerializedName("isGroupSession")
    @Expose
    private Boolean isGroupSession;
    @SerializedName("numberOfStudent")
    @Expose
    private String numberOfStudent;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("trainersApprove")
    @Expose
    private String trainersApprove;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("trainerCourseId")
    @Expose
    private Integer trainerCourseId;
    @SerializedName("trainerOperatingType")
    @Expose
    private String trainerOperatingType;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("courseType")
    @Expose
    private String courseType;

    public LiveCourseParentModel() {
    }

    protected LiveCourseParentModel(Parcel in) {
        if (in.readByte() == 0) {
            sessionMasterId = null;
        } else {
            sessionMasterId = in.readInt();
        }
        sessionType = in.readString();
        date = in.readString();
        time = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readDouble();
        }
        if (in.readByte() == 0) {
            subTotal = null;
        } else {
            subTotal = in.readDouble();
        }
        if (in.readByte() == 0) {
            vat = null;
        } else {
            vat = in.readDouble();
        }
        if (in.readByte() == 0) {
            grandTotal = null;
        } else {
            grandTotal = in.readDouble();
        }
        if (in.readByte() == 0) {
            acceptedBy = null;
        } else {
            acceptedBy = in.readInt();
        }
        bookByType = in.readString();
        if (in.readByte() == 0) {
            bookById = null;
        } else {
            bookById = in.readInt();
        }
        bookForType = in.readString();
        if (in.readByte() == 0) {
            bookForId = null;
        } else {
            bookForId = in.readInt();
        }
        byte tmpIsGroupSession = in.readByte();
        isGroupSession = tmpIsGroupSession == 0 ? null : tmpIsGroupSession == 1;
        numberOfStudent = in.readString();
        note = in.readString();
        trainersApprove = in.readString();
        status = in.readString();
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        if (in.readByte() == 0) {
            trainerCourseId = null;
        } else {
            trainerCourseId = in.readInt();
        }
        trainerOperatingType = in.readString();
        paymentType = in.readString();
        courseType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sessionMasterId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sessionMasterId);
        }
        dest.writeString(sessionType);
        dest.writeString(date);
        dest.writeString(time);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rate);
        }
        if (subTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(subTotal);
        }
        if (vat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vat);
        }
        if (grandTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(grandTotal);
        }
        if (acceptedBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(acceptedBy);
        }
        dest.writeString(bookByType);
        if (bookById == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bookById);
        }
        dest.writeString(bookForType);
        if (bookForId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bookForId);
        }
        dest.writeByte((byte) (isGroupSession == null ? 0 : isGroupSession ? 1 : 2));
        dest.writeString(numberOfStudent);
        dest.writeString(note);
        dest.writeString(trainersApprove);
        dest.writeString(status);
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        if (trainerCourseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerCourseId);
        }
        dest.writeString(trainerOperatingType);
        dest.writeString(paymentType);
        dest.writeString(courseType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveCourseParentModel> CREATOR = new Creator<LiveCourseParentModel>() {
        @Override
        public LiveCourseParentModel createFromParcel(Parcel in) {
            return new LiveCourseParentModel(in);
        }

        @Override
        public LiveCourseParentModel[] newArray(int size) {
            return new LiveCourseParentModel[size];
        }
    };

    public Integer getSessionMasterId() {
        return sessionMasterId;
    }

    public void setSessionMasterId(Integer sessionMasterId) {
        this.sessionMasterId = sessionMasterId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(Integer acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getBookByType() {
        return bookByType;
    }

    public void setBookByType(String bookByType) {
        this.bookByType = bookByType;
    }

    public Integer getBookById() {
        return bookById;
    }

    public void setBookById(Integer bookById) {
        this.bookById = bookById;
    }

    public String getBookForType() {
        return bookForType;
    }

    public void setBookForType(String bookForType) {
        this.bookForType = bookForType;
    }

    public Integer getBookForId() {
        return bookForId;
    }

    public void setBookForId(Integer bookForId) {
        this.bookForId = bookForId;
    }

    public Boolean getIsGroupSession() {
        return isGroupSession;
    }

    public void setIsGroupSession(Boolean isGroupSession) {
        this.isGroupSession = isGroupSession;
    }

    public String getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(String numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTrainersApprove() {
        return trainersApprove;
    }

    public void setTrainersApprove(String trainersApprove) {
        this.trainersApprove = trainersApprove;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getTrainerCourseId() {
        return trainerCourseId;
    }

    public void setTrainerCourseId(Integer trainerCourseId) {
        this.trainerCourseId = trainerCourseId;
    }

    public String getTrainerOperatingType() {
        return trainerOperatingType;
    }

    public void setTrainerOperatingType(String trainerOperatingType) {
        this.trainerOperatingType = trainerOperatingType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    @Override
    public String toString() {
        return "{" +
                "sessionMasterId=" + sessionMasterId +
                ", sessionType='" + sessionType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration=" + duration +
                ", rate=" + rate +
                ", subTotal=" + subTotal +
                ", vat=" + vat +
                ", grandTotal=" + grandTotal +
                ", acceptedBy=" + acceptedBy +
                ", bookByType='" + bookByType + '\'' +
                ", bookById=" + bookById +
                ", bookForType='" + bookForType + '\'' +
                ", bookForId=" + bookForId +
                ", isGroupSession=" + isGroupSession +
                ", numberOfStudent='" + numberOfStudent + '\'' +
                ", note='" + note + '\'' +
                ", trainersApprove='" + trainersApprove + '\'' +
                ", status='" + status + '\'' +
                ", trainerId=" + trainerId +
                ", trainerCourseId=" + trainerCourseId +
                ", trainerOperatingType='" + trainerOperatingType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", courseType='" + courseType + '\'' +
                '}';
    }
}