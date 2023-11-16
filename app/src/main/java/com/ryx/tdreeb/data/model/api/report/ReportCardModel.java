package com.ryx.tdreeb.data.model.api.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.sessionmodel.UserModelTwo;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;

public class ReportCardModel {
    @SerializedName("reportCardId")
    @Expose
    private Integer reportCardId;
    @SerializedName("reportCardKey")
    @Expose
    private String reportCardKey;
    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("courseType")
    @Expose
    private String courseType;
    @SerializedName("marks")
    @Expose
    private Double marks;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("trainerCourse")
    @Expose
    private SubjectModel trainerCourse;
    @SerializedName("liveorVideoCourse")
    @Expose
    private Object liveorVideoCourse;
    @SerializedName("student")
    @Expose
    private UserModelTwo student;
    @SerializedName("trainerTrainingsCourseResponse")
    @Expose
    private Object trainerTrainingsCourseResponse;

    public Integer getReportCardId() {
        return reportCardId;
    }

    public void setReportCardId(Integer reportCardId) {
        this.reportCardId = reportCardId;
    }

    public String getReportCardKey() {
        return reportCardKey;
    }

    public void setReportCardKey(String reportCardKey) {
        this.reportCardKey = reportCardKey;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public SubjectModel getTrainerCourse() {
        return trainerCourse;
    }

    public void setTrainerCourse(SubjectModel trainerCourse) {
        this.trainerCourse = trainerCourse;
    }

    public Object getLiveorVideoCourse() {
        return liveorVideoCourse;
    }

    public void setLiveorVideoCourse(Object liveorVideoCourse) {
        this.liveorVideoCourse = liveorVideoCourse;
    }

    public UserModelTwo getStudent() {
        return student;
    }

    public void setStudent(UserModelTwo student) {
        this.student = student;
    }

    public Object getTrainerTrainingsCourseResponse() {
        return trainerTrainingsCourseResponse;
    }

    public void setTrainerTrainingsCourseResponse(Object trainerTrainingsCourseResponse) {
        this.trainerTrainingsCourseResponse = trainerTrainingsCourseResponse;
    }

}