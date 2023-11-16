package com.ryx.tdreeb.ui.fragments.trainerfragment.notification;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerNotificationViewModel extends BaseViewModel<TrainerNotificationNavigator> {

    @Inject
    public TrainerNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
