package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.LiveCourseCallBack;

public class VideoTrainerCourseItemViewModel {
    public final ObservableField<String> image;
    public final ObservableField<String> title;
    public final ObservableField<String> subject;
    public final ObservableField<String> price;
    public final LiveCourseModel mLiveCourseModel;
    public final LiveCourseCallBack mLiveCourseCallBack;


    public VideoTrainerCourseItemViewModel(LiveCourseModel mLiveCourseModel,LiveCourseCallBack mLiveCourseCallBack) {
        this.mLiveCourseModel = mLiveCourseModel;
        this.mLiveCourseCallBack = mLiveCourseCallBack;
        this.image = new ObservableField<>(mLiveCourseModel.getUploadLink());
        this.title = new ObservableField<>(mLiveCourseModel.getCourseTitle());
        this.subject = new ObservableField<>(mLiveCourseModel.getCourseSubject());
        this.price = new ObservableField<>("AED "+mLiveCourseModel.getPrice());
    }

    public void onClickEdit(){
        if(mLiveCourseCallBack!=null){
            mLiveCourseCallBack.onClickEdit(mLiveCourseModel);
        }
    }

    public void onClickDelete(){
        if(mLiveCourseCallBack!=null){
            mLiveCourseCallBack.onClickDelete(mLiveCourseModel);
        }
    }
}