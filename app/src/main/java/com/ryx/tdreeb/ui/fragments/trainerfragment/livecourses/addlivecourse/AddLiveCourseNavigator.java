package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface AddLiveCourseNavigator {
    void submitData();

    void openImgAndVideo();

    void openSubject();

    void openSub();

    void openDate();

    void openTimePicker();

    void handleError(Throwable throwable);

    void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse);

    void onSuccessSubjectResponse(SubjectResponse mSubjectResponse);
}
