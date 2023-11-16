package com.ryx.tdreeb.ui.fragments.parentfragment.scheduledatetime;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class ScheduleDateTimeViewModel extends BaseViewModel<ScheduleDateTimeNavigator> {
    @Inject
    public ScheduleDateTimeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getSchedule(Map<String,String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetTrainerScheduleTypeWise(map,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessSchedule(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
