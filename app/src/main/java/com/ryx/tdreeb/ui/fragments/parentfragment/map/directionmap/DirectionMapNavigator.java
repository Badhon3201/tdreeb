package com.ryx.tdreeb.ui.fragments.parentfragment.map.directionmap;

import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.data.model.api.mapdirectionmodel.DirectionResponses;

public interface DirectionMapNavigator {
    void onBackTimeLine();

    void handleError(Throwable throwable);

    void onSuccessDirection(DirectionResponses mDirectionResponses);
}
