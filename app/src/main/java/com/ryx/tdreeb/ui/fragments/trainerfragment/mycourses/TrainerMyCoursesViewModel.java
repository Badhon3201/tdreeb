package com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerMyCoursesViewModel extends BaseViewModel<TrainerMyCoursesNavigator> {

    @Inject
    public TrainerMyCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getCoursesByID(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetCourses(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourses(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERRORDATA", "getCoursesByID: "+throwable.getMessage() );
                    getNavigator().handleError(throwable);
                }));
    }
    public void addCourseByParams(AddCourseModel params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doAddCourses(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourses(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void updateCourseByParams(AddCourseModel params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUpdateCourses(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourses(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void addRemoveByParams(AddCourseModel params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveCourses(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourses(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }



    public void onClickAddSubject() { getNavigator().addSubject(); }
}
