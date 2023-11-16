package com.ryx.tdreeb.ui.fragments.parentfragment.trainerdetails;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerDetailsViewModel extends BaseViewModel<TrainerDetailsNavigator> {

    @Inject
    public TrainerDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getResourceData(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetResource(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessGetResource(response);
                }, throwable -> {
                    Log.e("identifyError", "getResourceData: "+throwable.getMessage());
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickReview(){
        getNavigator().openReview();
    }

    public void onClickBook(){
        getNavigator().openBook();
    }
}
