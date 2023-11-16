package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.subcategory;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;

public class SubCategoryItemViewModel {

        public final ObservableField<String> subject_name;

        public final OnClickFindCourse mOnClickFindCourse;

//        public final SubjectModel mSubjectModel;

        public SubCategoryItemViewModel(String name, OnClickFindCourse mOnClickFindCourse) {
//            this.mSubjectModel = mSubjectModel;
            this.subject_name = new ObservableField<>(name);
            this.mOnClickFindCourse = mOnClickFindCourse;
        }

        public void onClickItem() {
            if (mOnClickFindCourse != null) {
                mOnClickFindCourse.onClickItemModel(null);
            }
        }
    }