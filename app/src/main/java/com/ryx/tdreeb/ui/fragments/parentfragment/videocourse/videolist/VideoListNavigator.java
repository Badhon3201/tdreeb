package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.videolist;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface VideoListNavigator {
    void openBuy();

    void openMap();

    void openBottomSheet();

    void handleError(Throwable throwable);

    void onSuccessVideoCourse(LiveCourseResponse mLiveCourseResponse);
}
