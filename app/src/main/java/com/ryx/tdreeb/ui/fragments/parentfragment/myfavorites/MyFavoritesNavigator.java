package com.ryx.tdreeb.ui.fragments.parentfragment.myfavorites;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteSubmitResponse;

public interface MyFavoritesNavigator {

    void handleError(Throwable throwable);

    void onSuccessFavorite(FavoriteResponse mFavoriteResponse);

    void onSuccessAddFavorite(FavoriteSubmitResponse mFavoriteSubmitResponse);

    void onSuccessAddChildResponse(AddChildResponse mAddChildResponse);
}
