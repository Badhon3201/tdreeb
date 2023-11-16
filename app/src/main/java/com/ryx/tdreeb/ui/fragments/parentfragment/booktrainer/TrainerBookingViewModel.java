package com.ryx.tdreeb.ui.fragments.parentfragment.booktrainer;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerBookingViewModel extends BaseViewModel<TrainerBookingNavigator> {
    @Inject
    public TrainerBookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getChildren(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetChild(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddChildResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }


    public void onClickScheduleDateTime() {
        getNavigator().openScheduleDateTime();
    }

    public void onClickPaymentMethod() {
        getNavigator().openPaymentMethod();
    }

    public void onClickScheduleType() {
        getNavigator().openScheduleType();
    }

    public void onClickChooseKid() {
        getNavigator().openChooseKid();
    }

    public void onClickGroupSession() {
        getNavigator().isGroupSessionClick();
    }
}
