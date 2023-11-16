package com.ryx.tdreeb.ui.fragments.parentfragment.scheduledatetime;

import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.TrainerScheduleResponse;

public interface ScheduleDateTimeNavigator {

    void handleError(Throwable throwable);

    void onSuccessSchedule(TrainerScheduleResponse mTrainerScheduleResponse);
}
