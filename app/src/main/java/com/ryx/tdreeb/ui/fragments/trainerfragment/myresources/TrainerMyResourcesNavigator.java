package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources;

import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;

public interface TrainerMyResourcesNavigator {
    void openAddResource();

    void handleError(Throwable throwable);

    void onSuccessGetResource(ResourceResponse mResourceResponse);
}
