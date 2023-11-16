package com.ryx.tdreeb.data.model.api.getcoursemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.TrainerProfileModelTwo;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;

import java.util.List;

public class CourseModel implements Parcelable {

    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("trainerCourseId")
    @Expose
    private Integer trainerCourseId;
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("curriculums")
    @Expose
    private List<CurriculumModel> curriculums = null;
    @SerializedName("grades")
    @Expose
    private List<GradeModel> grades = null;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("trainer")
    @Expose
    private TrainerProfileModelTwo trainer;
    @SerializedName("subject")
    @Expose
    private SubjectModel subject;

    protected CourseModel(Parcel in) {
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
        if (in.readByte() == 0) {
            courseId = null;
        } else {
            courseId = in.readInt();
        }
        subjectName = in.readString();
        if (in.readByte() == 0) {
            subjectId = null;
        } else {
            subjectId = in.readInt();
        }
        image = in.readString();
        curriculums = in.createTypedArrayList(CurriculumModel.CREATOR);
        grades = in.createTypedArrayList(GradeModel.CREATOR);
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        trainer = in.readParcelable(TrainerProfileModelTwo.class.getClassLoader());
        subject = in.readParcelable(SubjectModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        if (courseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(courseId);
        }
        dest.writeString(subjectName);
        if (subjectId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subjectId);
        }
        dest.writeString(image);
        dest.writeTypedList(curriculums);
        dest.writeTypedList(grades);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeParcelable(trainer, flags);
        dest.writeParcelable(subject, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseModel> CREATOR = new Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CurriculumModel> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<CurriculumModel> curriculums) {
        this.curriculums = curriculums;
    }

    public List<GradeModel> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeModel> grades) {
        this.grades = grades;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public TrainerProfileModelTwo getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfileModelTwo trainer) {
        this.trainer = trainer;
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
                "trainerId=" + trainerId +
                ", trainerCourseId=" + trainerCourseId +
                ", courseId=" + courseId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectId=" + subjectId +
                ", image='" + image + '\'' +
                ", curriculums=" + curriculums +
                ", grades=" + grades +
                ", price=" + price +
                ", rating=" + rating +
                ", trainer=" + trainer +
                ", subject=" + subject +
                '}';
    }
}