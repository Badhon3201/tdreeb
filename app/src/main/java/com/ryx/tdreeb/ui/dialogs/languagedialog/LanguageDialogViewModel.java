package com.ryx.tdreeb.ui.dialogs.languagedialog;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LanguageDialogViewModel extends BaseViewModel<LanguageDialogNavigator> {

    @Inject
    public LanguageDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
