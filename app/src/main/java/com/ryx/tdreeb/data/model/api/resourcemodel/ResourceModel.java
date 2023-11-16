package com.ryx.tdreeb.data.model.api.resourcemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileModel;

import java.util.List;

public class ResourceModel implements Parcelable {

    @SerializedName("trainerResourcesId")
    @Expose
    private Integer trainerResourcesId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("trainerId")
    @Expose
    private Integer trainerId;
    @SerializedName("trainer")
    @Expose
    private TrainerProfileModel trainer;
    @SerializedName("resourceName")
    @Expose
    private String resourceName;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("curriculumId")
    @Expose
    private Integer curriculumId;
    @SerializedName("curriculum")
    @Expose
    private CurriculumModel curriculum;
    @SerializedName("gradeId")
    @Expose
    private Integer gradeId;
    @SerializedName("grade")
    @Expose
    private GradeModel grade;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("subject")
    @Expose
    private SubjectModel subject;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("resourceDetails")
    @Expose
    private List<ResourceDetailModel> resourceDetails;
    @SerializedName("isFavorite")
    @Expose
    private boolean isFavorite;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("ratingCounter")
    @Expose
    private int ratingCounter;

    @SerializedName("resourceImage")
    @Expose
    private String resourceImage;

    protected ResourceModel(Parcel in) {
        if (in.readByte() == 0) {
            trainerResourcesId = null;
        } else {
            trainerResourcesId = in.readInt();
        }
        key = in.readString();
        if (in.readByte() == 0) {
            trainerId = null;
        } else {
            trainerId = in.readInt();
        }
        trainer = in.readParcelable(TrainerProfileModel.class.getClassLoader());
        resourceName = in.readString();
        details = in.readString();
        if (in.readByte() == 0) {
            curriculumId = null;
        } else {
            curriculumId = in.readInt();
        }
        curriculum = in.readParcelable(CurriculumModel.class.getClassLoader());
        if (in.readByte() == 0) {
            gradeId = null;
        } else {
            gradeId = in.readInt();
        }
        grade = in.readParcelable(GradeModel.class.getClassLoader());
        if (in.readByte() == 0) {
            subjectId = null;
        } else {
            subjectId = in.readInt();
        }
        subject = in.readParcelable(SubjectModel.class.getClassLoader());
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        resourceDetails = in.createTypedArrayList(ResourceDetailModel.CREATOR);
        isFavorite = in.readByte() != 0;
        rating = in.readDouble();
        ratingCounter = in.readInt();
        resourceImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (trainerResourcesId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerResourcesId);
        }
        dest.writeString(key);
        if (trainerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trainerId);
        }
        dest.writeParcelable(trainer, flags);
        dest.writeString(resourceName);
        dest.writeString(details);
        if (curriculumId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(curriculumId);
        }
        dest.writeParcelable(curriculum, flags);
        if (gradeId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gradeId);
        }
        dest.writeParcelable(grade, flags);
        if (subjectId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subjectId);
        }
        dest.writeParcelable(subject, flags);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeTypedList(resourceDetails);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeDouble(rating);
        dest.writeInt(ratingCounter);
        dest.writeString(resourceImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResourceModel> CREATOR = new Creator<ResourceModel>() {
        @Override
        public ResourceModel createFromParcel(Parcel in) {
            return new ResourceModel(in);
        }

        @Override
        public ResourceModel[] newArray(int size) {
            return new ResourceModel[size];
        }
    };

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public Integer getTrainerResourcesId() {
        return trainerResourcesId;
    }

    public void setTrainerResourcesId(Integer trainerResourcesId) {
        this.trainerResourcesId = trainerResourcesId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public TrainerProfileModel getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfileModel trainer) {
        this.trainer = trainer;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Integer curriculumId) {
        this.curriculumId = curriculumId;
    }

    public CurriculumModel getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(CurriculumModel curriculum) {
        this.curriculum = curriculum;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public GradeModel getGrade() {
        return grade;
    }

    public void setGrade(GradeModel grade) {
        this.grade = grade;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ResourceDetailModel> getResourceDetails() {
        return resourceDetails;
    }

    public void setResourceDetails(List<ResourceDetailModel> resourceDetails) {
        this.resourceDetails = resourceDetails;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCounter() {
        return ratingCounter;
    }

    public void setRatingCounter(int ratingCounter) {
        this.ratingCounter = ratingCounter;
    }

    @Override
    public String toString() {
        return "{" +
                "trainerResourcesId=" + trainerResourcesId +
                ", key='" + key + '\'' +
                ", trainerId=" + trainerId +
                ", trainer=" + trainer +
                ", resourceName='" + resourceName + '\'' +
                ", details='" + details + '\'' +
                ", curriculumId=" + curriculumId +
                ", curriculum=" + curriculum +
                ", gradeId=" + gradeId +
                ", grade=" + grade +
                ", subjectId=" + subjectId +
                ", subject=" + subject +
                ", price=" + price +
                ", resourceDetails=" + resourceDetails +
                ", isFavorite=" + isFavorite +
                ", rating=" + rating +
                ", ratingCounter=" + ratingCounter +
                ", resourceImage=" + resourceImage +
                '}';
    }
}