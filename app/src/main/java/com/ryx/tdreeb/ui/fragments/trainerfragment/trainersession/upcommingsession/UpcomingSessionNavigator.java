package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface UpcomingSessionNavigator {

    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
