package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;

public class FindVideoCourseItemViewModel {
    public final ObservableField<String> courseImage;

    public final ObservableField<String> courseName;

    public final ObservableField<String> url;

    public final OnClickFindCourse mListener;


    public FindVideoCourseItemViewModel(String courseImage, String courseName,String url, OnClickFindCourse mListener) {
        this.courseImage = new ObservableField<>(courseImage);
        this.url = new ObservableField<>(url);
        this.courseName = new ObservableField<>(courseName);
        this.mListener = mListener;
    }

    public void onClickItem() {
        if (mListener != null){
            if(!url.get().isEmpty()){
                mListener.onClickItem(url.get());
            }else{
                mListener.onClickItemModel(new SubjectModel());
            }

        }

    }
}