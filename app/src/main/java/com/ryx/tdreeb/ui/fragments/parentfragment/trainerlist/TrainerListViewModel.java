package com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerListViewModel extends BaseViewModel<TrainerListNavigator> {

    @Inject
    public TrainerListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getCourse(String subjectName) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllCoursesBySubject(subjectName,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getTraining(String subjectName) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllTraining(subjectName,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessCourse(response);
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
