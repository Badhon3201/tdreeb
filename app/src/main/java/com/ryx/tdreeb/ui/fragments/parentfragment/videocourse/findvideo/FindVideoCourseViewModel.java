package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseNavigator;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class FindVideoCourseViewModel extends BaseViewModel<FindVideoCourseNavigator> {
    @Inject
    public FindVideoCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void getVideoCourseCourses(String searchData) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetSubjects(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessVideoCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
