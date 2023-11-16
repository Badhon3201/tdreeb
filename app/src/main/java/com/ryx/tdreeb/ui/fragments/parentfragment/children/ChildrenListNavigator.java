package com.ryx.tdreeb.ui.fragments.parentfragment.children;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;

public interface ChildrenListNavigator {
    void openProfile();

    void handleError(Throwable throwable);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);
}
