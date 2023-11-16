package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;

public class MyVideoHomeTwoItemViewModel {

    public final ObservableField<String> name;
    public final ObservableField<String> sourceBy;
    public final ObservableField<String> price;
    public final ObservableField<String> ratings;
    public final ObservableField<String> img;
    public final LiveCourseModel mLiveCourseModel;
    public final LiveCourseModelPass mLiveCourseModelPass;

    public MyVideoHomeTwoItemViewModel(LiveCourseModel mLiveCourseModel, Context context,LiveCourseModelPass mLiveCourseModelPass) {
        this.mLiveCourseModelPass = mLiveCourseModelPass;
        this.mLiveCourseModel = mLiveCourseModel;
        this.name = new ObservableField<>(mLiveCourseModel.getCourseTitle());
        this.price = new ObservableField<>("AED "+mLiveCourseModel.getPrice());
        this.ratings = new ObservableField<>("4.5");
        this.img = new ObservableField<>(mLiveCourseModel.getUploadLink());
        this.sourceBy = new ObservableField<>(context.getString(R.string.source_by_teacher,mLiveCourseModel.getTrainerResponse().getName()));
    }

    public void onClickItem(){
        if(mLiveCourseModelPass != null){
            mLiveCourseModelPass.onClickLiveCourses(0,mLiveCourseModel);
        }
    }
}
