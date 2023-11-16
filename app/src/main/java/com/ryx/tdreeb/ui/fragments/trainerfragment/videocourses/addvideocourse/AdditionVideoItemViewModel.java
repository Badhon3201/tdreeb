package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.interfaces.VideoResourceCallBack;

public class AdditionVideoItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> number_plete;

    public final VideoResourceCallBack mVideoResourceCallBack;

    public AdditionVideoItemViewModel(String image, String number_plete,VideoResourceCallBack mVideoResourceCallBack) {
        this.mVideoResourceCallBack = mVideoResourceCallBack;
        this.image = new ObservableField<>(image);
        this.number_plete = new ObservableField<>(number_plete);
    }

    public void onClickAdd(){
        if(mVideoResourceCallBack!=null){
            mVideoResourceCallBack.addVideo();
        }
    }
}