package com.ryx.tdreeb.ui.activites.login;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.Map;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    @Inject
    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void userParentLogin( Map<String,String> params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostParentLogin(params)
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

    public void userStudentLogin( Map<String,String> params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostStudentLogin(params)
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

    public void userTeacherLogin( Map<String,String> params ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doPostTeacherLogin(params)
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
        getNavigator().submitLoginData();
    }

    public void onClickReg() {
        getNavigator().openReg();
    }
}
