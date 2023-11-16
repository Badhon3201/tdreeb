package com.ryx.tdreeb.ui.fragments.thankyou;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ThankYouViewModel extends BaseViewModel<ThankYouNavigator> {
    @Inject
    public ThankYouViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickHome(){
        getNavigator().openHome();
    }
}
