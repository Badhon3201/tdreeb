package com.ryx.tdreeb.ui.fragments.trainerfragment.mypayment;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerMyPaymentViewModel extends BaseViewModel<TrainerMyPaymentNavigator> {

    @Inject
    public TrainerMyPaymentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
