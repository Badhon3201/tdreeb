package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;

public class FindCourseItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> subject_name;


    public final OnClickFindCourse mOnClickFindCourse;

    public final SubjectModel mSubjectModel;

    public FindCourseItemViewModel(SubjectModel mSubjectModel, OnClickFindCourse mOnClickFindCourse) {
        this.mSubjectModel = mSubjectModel;
        this.image = new ObservableField<>(mSubjectModel.getImage());
        this.subject_name = new ObservableField<>(mSubjectModel.getSubjectName());
        this.mOnClickFindCourse = mOnClickFindCourse;
    }

    public void onClickItem() {
        if (mOnClickFindCourse != null) {
            mOnClickFindCourse.onClickItemModel(mSubjectModel);
        }
    }
}