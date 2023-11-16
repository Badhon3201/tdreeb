package com.ryx.tdreeb.ui.fragments.parentfragment.myfavorites;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class MyFavoritesViewModel extends BaseViewModel<MyFavoritesNavigator> {
    @Inject
    public MyFavoritesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllFavorite(int FavoriteById, String FavoriteBy) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetAllFavorite(FavoriteById, FavoriteBy,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessFavorite(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERRORDATA", "getResource: " + throwable.getMessage());
                    getNavigator().handleError(throwable);
                }));
    }

    public void removeFavorite(Map<String, String> params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveFavorite(params,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddFavorite(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: " + throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getChildren(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetChild(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddChildResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
