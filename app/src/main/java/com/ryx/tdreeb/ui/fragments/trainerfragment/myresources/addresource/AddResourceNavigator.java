package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource;

import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculaResponse;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradesResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface AddResourceNavigator {

    void openFile();

    void openImage();

    void openSubject();

    void openSubSubject();

    void openCurriculum();

    void openGrade();

    void submit();

    void handleError(Throwable throwable);

    void onSuccessSubjectResponse(SubjectResponse mSubjectResponse);

    void onSuccessCurriculumResponse(CurriculaResponse mCurriculaResponse);

    void onSuccessGradeResponse(GradesResponse mGradesResponse);

    void onSuccessAddResource(LiveCourseResponse mLiveCourseResponse);

    void onSuccessGetResource(ResourceResponse mResourceResponse);
}
