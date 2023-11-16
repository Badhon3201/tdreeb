package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;

public interface TrainerLiveCoursesNavigator {

    void openAddLiveCourse();

    void handleError(Throwable throwable);

    void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse);
}
