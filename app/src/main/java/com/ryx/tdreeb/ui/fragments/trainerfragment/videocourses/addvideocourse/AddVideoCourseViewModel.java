package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseAddModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

public class AddVideoCourseViewModel extends BaseViewModel<AddVideoCourseNavigator> {

    @Inject
    public AddVideoCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addVideoCoursesByData(Map<String, String> params, File file, Map<String, File> videoParams,File intro) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doAddVideoCourses(params, file, videoParams,intro)
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

    public void updateVideoCoursesByData(Map<String, String> params, File file, Map<String, File> videoParams, File intro) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUpdateVideoCourses(params,file,videoParams,intro)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessVideoCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERROR_DATA", "updateVideoCoursesByData: "+throwable.getMessage() );
                    getNavigator().handleError(throwable);
                }));
    }

    public void getSubject() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetSubjects(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessSubjectResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void removeVideoResources(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveVideoResources(id)
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

    public void onClickSubmit() {
        getNavigator().submitData();
    }

    public void onClickSubject() {
        getNavigator().openSubject();
    }

    public void onClickSubSubject() {
        getNavigator().openSubSubject();
    }

    public void onClickUpload() {
        getNavigator().openImgAndVideo();
    }

    public void onClickIntroUpload() {
        getNavigator().openIntroVideo();
    }
}
