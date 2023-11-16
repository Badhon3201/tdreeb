package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse;

import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface FindCourseNavigator {
    void openSubSubject();

    void openTrainerList();

    void handleError(Throwable throwable);

    void onSuccessSubject(SubjectResponse mSubjectResponse);
}
