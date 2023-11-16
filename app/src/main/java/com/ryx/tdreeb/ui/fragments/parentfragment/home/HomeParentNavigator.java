package com.ryx.tdreeb.ui.fragments.parentfragment.home;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;

public interface HomeParentNavigator {

    void openFindCourse();

    void openFindTrainer();

    void openSession();

    void openChat();

    void openChildrenList();

    void openChildren();

    void openResource();

    void openFavorites();

    void openVideoCourse();

    void openLiveCourse();

    void openProfile();

    void openMainDrawer();

    void handleError(Throwable throwable);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);

    void onSuccessResource(SessionResponse mSessionResponse);
}
