package com.ryx.tdreeb.ui.fragments.trainerfragment.profile;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerProfileViewModel extends BaseViewModel<TrainerProfileNavigator> {

    @Inject
    public TrainerProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getLanguages() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetGeneralLanguage(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessLanguageResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getNationalities() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetGeneralNationalities(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessNationalitiesResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getProfile(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetTeacherProfile(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessData(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void updateData(ChildenModel params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostTeacherUpdate(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessData(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickImg() {
        getNavigator().openImage();
    }

    public void onClickDOB() {
        getNavigator().openDOB();
    }

    public void onClickLanguage() {
        getNavigator().openLanguage();
    }

    public void onClickNationality() {
        getNavigator().openNationality();
    }

    public void onClickAddress() {
        getNavigator().openMap();
    }

    public void onClickUpdate(){getNavigator().updateProfile();}
}
