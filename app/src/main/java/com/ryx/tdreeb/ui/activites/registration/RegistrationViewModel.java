package com.ryx.tdreeb.ui.activites.registration;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.SendRegistrationData;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {

    @Inject
    public RegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void userParentReg(SendRegistrationData params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostParentRegistration(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessLogin(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void userStudentReg(SendRegistrationData params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostStudentRegistration(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessLogin(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void userTeacherReg(SendRegistrationData params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostTeacherRegistration(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessLogin(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickSubmit() {
        getNavigator().submitRegistrationData();
    }

    public void onClickLogin() {
        getNavigator().openLogin();
    }
}
