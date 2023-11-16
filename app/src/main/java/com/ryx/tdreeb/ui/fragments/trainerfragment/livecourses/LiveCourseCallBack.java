package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;

public interface LiveCourseCallBack {
    void onClickEdit(LiveCourseModel mLiveCourseModel);

    void onClickDelete(LiveCourseModel mLiveCourseModel);
}
