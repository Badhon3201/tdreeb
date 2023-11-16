package com.ryx.tdreeb.ui.fragments.parentfragment.mysession;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;


public class MySessionParentViewModel extends BaseViewModel<MySessionParentNavigator> {

    @Inject
    public MySessionParentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
