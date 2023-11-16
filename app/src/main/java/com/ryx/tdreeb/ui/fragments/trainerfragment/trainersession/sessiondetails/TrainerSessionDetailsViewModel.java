package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.sessiondetails;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class TrainerSessionDetailsViewModel extends BaseViewModel<TrainerSessionDetailsNavigator> {

    @Inject
    public TrainerSessionDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void sessionUpdate(Map<String,String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .postSessionDateUpdate(map)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessSession(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void sessionStatusUpdate(Map<String,String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostSessionUpdate(map)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessSessionStatusUpdate(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickEdit(){
        getNavigator().openScheduleDateTime();
    }

    public void onClickZoom(){
        getNavigator().openZoom();
    }

    public void onClickShare(){
        getNavigator().shareLinkZoom();
    }

    public void onClickCopy(){
        getNavigator().copyLinkZoom();
    }

    public void onClickStart(){
        getNavigator().onClickStart();
    }

    public void onClickReached(){
        getNavigator().onClickReached();
    }

    public void onClickComplete(){
        getNavigator().onClickFinished();
    }

}
