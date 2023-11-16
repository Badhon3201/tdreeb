package com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;

public class SubjectCourseItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> subject_name;

    public final ObservableField<String> grade_name;

    public final ObservableField<String> curriculum_name;

    public final ObservableField<String> price;

    public final CourseModel mCourseModel;

    public final AddCourseCallBack mListener;


    public SubjectCourseItemViewModel(CourseModel mCourseModel, AddCourseCallBack mListener) {
        this.mListener = mListener;
        this.mCourseModel = mCourseModel;
        this.image = new ObservableField<>(mCourseModel.getImage());
        this.subject_name = new ObservableField<>(mCourseModel.getSubjectName());
        if (mCourseModel.getGrades() != null && mCourseModel.getGrades().size() > 0) {
            this.grade_name = new ObservableField<>(mCourseModel.getGrades().get(0).getGradeName());
        } else {
            this.grade_name = new ObservableField<>("");
        }
        if (mCourseModel.getCurriculums() != null && mCourseModel.getCurriculums().size() > 0) {
            this.curriculum_name = new ObservableField<>(mCourseModel.getCurriculums().get(0).getCurriculumName());
        } else {
            this.curriculum_name = new ObservableField<>("");
        }
        this.price = new ObservableField<>("AED " + mCourseModel.getPrice());
    }

    public void onDeleteClick() {
        if (mListener != null) {
            mListener.onCallDelete(mCourseModel);
        }
    }

    public void onUpdateClick() {
        if (mListener != null) {
            mListener.onCallBackUpdate(mCourseModel);
        }
    }
}