package com.ryx.tdreeb.ui.fragments.parentfragment.addchildren;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;

public interface AddChildrenNavigation {
    void showPass();

    void openImage();

    void openDOB();

    void openLanguage();

    void openNationality();

    void openMap();

    void submitData();

    void handleError(Throwable throwable);

    void onSuccessLanguageResponse(LanguagesResponse mLanguagesResponse);

    void onSuccessNationalitiesResponse(NationalitiesResponse mNationalitiesResponse);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);
}
