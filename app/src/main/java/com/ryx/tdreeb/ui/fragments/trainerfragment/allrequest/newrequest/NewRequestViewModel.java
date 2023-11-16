package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class NewRequestViewModel extends BaseViewModel<NewRequestNavigator> {

    @Inject
    public NewRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllRequest(Map<String, String> param) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetTrainerAllRequest(param,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAllRequest(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getRequestUpdate(Map<String, String> param) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetTrainerAllRequestUpdate(param,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessRequestUpdate(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
