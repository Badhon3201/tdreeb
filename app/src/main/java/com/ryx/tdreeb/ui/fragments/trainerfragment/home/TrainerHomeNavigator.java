package com.ryx.tdreeb.ui.fragments.trainerfragment.home;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface TrainerHomeNavigator {
    void openMainDrawer();

    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
