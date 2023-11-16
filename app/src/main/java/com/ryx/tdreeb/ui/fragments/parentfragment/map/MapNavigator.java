package com.ryx.tdreeb.ui.fragments.parentfragment.map;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface MapNavigator {
    void openTrainerList();

    void handleError(Throwable throwable);

    void onSuccessCourse(CoursesResponse mCoursesResponse);
}
