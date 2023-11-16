package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo;

import com.ryx.tdreeb.data.model.api.ParentAllVideoList.AllVideoCourseResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface MyVideoNavigator {
    void handleError(Throwable throwable);

    void onSuccessResource(SessionResponse mSessionResponse);
}
