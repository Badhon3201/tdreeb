package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface CompletedSessionNavigator {
    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
