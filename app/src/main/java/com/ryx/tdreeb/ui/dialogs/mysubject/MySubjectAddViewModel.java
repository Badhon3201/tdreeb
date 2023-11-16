package com.ryx.tdreeb.ui.dialogs.mysubject;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MySubjectAddViewModel extends BaseViewModel<MySubjectAddNavigation> {

    @Inject
    public MySubjectAddViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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

    public void getCurriculum() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetCurriculum(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCurriculumResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getGrade() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetGrades(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGradeResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }


    public void onClickSubject() {
        getNavigator().openSubject();
    }

    public void onClickSubSubject() {
        getNavigator().openSubSubject();
    }

    public void onClickCurriculum() {
        getNavigator().openCurriculum();
    }

    public void onClickGrade() {
        getNavigator().openGrade();
    }

    public void onClickDone() {
        getNavigator().btnDone();
    }
}
