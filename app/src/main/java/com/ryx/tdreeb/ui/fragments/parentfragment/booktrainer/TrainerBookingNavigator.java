package com.ryx.tdreeb.ui.fragments.parentfragment.booktrainer;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;

public interface TrainerBookingNavigator {
    void openScheduleType();

    void openScheduleDateTime();

    void openPaymentMethod();

    void isGroupSessionClick();

    void openChooseKid();

    void handleError(Throwable throwable);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);
}
