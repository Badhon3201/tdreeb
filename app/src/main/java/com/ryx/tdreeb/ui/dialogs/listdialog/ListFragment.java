package com.ryx.tdreeb.ui.dialogs.listdialog;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.LanguageAdapter;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.FragmentListBinding;
import com.ryx.tdreeb.di.component.DaggerDialogComponent;
import com.ryx.tdreeb.di.component.DialogComponent;
import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseDialog;

import java.util.List;

import javax.inject.Inject;

public class ListFragment extends BaseDialog implements ListNavigator {

    private static final String TAG = "ListFragment";

    FragmentListBinding mFragmentListBinding;
    @Inject
    ListViewModel mListViewModel;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    LanguageAdapter mLanguageAdapter;

    String title = "";

    List<SubjectModel> listSubject;
    List<CurriculumModel> listCurriculumModel;
    List<GradeModel> listGrade;

    private ListCallBack mListCallBack;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        View view = mFragmentListBinding.getRoot();
        performDependencyInjection(getBuildComponent());
        mFragmentListBinding.setViewModel(mListViewModel);
        mListViewModel.setNavigator(this);
        setUp();
        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    private DialogComponent getBuildComponent() {
        return DaggerDialogComponent.builder()
                .appComponent(((MvvmApp) (getContext().getApplicationContext())).appComponent)
                .dialogModule(new DialogModule(this))
                .build();
    }

    public void setSubject(List<SubjectModel> listSubject, String title) {
        this.title = title;
        this.listSubject = listSubject;
    }

    public void setCurriculum(List<CurriculumModel> listCurriculumModel, String title) {
        this.title = title;
        this.listCurriculumModel = listCurriculumModel;
    }

    public void setGrade(List<GradeModel> listGrade, String title) {
        this.title = title;
        this.listGrade = listGrade;
    }

    public void setCallBack(ListCallBack mListCallBack) {
        this.mListCallBack = mListCallBack;
    }

    private void performDependencyInjection(DialogComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setUp() {

        mFragmentListBinding.tvTitle.setText(title);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentListBinding.rv.setLayoutManager(mLinearLayoutManager);
        mFragmentListBinding.rv.addItemDecoration(new EqualSpacingItemDecoration(2));
        mFragmentListBinding.rv.setItemAnimator(new DefaultItemAnimator());
        mFragmentListBinding.rv.setAdapter(mLanguageAdapter);

        if (listSubject != null) {
            mLanguageAdapter.addItemsSubject(listSubject);
            mLanguageAdapter.setListener(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                    if (mListCallBack != null) {
                        mListCallBack.callBackSubject(mSubjectModel);
                    }
                    dismiss();
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
        } else if (listCurriculumModel != null) {
            mLanguageAdapter.addItemsCurriculum(listCurriculumModel);
            mLanguageAdapter.setListener(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {

                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                    if (mListCallBack != null) {
                        mListCallBack.callCurriculum(mCurriculumModel);
                    }
                    dismiss();
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
        } else {
            mLanguageAdapter.addItemsGrade(listGrade);
            mLanguageAdapter.setListener(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {

                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {
                    if (mListCallBack != null) {
                        mListCallBack.callGrade(mGradeModel);
                    }
                    dismiss();
                }
            });
        }
    }
}