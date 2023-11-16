package com.ryx.tdreeb.data.model.api.bookingmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.TrainingModel.TrainingModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.PaymentModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.TrainerProfileModelTwo;
import com.ryx.tdreeb.data.model.api.sessionmodel.UserModelTwo;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileModel;

import java.util.List;

public class SessionModel implements Parcelable {

    @SerializedName("sessionId")
    @Expose
    private Integer sessionId;
    @SerializedName("sessionType")
    @Expose
    private String sessionType;
    @SerializedName("operatingType")
    @Expose
    private String operatingType;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("numberOfStudent")
    @Expose
    private String numberOfStudent;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("isGroupSession")
    @Expose
    private Boolean isGroupSession;
    @SerializedName("isApprovedByTrainer")
    @Expose
    private Boolean isApprovedByTrainer;
    @SerializedName("isAccepted")
    @Expose
    private Boolean isAccepted;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("invoiceNumber")
    @Expose
    private String invoiceNumber;
    @SerializedName("payment")
    @Expose
    private PaymentModel payment;
    @SerializedName("bookBy")
    @Expose
    private UserModelTwo bookBy;
    @SerializedName("bookFor")
    @Expose
    private UserModelTwo bookFor;
    @SerializedName("trainer")
    @Expose
    private TrainerProfileModelTwo trainer;
    @SerializedName("trainerCourse")
    @Expose
    private SubjectModel trainerCourse;
    @SerializedName("sessionLogs")
    @Expose
    private List<SessionLogsModel> sessionLogs = null;
    @SerializedName("liveorVideoCourse")
    @Expose
    private LiveCourseModel liveorVideoCourse;
    @SerializedName("trainerResourcesRequest")
    @Expose
    private ResourceModel trainerResourcesRequest;

    @SerializedName("subject")
    @Expose
    private SubjectModel subject;


    @SerializedName("trainerTrainingsCourseResponse")
    @Expose
    private TrainingModel trainerTrainingsCourseResponse;


