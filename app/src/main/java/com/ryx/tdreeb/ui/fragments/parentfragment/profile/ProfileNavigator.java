package com.ryx.tdreeb.ui.fragments.parentfragment.profile;

import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;

public interface ProfileNavigator {
    void showPass();

    void openImage();

    void openDOB();

    void openLanguage();

    void openNationality();

    void openMap();

    void updateProfile();

    void handleError(Throwable throwable);

    void onSuccessLanguageResponse(LanguagesResponse mLanguagesResponse);

    void onSuccessNationalitiesResponse(NationalitiesResponse mNationalitiesResponse);

    void onSuccessProfileUpdate(RegistrationResponse mRegistrationResponse);
}
