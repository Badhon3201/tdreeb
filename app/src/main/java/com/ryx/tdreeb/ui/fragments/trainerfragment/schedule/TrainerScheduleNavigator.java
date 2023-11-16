package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule;

import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.TrainerScheduleResponse;

public interface TrainerScheduleNavigator {
    void handleError(Throwable throwable);
    void onSuccessGetTrainerSchedule(TrainerScheduleResponse trainerScheduleResponse);

    void openDateForDirectFrom();

    void openDateForDirectTo();

    void openDateForOnlineFrom();

    void openDateForOnlineTo();

    void onClickAvailableforDirectTraining();

    void onClickAvailableforDirectGroup();

    void onClickAvailableforOnlineTraining();

    void onClickAvailableforOnlineGroup();
}
