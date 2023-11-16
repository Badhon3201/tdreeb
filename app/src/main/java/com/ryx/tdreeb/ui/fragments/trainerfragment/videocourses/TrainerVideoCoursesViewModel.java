package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerVideoCoursesViewModel extends BaseViewModel<TrainerVideoCoursesNavigator> {

    @Inject
    public TrainerVideoCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getVideoCoursesByID(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetVideoCourses(id)
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

    public void removeVideoCourse(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveVideoCourses(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessRemoveVideoCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickAddVideo(){
        getNavigator().openAddVideoCourse();
    }
}
