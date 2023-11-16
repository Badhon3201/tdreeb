package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.interfaces.SimpleDialogClick;

public class LiveDateAddItemViewModel {

    public final ObservableField<String> courseTime;

    public final SimpleDialogClick mSimpleDialogClick;

    public LiveDateAddItemViewModel(String courseTime, SimpleDialogClick mSimpleDialogClick) {
        this.mSimpleDialogClick = mSimpleDialogClick;
        this.courseTime = new ObservableField<>(courseTime);
    }

    public void onClickAdd() {
        if (mSimpleDialogClick != null)
            mSimpleDialogClick.onClose();
    }
}
