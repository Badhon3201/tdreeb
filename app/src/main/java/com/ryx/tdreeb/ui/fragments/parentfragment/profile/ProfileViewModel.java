package com.ryx.tdreeb.ui.fragments.parentfragment.profile;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.SendRegistrationData;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {
    @Inject
    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
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

    public void updateProfile(SendRegistrationData params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetUpdateParent(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessProfileUpdate(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void updateStudentProfile(SendRegistrationData params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetUpdateStudentProfile(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessProfileUpdate(response);
                }, throwable -> {
                    Log.e("ERRORPROFILE", "updateStudentProfile: "+throwable.getMessage() );
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickShowPass() {
        getNavigator().showPass();
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

    public void onClickUpdate(){
        getNavigator().updateProfile();
    }
}
