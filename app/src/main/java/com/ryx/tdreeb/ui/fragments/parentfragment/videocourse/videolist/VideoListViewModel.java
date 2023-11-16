package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.videolist;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class VideoListViewModel extends BaseViewModel<VideoListNavigator> {
    @Inject
    public VideoListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getVideoCourseCourses(String searchData) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetAllVideoListParent(searchData)
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

    public void onClickMapView(){
        getNavigator().openMap();
    }

    public void onClickBottom(){
        getNavigator().openBottomSheet();
    }

}
