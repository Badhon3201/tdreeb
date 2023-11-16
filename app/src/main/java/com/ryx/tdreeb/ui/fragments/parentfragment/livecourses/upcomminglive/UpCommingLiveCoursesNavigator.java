package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;

public interface UpCommingLiveCoursesNavigator {

    void handleError(Throwable throwable);

    void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse);
}
