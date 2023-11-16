package com.ryx.tdreeb.ui.activites.login;

import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;

public interface LoginNavigator {
    void openOtp();

    void openReg();

    void openUnderEighteen();

    void submitLoginData();

    void handleError(Throwable throwable);

    void onSuccessLogin(RegistrationResponse mRegistrationResponse);
}
