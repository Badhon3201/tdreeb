package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;

public class MyVideoItemViewModel {
    public final ObservableField<String> name;

    public final ObservableField<String> image;
    public final ObservableField<String> price;
    public final ObservableField<String> subject;
    public final ObservableField<String> buyFor;
    public final ObservableField<String> rate;
    public final LiveCourseModel mLiveCourseModel;
    public final SessionModel mSessionModel;

    public final LiveCourseModelPass mListener;

    public MyVideoItemViewModel(LiveCourseModel mLiveCourseModel, LiveCourseModelPass mListener) {
        this.mLiveCourseModel = mLiveCourseModel;
        this.mSessionModel = null;
        this.mListener = mListener;
        this.name = new ObservableField<>(mLiveCourseModel.getCourseTitle());
        this.image = new ObservableField<>(mLiveCourseModel.getUploadLink());
        this.price = new ObservableField<>("AED: " + mLiveCourseModel.getPrice());
        this.subject = new ObservableField<>(mLiveCourseModel.getCourseSubject());
        this.buyFor = new ObservableField<>("");
        this.rate = new ObservableField<>("0.0");
    }

    public MyVideoItemViewModel(SessionModel mSessionModel,LiveCourseModelPass mListener) {
        this.mSessionModel = mSessionModel;
        this.mListener = mListener;
        this.mLiveCourseModel = null;
        if(mSessionModel.getLiveorVideoCourse()!= null){
            this.name = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getCourseTitle());
            this.image = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getUploadLink());
            this.price = new ObservableField<>("AED: " + mSessionModel.getLiveorVideoCourse().getPrice());
            this.subject = new ObservableField<>(mSessionModel.getLiveorVideoCourse().getCourseSubject());
        }else{
            this.name = new ObservableField<>("");
            this.image = new ObservableField<>("");
            this.price = new ObservableField<>("AED: ");
            this.subject = new ObservableField<>("");
        }

        this.buyFor = new ObservableField<>("Source buy for: "+mSessionModel.getBookFor().getName());
        this.rate = new ObservableField<>("0.0");
    }

    public void onClickItem() {
        if (mListener != null) {
            mListener.onClickLiveCourses(0,mLiveCourseModel);
        }
    }
}