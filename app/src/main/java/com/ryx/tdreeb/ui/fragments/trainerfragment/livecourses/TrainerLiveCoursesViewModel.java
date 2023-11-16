package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerLiveCoursesViewModel extends BaseViewModel<TrainerLiveCoursesNavigator> {

    @Inject
    public TrainerLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getLiveCoursesByID(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetLiveCourses(id,getDataManager().getLanguage())
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

    public void addLiveCourseBtn(){
        getNavigator().openAddLiveCourse();
    }
}
