package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.sessiondetails;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionStatusUpdateResponse;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.data.model.api.sessionmodel.AllRequestUpdate;

public interface TrainerSessionDetailsNavigator {

    void onClickStart();

    void onClickReached();

    void onClickFinished();

    void openScheduleDateTime();

    void openZoom();

    void copyLinkZoom();

    void shareLinkZoom();

    void handleError(Throwable throwable);

    void onSuccessSession(CoursesResponse mCoursesResponse);

    void onSuccessSessionStatusUpdate(SessionStatusUpdateResponse mSessionStatusUpdateResponse);
}