    protected SessionModel(Parcel in) {
        if (in.readByte() == 0) {
            sessionId = null;
        } else {
            sessionId = in.readInt();
        }
        sessionType = in.readString();
        operatingType = in.readString();
        date = in.readString();
        time = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        numberOfStudent = in.readString();
        note = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        byte tmpIsGroupSession = in.readByte();
        isGroupSession = tmpIsGroupSession == 0 ? null : tmpIsGroupSession == 1;
        byte tmpIsApprovedByTrainer = in.readByte();
        isApprovedByTrainer = tmpIsApprovedByTrainer == 0 ? null : tmpIsApprovedByTrainer == 1;
        byte tmpIsAccepted = in.readByte();
        isAccepted = tmpIsAccepted == 0 ? null : tmpIsAccepted == 1;
        paymentType = in.readString();
        invoiceNumber = in.readString();
        payment = in.readParcelable(PaymentModel.class.getClassLoader());
        bookBy = in.readParcelable(UserModelTwo.class.getClassLoader());
        bookFor = in.readParcelable(UserModelTwo.class.getClassLoader());
        trainer = in.readParcelable(TrainerProfileModelTwo.class.getClassLoader());
        trainerCourse = in.readParcelable(SubjectModel.class.getClassLoader());
        sessionLogs = in.createTypedArrayList(SessionLogsModel.CREATOR);
        liveorVideoCourse = in.readParcelable(LiveCourseModel.class.getClassLoader());
        trainerResourcesRequest = in.readParcelable(ResourceModel.class.getClassLoader());
        subject = in.readParcelable(SubjectModel.class.getClassLoader());
        trainerTrainingsCourseResponse = in.readParcelable(TrainingModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sessionId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sessionId);
        }
        dest.writeString(sessionType);
        dest.writeString(operatingType);
        dest.writeString(date);
        dest.writeString(time);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(numberOfStudent);
        dest.writeString(note);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeByte((byte) (isGroupSession == null ? 0 : isGroupSession ? 1 : 2));
        dest.writeByte((byte) (isApprovedByTrainer == null ? 0 : isApprovedByTrainer ? 1 : 2));
        dest.writeByte((byte) (isAccepted == null ? 0 : isAccepted ? 1 : 2));
        dest.writeString(paymentType);
        dest.writeString(invoiceNumber);
        dest.writeParcelable(payment, flags);
        dest.writeParcelable(bookBy, flags);
        dest.writeParcelable(bookFor, flags);
        dest.writeParcelable(trainer, flags);
        dest.writeParcelable(trainerCourse, flags);
        dest.writeTypedList(sessionLogs);
        dest.writeParcelable(liveorVideoCourse, flags);
        dest.writeParcelable(trainerResourcesRequest, flags);
        dest.writeParcelable(subject, flags);
        dest.writeParcelable(trainerTrainingsCourseResponse, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SessionModel> CREATOR = new Creator<SessionModel>() {
        @Override
        public SessionModel createFromParcel(Parcel in) {
            return new SessionModel(in);
        }

        @Override
        public SessionModel[] newArray(int size) {
            return new SessionModel[size];
        }
    };

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getOperatingType() {
        return operatingType;
    }

    public void setOperatingType(String operatingType) {
        this.operatingType = operatingType;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getIsGroupSession() {
        return isGroupSession;
    }

    public void setIsGroupSession(Boolean isGroupSession) {
        this.isGroupSession = isGroupSession;
    }

    public Boolean getIsApprovedByTrainer() {
        return isApprovedByTrainer;
    }

    public void setIsApprovedByTrainer(Boolean isApprovedByTrainer) {
        this.isApprovedByTrainer = isApprovedByTrainer;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
    }

    public UserModelTwo getBookBy() {
        return bookBy;
    }

    public void setBookBy(UserModelTwo bookBy) {
        this.bookBy = bookBy;
    }

    public UserModelTwo getBookFor() {
        return bookFor;
    }

    public void setBookFor(UserModelTwo bookFor) {
        this.bookFor = bookFor;
    }

    public TrainerProfileModelTwo getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfileModelTwo trainer) {
        this.trainer = trainer;
    }

    public SubjectModel getTrainerCourse() {
        return trainerCourse;
    }

    public void setTrainerCourse(SubjectModel trainerCourse) {
        this.trainerCourse = trainerCourse;
    }

    public List<SessionLogsModel> getSessionLogs() {
        return sessionLogs;
    }

    public void setSessionLogs(List<SessionLogsModel> sessionLogs) {
        this.sessionLogs = sessionLogs;
    }

    public LiveCourseModel getLiveorVideoCourse() {
        return liveorVideoCourse;
    }

    public void setLiveorVideoCourse(LiveCourseModel liveorVideoCourse) {
        this.liveorVideoCourse = liveorVideoCourse;
    }

    public ResourceModel getTrainerResourcesRequest() {
        return trainerResourcesRequest;
    }

    public void setTrainerResourcesRequest(ResourceModel trainerResourcesRequest) {
        this.trainerResourcesRequest = trainerResourcesRequest;
    }

    public TrainingModel getTrainerTrainingsCourseResponse() {
        return trainerTrainingsCourseResponse;
    }

    public void setTrainerTrainingsCourseResponse(TrainingModel trainerTrainingsCourseResponse) {
        this.trainerTrainingsCourseResponse = trainerTrainingsCourseResponse;
    }

    public Boolean getGroupSession() {
        return isGroupSession;
    }

    public void setGroupSession(Boolean groupSession) {
        isGroupSession = groupSession;
    }

    public Boolean getApprovedByTrainer() {
        return isApprovedByTrainer;
    }

    public void setApprovedByTrainer(Boolean approvedByTrainer) {
        isApprovedByTrainer = approvedByTrainer;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "{" +
                "sessionId=" + sessionId +
                ", sessionType='" + sessionType + '\'' +
                ", operatingType='" + operatingType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration=" + duration +
                ", numberOfStudent='" + numberOfStudent + '\'' +
                ", note='" + note + '\'' +
                ", rating=" + rating +
                ", isGroupSession=" + isGroupSession +
                ", isApprovedByTrainer=" + isApprovedByTrainer +
                ", isAccepted=" + isAccepted +
                ", paymentType='" + paymentType + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", payment=" + payment +
                ", bookBy=" + bookBy +
                ", bookFor=" + bookFor +
                ", trainer=" + trainer +
                ", trainerCourse=" + trainerCourse +
                ", sessionLogs=" + sessionLogs +
                ", liveorVideoCourse=" + liveorVideoCourse +
                ", trainerResourcesRequest=" + trainerResourcesRequest +
                ", subject=" + subject +
                ", trainerTrainingsCourseResponse=" + trainerTrainingsCourseResponse +
                '}';
    }
}