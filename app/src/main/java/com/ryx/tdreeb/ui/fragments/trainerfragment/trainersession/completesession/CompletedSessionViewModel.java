package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class CompletedSessionViewModel extends BaseViewModel<CompletedSessionNavigator> {

    @Inject
    public CompletedSessionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getResource(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetUpCompleteSession(id,getDataManager().getUserType(),getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessResource(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERRORDATA", "getResource: "+throwable.getMessage());
                    getNavigator().handleError(throwable);
                }));
    }
}
