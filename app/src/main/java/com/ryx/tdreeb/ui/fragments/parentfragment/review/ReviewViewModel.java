package com.ryx.tdreeb.ui.fragments.parentfragment.review;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ReviewViewModel extends BaseViewModel<ReviewNavigator> {
    @Inject
    public ReviewViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
