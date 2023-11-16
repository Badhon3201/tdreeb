package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;

public interface TrainerVideoCoursesNavigator {
    void openAddVideoCourse();

    void handleError(Throwable throwable);

    void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse);

    void onSuccessRemoveVideoCourse(LiveCourseResponse mLiveCourseResponse);
}
