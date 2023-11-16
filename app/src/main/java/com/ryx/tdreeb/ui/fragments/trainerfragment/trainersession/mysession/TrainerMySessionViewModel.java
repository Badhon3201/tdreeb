package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.mysession;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerMySessionViewModel extends BaseViewModel<TrainerMySessionNavigator> {

    @Inject
    public TrainerMySessionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
