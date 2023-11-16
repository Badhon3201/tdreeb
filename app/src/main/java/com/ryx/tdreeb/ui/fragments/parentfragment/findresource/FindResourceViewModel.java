package com.ryx.tdreeb.ui.fragments.parentfragment.findresource;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class FindResourceViewModel extends BaseViewModel<FindResourceNavigator> {
    @Inject
    public FindResourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllResourceData(Map<String,String> params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetAllResource(params,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    Log.e("identifyError","getResourceData: "+throwable.getMessage());
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

    public void addFavorite(Map<String,String> params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doSubmitFavorite(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddFavorite(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: "+throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void removeFavorite(Map<String,String> params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveFavorite(params,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddFavorite(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: "+throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
