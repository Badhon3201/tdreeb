package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class AcceptedRequestViewModel extends BaseViewModel<AcceptedRequestNavigator> {
    @Inject
    public AcceptedRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllRequest(Map<String,String> param) {
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
}
