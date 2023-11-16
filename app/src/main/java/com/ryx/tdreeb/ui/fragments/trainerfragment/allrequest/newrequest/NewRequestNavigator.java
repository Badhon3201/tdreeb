package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.sessionmodel.AllRequestUpdate;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface NewRequestNavigator {

    void handleError(Throwable throwable);

    void onSuccessAllRequest(SessionResponse mSessionResponse);

    void onSuccessRequestUpdate(AllRequestUpdate mAllRequestUpdate);
}
