package com.ryx.tdreeb.ui.fragments.paymentsummary;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class PaymentSummaryViewModel extends BaseViewModel<PaymentSummaryNavigator> {
    @Inject
    public PaymentSummaryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickBook(){
        getNavigator().openPayment();
    }
}
