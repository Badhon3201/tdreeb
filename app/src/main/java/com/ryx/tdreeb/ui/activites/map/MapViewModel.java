package com.ryx.tdreeb.ui.activites.map;


import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MapViewModel extends BaseViewModel<MapNavigator> {

    @Inject
    public MapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickAddressList() {
        getNavigator().openMap();
    }

    public void onClickCloseMap() {
        getNavigator().closeMap();
    }

    public void onClickLocation() {
        getNavigator().onClickLocation();
    }
}
