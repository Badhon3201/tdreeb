package com.ryx.tdreeb.data.model.api.getcoursemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;

import java.util.List;

public class CoursesDataModel {
    @SerializedName("courses")
    @Expose
    private List<CourseModel> courses = null;

    @SerializedName("session")
    @Expose
    private SessionModel session;

    public List<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseModel> courses) {
        this.courses = courses;
    }

    public SessionModel getSession() {
        return session;
    }

    public void setSession(SessionModel session) {
        this.session = session;
    }
}