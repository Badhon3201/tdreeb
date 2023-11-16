package com.ryx.tdreeb.ui.activites.splash;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    @Inject
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
