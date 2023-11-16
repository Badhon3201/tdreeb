package com.ryx.tdreeb.ui.fragments.parentfragment.trainerdetails;

import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;

public interface TrainerDetailsNavigator {
    void openReview();

    void handleError(Throwable throwable);

    void onSuccessGetResource(ResourceResponse mResourceResponse);

    void openBook();
}
