package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface MyLiveCoursesNavigator {

    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
