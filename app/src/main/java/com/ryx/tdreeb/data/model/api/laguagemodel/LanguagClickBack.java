package com.ryx.tdreeb.data.model.api.laguagemodel;

import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;

public interface LanguagClickBack {
    void onClickItem(LanguageModel mLanguageModel);

    void onClickItemNationality(NationalityModel mNationalityModel);
}
