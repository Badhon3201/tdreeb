package com.ryx.tdreeb.ui.fragments.parentfragment.myresource;

import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface MyResourceNavigator {
    void openFindResource();

    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
