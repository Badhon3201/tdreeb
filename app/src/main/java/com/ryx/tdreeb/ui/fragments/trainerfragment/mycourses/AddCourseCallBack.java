package com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses;

import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;

public interface AddCourseCallBack {
    void onCallBack(AddCourseModel addCourseModel, boolean isUpdate);

    void onCallBackUpdate(CourseModel mCourseModell);

    void onCallDelete(CourseModel mCourseModel);
}
