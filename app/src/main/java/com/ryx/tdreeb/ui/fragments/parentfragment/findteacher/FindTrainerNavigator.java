package com.ryx.tdreeb.ui.fragments.parentfragment.findteacher;

import com.ryx.tdreeb.data.model.api.TrainingModel.TrainingResponseModel;

public interface FindTrainerNavigator {

    void openSubSubject();

    void handleError(Throwable throwable);

    void onSuccessTraining(TrainingResponseModel mTrainingResponseModel);
}
