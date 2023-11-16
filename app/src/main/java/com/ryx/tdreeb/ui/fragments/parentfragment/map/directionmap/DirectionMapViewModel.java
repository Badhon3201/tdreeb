package com.ryx.tdreeb.ui.fragments.parentfragment.map.directionmap;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class DirectionMapViewModel extends BaseViewModel<DirectionMapNavigator> {

    @Inject
    public DirectionMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getDirectionData(String origin, String destination, String key) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doMapDirectionApi(origin, destination, key)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessDirection(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickBackTimeline() {
        getNavigator().onBackTimeLine();
    }
}
