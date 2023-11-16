package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LiveCoursesViewModel extends BaseViewModel<LiveCoursesNavigator> {

    @Inject
    public LiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
