package com.ryx.tdreeb.ui.fragments.parentfragment.children;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;

public class ChildListItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final ChildenModel mChildenModel;

    public final ChildrenCallBack mChildrenCallBack;

    public ChildListItemViewModel(ChildenModel mChildenModel,ChildrenCallBack mChildrenCallBack) {
        this.mChildenModel = mChildenModel;
        this.mChildrenCallBack = mChildrenCallBack;
        this.image = new ObservableField<>(mChildenModel.getImage());
        this.name = new ObservableField<>(mChildenModel.getFirstName()+" "+mChildenModel.getLastName());
    }

    public void onClickItem(){
        if (mChildrenCallBack != null){
            mChildrenCallBack.onClickItemView(mChildenModel);
        }
    }
}