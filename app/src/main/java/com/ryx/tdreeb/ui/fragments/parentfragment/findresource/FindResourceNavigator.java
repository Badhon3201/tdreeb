package com.ryx.tdreeb.ui.fragments.parentfragment.findresource;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteSubmitResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;

public interface FindResourceNavigator {

    void openPaymentSummary();

    void handleError(Throwable throwable);

    void onSuccessGetResource(ResourceResponse mResourceResponse);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);

    void onSuccessAddFavorite(FavoriteSubmitResponse mFavoriteSubmitResponse);
}
