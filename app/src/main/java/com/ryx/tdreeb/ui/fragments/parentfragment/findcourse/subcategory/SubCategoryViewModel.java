package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.subcategory;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class SubCategoryViewModel extends BaseViewModel<SubCategoryNavigator> {

    @Inject
    public SubCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
