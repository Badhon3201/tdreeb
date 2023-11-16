package com.ryx.tdreeb.ui.fragments.trainerfragment.profile;

import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileResponse;

public interface TrainerProfileNavigator {

    void openImage();

    void openDOB();

    void openLanguage();

    void openNationality();

    void openMap();

    void updateProfile();

    void handleError(Throwable throwable);

    void onSuccessData(TrainerProfileResponse mTrainerProfileResponse);

    void onSuccessLanguageResponse(LanguagesResponse mLanguagesResponse);

    void onSuccessNationalitiesResponse(NationalitiesResponse mNationalitiesResponse);
}
