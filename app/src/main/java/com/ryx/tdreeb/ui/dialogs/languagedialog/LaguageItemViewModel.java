package com.ryx.tdreeb.ui.dialogs.languagedialog;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;

public class LaguageItemViewModel {

    public final ObservableField<String> title;

    private final LanguageModel mLanguageModel;

    private final NationalityModel mNationalityModel;

    private final SubjectModel mSubjectModel;

    private final CurriculumModel mCurriculumModel;

    private final GradeModel mGradeModel;
    //
    public final LanguagClickBack mListener;

    public final ListCallBack mCallBack;

    public LaguageItemViewModel(LanguageModel mCityModelTwo, LanguagClickBack listener) {
        this.mLanguageModel = mCityModelTwo;
        this.mCallBack = null;
        this.mSubjectModel = null;
        this.mCurriculumModel = null;
        this.mNationalityModel = null;
        this.mGradeModel = null;
        this.mListener = listener;
        title = new ObservableField<>(mLanguageModel.getLanguageName());
    }

    public LaguageItemViewModel(NationalityModel mNationalityModel, LanguagClickBack listener) {
        this.mLanguageModel = null;
        this.mSubjectModel = null;
        this.mCurriculumModel = null;
        this.mGradeModel = null;
        this.mNationalityModel = mNationalityModel;
        this.mListener = listener;
        this.mCallBack = null;
        title = new ObservableField<>(mNationalityModel.getNationalityTitle());
    }

    public LaguageItemViewModel(SubjectModel mSubjectModel, ListCallBack mCallBack) {
        this.mLanguageModel = null;
        this.mCallBack = mCallBack;
        this.mSubjectModel = mSubjectModel;
        this.mCurriculumModel = null;
        this.mNationalityModel = null;
        this.mGradeModel = null;
        this.mListener = null;
        title = new ObservableField<>(mSubjectModel.getSubjectName());
    }

    public LaguageItemViewModel(CurriculumModel mCurriculumModel, ListCallBack mCallBack) {
        this.mLanguageModel = null;
        this.mCallBack = mCallBack;
        this.mSubjectModel = null;
        this.mCurriculumModel = mCurriculumModel;
        this.mNationalityModel = null;
        this.mGradeModel = null;
        this.mListener = null;
        title = new ObservableField<>(mCurriculumModel.getCurriculumName());
    }

    public LaguageItemViewModel(GradeModel mGradeModel, ListCallBack mCallBack) {
        this.mLanguageModel = null;
        this.mCallBack = mCallBack;
        this.mSubjectModel = null;
        this.mCurriculumModel = null;
        this.mGradeModel = mGradeModel;
        this.mNationalityModel = null;
        this.mListener = null;
        title = new ObservableField<>(mGradeModel.getGradeName());
    }

    public void onClickItem() {
        if (mListener != null) {
            if (mLanguageModel != null) {
                mListener.onClickItem(mLanguageModel);
            } else {
                mListener.onClickItemNationality(mNationalityModel);
            }
        } else if (mCallBack != null) {
            if (mSubjectModel != null) {
                mCallBack.callBackSubject(mSubjectModel);
            } else if (mCurriculumModel != null) {
                mCallBack.callCurriculum(mCurriculumModel);
            } else if (mGradeModel != null) {
                mCallBack.callGrade(mGradeModel);
            }
        }
    }
}