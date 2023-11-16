package com.ryx.tdreeb.ui.fragments.trainerfragment.home;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.utils.CommonUtils;


public class TrainerHomeSessionItemViewModel {

    public final ObservableField<String> student_image;
    public final ObservableField<String> teacher_image;
    public final ObservableField<String> teacher_name;
    public final ObservableField<String> teacher_address;
    public final ObservableField<String> student_name;
    public final ObservableField<String> student_subject;
    public final ObservableField<String> time;
    public final ObservableField<String> remain_time;

    public final SessionModel mSessionModel;
    public final MyResourceCallBack mMyResourceCallBack;

    public TrainerHomeSessionItemViewModel(SessionModel mSessionModel, MyResourceCallBack mMyResourceCallBack, Context context) {
        this.mSessionModel = mSessionModel;
        this.mMyResourceCallBack = mMyResourceCallBack;
        this.teacher_image = new ObservableField<>(mSessionModel.getTrainer().getImage());
        this.teacher_name = new ObservableField<>(mSessionModel.getTrainer().getName());
        if (mSessionModel.getTrainerCourse() != null) {
            this.student_subject = new ObservableField<>(" - " + mSessionModel.getTrainerCourse().getSubjectName());
        } else if (mSessionModel.getLiveorVideoCourse() != null) {
            this.student_subject = new ObservableField<>(" - " + mSessionModel.getLiveorVideoCourse().getCourseSubject());
        } else if (mSessionModel.getTrainerTrainingsCourseResponse() != null) {
            this.student_subject = new ObservableField<>(" - " + mSessionModel.getTrainerTrainingsCourseResponse().getCourseName());
        } else {
            this.student_subject = new ObservableField<>(" - ");
        }

        this.student_name = new ObservableField<>(mSessionModel.getBookFor().getName());
        this.student_image = new ObservableField<>(mSessionModel.getBookFor().getImage());

        if (mSessionModel.getLiveorVideoCourse() != null && mSessionModel.getLiveorVideoCourse().getCourseType().equals("LiveCourse")) {
            this.teacher_address = new ObservableField<>(context.getString(R.string.zoom_meeting));
            this.remain_time = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getRemainingTime());
            this.time = new ObservableField<>(CommonUtils.dateFormater(mSessionModel.getLiveorVideoCourse().getLiveTime(), "HH a", "HH:mm a"));
        } else {
            this.time = new ObservableField<>(mSessionModel.getTime());
            this.teacher_address = new ObservableField<>(mSessionModel.getTrainer().getLocation().getStreetAddress());
            this.remain_time = new ObservableField<>(mSessionModel.getDuration() + "");
        }
    }

    public void setOnItemClick() {
        if (mMyResourceCallBack != null) {
            mMyResourceCallBack.myResourceFvrt(mSessionModel);
        }
    }
}
