package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface AcceptedRequestNavigator {

    void handleError(Throwable throwable);

    void onSuccessAllRequest(SessionResponse mSessionResponse);
}
