package com.ryx.tdreeb.ui.dialogs.mysubject;

import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculaResponse;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradesResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;

public interface MySubjectAddNavigation {

    void openSubject();

    void openSubSubject();

    void openCurriculum();

    void openGrade();

    void btnDone();

    void handleError(Throwable throwable);

    void onSuccessSubjectResponse(SubjectResponse mSubjectResponse);

    void onSuccessCurriculumResponse(CurriculaResponse mCurriculaResponse);

    void onSuccessGradeResponse(GradesResponse mGradesResponse);
}
