package com.ryx.tdreeb.ui.fragments.paymentmethod;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.submitmodels.LiveCourseParentModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class PaymentMethodViewModel extends BaseViewModel<PaymentMethodNavigator> {
    @Inject
    public PaymentMethodViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getChildren(LiveCourseParentModel params) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetBookSession(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessOnlineLiveCourse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickThankYou() {
        getNavigator().openThankYou();
    }
}
