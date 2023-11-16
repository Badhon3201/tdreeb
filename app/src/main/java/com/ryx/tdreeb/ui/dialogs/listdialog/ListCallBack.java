package com.ryx.tdreeb.ui.dialogs.listdialog;

import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;

public interface ListCallBack {
    void callBackSubject(SubjectModel mSubjectModel);

    void callCurriculum(CurriculumModel mCurriculumModel);

    void callGrade(GradeModel mGradeModel);

}
