package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.interfaces.AllRequestCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;


public class RequestItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> studentName;

    public final ObservableField<String> studentAddress;

    public final ObservableField<String> sessionTime;

    public final ObservableField<String> subjectName;

    public final ObservableField<String> date;

    public final ObservableField<Boolean> isVisible;

    public final ObservableField<Boolean> isViewShow;

    public final SessionModel mSessionModel;

    public final AllRequestCallBack mListener;


    public RequestItemViewModel(SessionModel mSessionModel, AllRequestCallBack mListener, boolean isViewShow) {
        this.mListener = mListener;
        this.isViewShow = new ObservableField<>(isViewShow);
        this.mSessionModel = mSessionModel;
        this.image = new ObservableField<>(mSessionModel.getBookBy().getImage());
        this.studentName = new ObservableField<>(mSessionModel.getBookBy().getName());
        this.studentAddress = new ObservableField<>(mSessionModel.getBookBy().getLocation().getStreetAddress());
        this.date = new ObservableField<>(mSessionModel.getDate());
        if (mSessionModel.getLiveorVideoCourse() != null) {
            if (mSessionModel.getLiveorVideoCourse().getCourseType().equals("LiveCourse")) {
                this.isVisible = new ObservableField<>(true);
            } else {
                this.isVisible = new ObservableField<>(false);
            }
            this.sessionTime = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getLiveTime());
            this.subjectName = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getCourseSubject());
        } else if (mSessionModel.getTrainerCourse() != null) {
            this.isVisible = new ObservableField<>(false);
            this.sessionTime = new ObservableField<>(mSessionModel.getTime());
            this.subjectName = new ObservableField<>(mSessionModel.getTrainerCourse().getSubjectName());
        } else if (mSessionModel.getTrainerTrainingsCourseResponse() != null) {
            this.isVisible = new ObservableField<>(false);
            this.sessionTime = new ObservableField<>(mSessionModel.getTime());
            this.subjectName = new ObservableField<>(mSessionModel.getTrainerTrainingsCourseResponse().getCourseName());
        } else {
            this.isVisible = new ObservableField<>(false);
            this.sessionTime = new ObservableField<>(mSessionModel.getTime());
            this.subjectName = new ObservableField<>("");
        }

        if(isViewShow){
            this.isVisible.set(false);
        }
    }

    public void onClickAccept() {
        if (mListener != null)
            mListener.onClickAccept(mSessionModel);
    }

    public void onClickCancel() {
        if (mListener != null)
            mListener.onClickCancel(mSessionModel);
    }

}
