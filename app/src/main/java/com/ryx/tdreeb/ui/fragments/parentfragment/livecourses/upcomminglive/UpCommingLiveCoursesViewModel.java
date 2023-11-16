package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class UpCommingLiveCoursesViewModel extends BaseViewModel<UpCommingLiveCoursesNavigator> {

    @Inject
    public UpCommingLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getLiveCourses() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetLiveCoursesParent(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessLiveCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

}
