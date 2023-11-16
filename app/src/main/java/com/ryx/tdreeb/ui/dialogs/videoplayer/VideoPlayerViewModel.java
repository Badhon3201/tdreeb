package com.ryx.tdreeb.ui.dialogs.videoplayer;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class VideoPlayerViewModel extends BaseViewModel<VideoPlayerNavigator> {

    @Inject
    public VideoPlayerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
