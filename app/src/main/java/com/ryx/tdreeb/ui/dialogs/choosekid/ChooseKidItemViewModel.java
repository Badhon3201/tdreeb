package com.ryx.tdreeb.ui.dialogs.choosekid;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;

public class ChooseKidItemViewModel {
    public final ObservableField<String> name;

    public final ObservableField<Boolean> isSelect;

    public final ChildenModel mChildenModel;
    public final ChildrenCallBack mChildrenCallBack;


    public ChooseKidItemViewModel(ChildenModel mChildenModel, ChildrenCallBack mChildrenCallBack) {
        this.mChildrenCallBack = mChildrenCallBack;
        this.mChildenModel = mChildenModel;
        this.isSelect = new ObservableField<>(mChildenModel.isSelect());
        this.name = new ObservableField<>(mChildenModel.getFirstName() + " " + mChildenModel.getLastName());
    }

    public void onClickItem() {
        if (mChildrenCallBack != null) {
            mChildrenCallBack.onClickItemView(mChildenModel);
        }
    }
}