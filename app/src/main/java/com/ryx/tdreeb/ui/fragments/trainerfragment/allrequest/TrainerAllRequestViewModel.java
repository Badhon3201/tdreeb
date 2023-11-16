package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerAllRequestViewModel extends BaseViewModel<TrainerAllRequestNavigator> {

    @Inject
    public TrainerAllRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
