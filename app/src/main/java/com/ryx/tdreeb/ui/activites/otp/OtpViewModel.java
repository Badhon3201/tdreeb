package com.ryx.tdreeb.ui.activites.otp;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class OtpViewModel extends BaseViewModel<OtpNavigator> {

    @Inject
    public OtpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickBTN() {
        getNavigator().onClickBtn();
    }
}
