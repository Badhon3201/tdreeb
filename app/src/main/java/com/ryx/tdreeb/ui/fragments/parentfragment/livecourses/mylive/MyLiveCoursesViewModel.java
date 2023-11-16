package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MyLiveCoursesViewModel extends BaseViewModel<MyLiveCoursesNavigator> {
    @Inject
    public MyLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getResource(int id, String courseType) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetUpComingSession(id, courseType,getDataManager().getUserType(),getDataManager().getLanguage())
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
