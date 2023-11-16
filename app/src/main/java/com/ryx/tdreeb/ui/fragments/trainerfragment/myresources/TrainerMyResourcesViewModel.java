package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class TrainerMyResourcesViewModel extends BaseViewModel<TrainerMyResourcesNavigator> {

    @Inject
    public TrainerMyResourcesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getResourceData(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetResource(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: "+throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void removeResourceDataById(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveResource(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickResource(){
        getNavigator().openAddResource();
    }
}
