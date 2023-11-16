package com.ryx.tdreeb.ui.fragments.parentfragment.home;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.interfaces.ChildrenCallBack;

public class AddChildItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> number_plete;

    public final ChildrenCallBack mListener;

    public AddChildItemViewModel(String image, String number_plete, ChildrenCallBack mListener) {
        this.mListener = mListener;
        this.image = new ObservableField<>(image);
        this.number_plete = new ObservableField<>(number_plete);
    }

    public void onClickItem(){
        if(mListener!=null){
            mListener.onClickItem();
        }
    }
}