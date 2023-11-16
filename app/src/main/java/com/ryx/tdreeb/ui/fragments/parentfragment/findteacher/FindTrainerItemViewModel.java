package com.ryx.tdreeb.ui.fragments.parentfragment.findteacher;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;

public class FindTrainerItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final OnClickFindCourse mOnClickFindCourse;

    public FindTrainerItemViewModel(String image, String name, OnClickFindCourse mOnClickFindCourse) {
        this.mOnClickFindCourse = mOnClickFindCourse;
        this.image = new ObservableField<>(image);
        this.name = new ObservableField<>(name);
    }

    public void onClickItem() {
        if (mOnClickFindCourse != null) {
            mOnClickFindCourse.onClickItem(name.get());
        }
    }
}