package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;

public class MyLiveCourseItemViewModel {
    public final ObservableField<String> teacherName;

    public final ObservableField<String> teacherImage;

    public final ObservableField<String> studentName;

    public final ObservableField<String> studentImage;

    public final ObservableField<String> subject;

    public final ObservableField<String> time;

    public final ObservableField<String> date;

    public final SessionModel mSessionModel;

    public final MyResourceCallBack mMyResourceCallBack;

    public MyLiveCourseItemViewModel(SessionModel mSessionModel, MyResourceCallBack mMyResourceCallBack) {
        this.mSessionModel = mSessionModel;
        this.mMyResourceCallBack = mMyResourceCallBack;
        this.teacherName = new ObservableField<>(mSessionModel.getTrainer().getName());
        this.teacherImage = new ObservableField<>(mSessionModel.getTrainer().getImage());
        this.studentName = new ObservableField<>(mSessionModel.getBookFor().getName());
        this.studentImage = new ObservableField<>(mSessionModel.getBookFor().getImage());
        if (mSessionModel.getTrainerCourse() != null) {
            this.subject = new ObservableField<>(" - " + mSessionModel.getTrainerCourse().getSubjectName());
        } else {
            this.subject = new ObservableField<>(" - " + mSessionModel.getLiveorVideoCourse().getCourseSubject());
        }
        this.time = new ObservableField<>(mSessionModel.getTime());
        this.date = new ObservableField<>(mSessionModel.getDate());
    }

    public void onClickItem() {
        if (mMyResourceCallBack != null) {
            mMyResourceCallBack.myResourceFvrt(mSessionModel);
        }
    }

}