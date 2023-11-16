package com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.CoursesCallBack;

public class TrainerListItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final ObservableField<String> rate;

    public final ObservableField<String> price;

    public final ObservableField<String> address;

    public final ObservableField<String> subject;

    public final CoursesCallBack mListener;

    public final CourseModel mCourseModel;


    public TrainerListItemViewModel(CourseModel mCourseModel, CoursesCallBack mListener) {
        this.mListener = mListener;
        this.mCourseModel = mCourseModel;
        this.image = new ObservableField<>(mCourseModel.getTrainer().getImage());
        this.name = new ObservableField<>(mCourseModel.getTrainer().getName());
        this.rate = new ObservableField<>(mCourseModel.getTrainer().getRating() + "");
        this.price = new ObservableField<>("AED: " + mCourseModel.getPrice());
        this.subject = new ObservableField<>(mCourseModel.getSubjectName());
        if (mCourseModel.getTrainer().getLocation().getStreetAddress() != null) {
            this.address = new ObservableField<>(mCourseModel.getTrainer().getLocation().getStreetAddress());
        } else {
            this.address = new ObservableField<>("");
        }

    }

    public void onClickItem() {
        if (mListener != null)
            mListener.onClickCourses(mCourseModel);
    }
}