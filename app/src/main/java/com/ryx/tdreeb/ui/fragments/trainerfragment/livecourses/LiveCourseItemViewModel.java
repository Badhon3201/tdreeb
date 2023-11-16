package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;

public class LiveCourseItemViewModel {

    public final ObservableField<String> course_subject;
    public final ObservableField<String> course_title;
    public final ObservableField<String> hr;
    public final ObservableField<String> price;
    public final LiveCourseModel mLiveCourseModel;
    public final LiveCourseCallBack mLiveCourseCallBack;

    public LiveCourseItemViewModel(LiveCourseModel mLiveCourseModel, LiveCourseCallBack mLiveCourseCallBack) {
        this.mLiveCourseModel = mLiveCourseModel;
        this.mLiveCourseCallBack = mLiveCourseCallBack;
        this.course_subject = new ObservableField<>(" - " + mLiveCourseModel.getCourseSubject());
        this.course_title = new ObservableField<>(mLiveCourseModel.getCourseTitle());
        this.price = new ObservableField<>("AED " + mLiveCourseModel.getPrice());
        this.hr = new ObservableField<>(mLiveCourseModel.getRemainingTime());
    }

    public void onClickEdit() {
        if (mLiveCourseCallBack != null) {
            mLiveCourseCallBack.onClickEdit(mLiveCourseModel);
        }
    }
}