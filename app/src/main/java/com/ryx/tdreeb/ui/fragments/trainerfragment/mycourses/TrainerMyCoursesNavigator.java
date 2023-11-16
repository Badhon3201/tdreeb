package com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;

public interface TrainerMyCoursesNavigator {
    void addSubject();

    void handleError(Throwable throwable);

    void onSuccessCourses(CoursesResponse mCoursesResponse);
}
