package com.ryx.tdreeb.ui.activites.registration;

import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;

public interface RegistrationNavigator {
    void openOtp();

    void openLogin();

    void submitRegistrationData();

    void handleError(Throwable throwable);

    void onSuccessLogin(RegistrationResponse mRegistrationResponse);
}
