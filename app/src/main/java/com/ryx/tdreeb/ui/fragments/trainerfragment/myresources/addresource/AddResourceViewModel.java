package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class AddResourceViewModel extends BaseViewModel<AddResourceNavigator> {
    @Inject
    public AddResourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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

    public void removeResourceFile(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRemoveResourceFile(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void updateResourceData(Map<String,String> parm, List<File> list) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doEditResource(parm,list)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    Log.e("errorDasd", "updateResourceData: "+throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void addResource(Map<String,String> params, List<File> list) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doAddResource(params,list)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddResource(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERRORADD", "addResource: "+throwable.getMessage() );
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

    public void openFile(){
        getNavigator().openFile();
    }

    public void openImage(){
        getNavigator().openImage();
    }

    public void onClickSubmit(){
        getNavigator().submit();
    }
}
