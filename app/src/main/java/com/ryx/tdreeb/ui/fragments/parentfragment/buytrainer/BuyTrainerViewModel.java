package com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class BuyTrainerViewModel extends BaseViewModel<BuyTrainerNavigator> {

    @Inject
    public BuyTrainerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
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


    public void onClickChooseKid() {
        getNavigator().openChooseKid();
    }
}
