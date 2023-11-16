package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.ScheduleSubmitModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class TrainerScheduleViewModel extends BaseViewModel<TrainerScheduleNavigator> {
    @Inject
    public TrainerScheduleViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getResourceData(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetTrainerSchedule(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetTrainerSchedule(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: " + throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void submitData(ScheduleSubmitModel params){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doSubmitTrainerSchedule(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetTrainerSchedule(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: " + throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void openDateDirectFrom(){
        getNavigator().openDateForDirectFrom();
    }

    public void openDateDirectTo(){
        getNavigator().openDateForDirectTo();
    }

    public void openDateOnlineFrom(){
        getNavigator().openDateForOnlineFrom();
    }

    public void openDateOnlineTo(){
        getNavigator().openDateForOnlineTo();
    }


    public void onClickDirectTraining() {
        getNavigator().onClickAvailableforDirectTraining();
    }

    public void onClickDirectGroup() {
        getNavigator().onClickAvailableforDirectGroup();
    }

    public void onClickOnlineTraining() {
        getNavigator().onClickAvailableforOnlineTraining();
    }

    public void onClickOnlineGroup() {
        getNavigator().onClickAvailableforOnlineGroup();
    }
}
