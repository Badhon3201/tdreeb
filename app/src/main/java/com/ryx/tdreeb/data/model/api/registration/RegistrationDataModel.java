package com.ryx.tdreeb.data.model.api.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationDataModel {

    @SerializedName("user")
    @Expose
    private UserModel user;

    @SerializedName("parent")
    @Expose
    private UserModel parent;

    @SerializedName("student")
    @Expose
    private UserModel student;

    public UserModel getParent() {
        return parent;
    }

    public void setParent(UserModel parent) {
        this.parent = parent;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getStudent() {
        return student;
    }

    public void setStudent(UserModel student) {
        this.student = student;
    }
}