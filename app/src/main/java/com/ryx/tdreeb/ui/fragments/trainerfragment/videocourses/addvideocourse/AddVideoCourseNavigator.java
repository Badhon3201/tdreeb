package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface AddVideoCourseNavigator {
    void submitData();

    void openImgAndVideo();

    void openIntroVideo();

    void openSubject();

    void openSubSubject();

    void handleError(Throwable throwable);

    void onSuccessVideoCourse(LiveCourseResponse mLiveCourseResponse);

    void onSuccessSubjectResponse(SubjectResponse mSubjectResponse);
}
