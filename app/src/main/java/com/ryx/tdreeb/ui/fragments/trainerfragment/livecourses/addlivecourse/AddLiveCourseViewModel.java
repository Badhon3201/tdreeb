package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseAddModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class AddLiveCourseViewModel extends BaseViewModel<AddLiveCourseNavigator> {

    @Inject
    public AddLiveCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addLiveCoursesByData(LiveCourseAddModel mLiveCourseAddModel) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doAddLiveCourses(mLiveCourseAddModel)
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

    public void updateLiveCoursesByData(LiveCourseAddModel mLiveCourseAddModel) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUpdateLiveCourses(mLiveCourseAddModel)
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

    public void onClickSubmit(){
        getNavigator().submitData();
    }

    public void onClickSubject(){
        getNavigator().openSubject();
    }

    public void onClickSub(){
        getNavigator().openSub();
    }

    public void onClickDate(){
        getNavigator().openDate();
    }

    public void onClickTime(){
        getNavigator().openTimePicker();
    }

    public void onClickUpload(){
        getNavigator().openImgAndVideo();
    }
}
