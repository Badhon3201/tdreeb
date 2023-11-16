package com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;

public interface TrainerListNavigator {

    void openMap();

    void openBottomSheet();

    void openTrainerDetails();

    void handleError(Throwable throwable);

    void onSuccessCourse(CoursesResponse mCoursesResponse);
}
