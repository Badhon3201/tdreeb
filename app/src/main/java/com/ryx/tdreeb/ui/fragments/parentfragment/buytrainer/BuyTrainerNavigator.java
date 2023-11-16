package com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;

public interface BuyTrainerNavigator {
    void openChooseKid();

    void openPaymentSummary();

    void openVideoPlayer(String url);

    void handleError(Throwable throwable);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);
}
