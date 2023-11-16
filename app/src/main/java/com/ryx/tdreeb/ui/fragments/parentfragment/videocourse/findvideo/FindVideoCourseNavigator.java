package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo;

import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface FindVideoCourseNavigator {

    void onSubSection();

    void onVideoList();

    void handleError(Throwable throwable);

    void onSuccessVideoCourse(SubjectResponse allVideoCourseResponse);
}
