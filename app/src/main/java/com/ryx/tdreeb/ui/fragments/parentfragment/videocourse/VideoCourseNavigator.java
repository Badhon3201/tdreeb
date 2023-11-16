package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse;

import com.ryx.tdreeb.data.model.api.allvideocourse.AllVideoCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.data.model.api.videoslider.SliderResponse;

public interface VideoCourseNavigator {
    void openVideoList();

    void openCategory();

    void openBuyTrainer();

    void handleError(Throwable throwable);

    void onSuccessSubject(SubjectResponse mSubjectResponse);

    void onSuccess(AllVideoCourseResponse mAllVideoCourseResponse);

    void onSuccessSlider(SliderResponse mSliderResponse);
}
