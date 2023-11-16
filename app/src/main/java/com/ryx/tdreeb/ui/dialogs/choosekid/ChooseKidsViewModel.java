package com.ryx.tdreeb.ui.dialogs.choosekid;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ChooseKidsViewModel extends BaseViewModel<ChooseKidsNavigator> {
    @Inject
    public ChooseKidsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickClose() {
        getNavigator().close();
    }

    public void onClickContinueBtn() {
        getNavigator().onContinueBtn();
    }
}
