package com.ryx.tdreeb.ui.dialogs.mysubject;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculaResponse;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradesResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.databinding.FragmentMySubjectAddBinding;
import com.ryx.tdreeb.di.component.DaggerDialogComponent;
import com.ryx.tdreeb.di.component.DialogComponent;
import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.ui.base.BaseDialog;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.AddCourseCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MySubjectAddFragment extends BaseDialog implements MySubjectAddNavigation {

    private static final String TAG = "MySubjectAddFragment";

    FragmentMySubjectAddBinding mFragmentMySubjectAddBinding;
    @Inject
    MySubjectAddViewModel mMySubjectAddViewModel;
    @Inject
    DataManager dataManager;

    List<SubjectModel> listSubject;
    List<SubjectModel> subListSubject;
    SubjectModel mSubjectModel;
    SubjectModel mSubModel;

    List<CurriculumModel> listCurriculumModel;
    CurriculumModel mCurriculumModel;

    List<GradeModel> listGrade;
    GradeModel mGradeModel;
    ListFragment mListFragment;

    ListFragment mListFragmentSubSubject;

    CourseModel mCourseModel;

    AddCourseCallBack mAddCourseCallBack;

    public MySubjectAddFragment() {
        // Required empty public constructor
    }

    public static MySubjectAddFragment newInstance() {
        MySubjectAddFragment fragment = new MySubjectAddFragment();
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
        mFragmentMySubjectAddBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_subject_add, container, false);
        View view = mFragmentMySubjectAddBinding.getRoot();
        performDependencyInjection(getBuildComponent());
        mFragmentMySubjectAddBinding.setViewModel(mMySubjectAddViewModel);
        mMySubjectAddViewModel.setNavigator(this);
        setUp();
        return view;
    }

    private void setUp() {
        subListSubject = new ArrayList<>();

        if (mCourseModel != null) {
            mSubjectModel = new SubjectModel();
            mSubjectModel.setSubjectName(mCourseModel.getSubjectName());
            mSubjectModel.setSubjectId(mCourseModel.getSubjectId());

            mFragmentMySubjectAddBinding.edtAmount.setText(mCourseModel.getPrice() + "");
            mGradeModel = new GradeModel();
            mCurriculumModel = new CurriculumModel();

            if(mCourseModel.getSubject()!=null){
                mSubjectModel.setSubjectName(mCourseModel.getSubject().getSubjectName());
                mFragmentMySubjectAddBinding.tvSubject.setText(mCourseModel.getSubject().getSubjectName());
                if(mCourseModel.getSubject().getSubSubjectResponse()!=null){
                    mFragmentMySubjectAddBinding.tvSubSubject.setVisibility(View.VISIBLE);
                    mFragmentMySubjectAddBinding.tvSubSubject.setText(mCourseModel.getSubject().getSubSubjectResponse().getSubjectName());
                }else{
                    mFragmentMySubjectAddBinding.tvSubSubject.setVisibility(View.GONE);
                }
            }

            if (mCourseModel.getGrades() != null && mCourseModel.getGrades().size() > 0) {
                mGradeModel.setGradeId(mCourseModel.getGrades().get(0).getGradeId());
                mFragmentMySubjectAddBinding.edtGrade.setText(mCourseModel.getGrades().get(0).getGradeName());
            }
            if (mCourseModel.getCurriculums() != null && mCourseModel.getCurriculums().size() > 0) {
                mCurriculumModel.setCurriculumId(mCourseModel.getCurriculums().get(0).getCurriculumId());
                mFragmentMySubjectAddBinding.edtCurriculum.setText(mCourseModel.getCurriculums().get(0).getCurriculumName());
            }
            mFragmentMySubjectAddBinding.btnDone.setText(getString(R.string.update));
        }
    }

    public void setData(CourseModel mCourseModel) {
        this.mCourseModel = mCourseModel;
    }

    public void setCallBack(AddCourseCallBack mAddCourseCallBack) {
        this.mAddCourseCallBack = mAddCourseCallBack;
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

    private void performDependencyInjection(DialogComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSubject() {
        if (listSubject == null) {
            showLoading();
            mMySubjectAddViewModel.getSubject();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setSubject(listSubject, getString(R.string.subject));

            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                    MySubjectAddFragment.this.mSubjectModel = mSubjectModel;
                    mFragmentMySubjectAddBinding.tvSubSubject.setText("");
                    if(!mSubjectModel.getSubSubject().isEmpty()){
                        subListSubject = mSubjectModel.getSubSubject();
                        mFragmentMySubjectAddBinding.tvSubSubject.setVisibility(View.VISIBLE);
                    }else{
                        subListSubject.clear();
                        mFragmentMySubjectAddBinding.tvSubSubject.setVisibility(View.GONE);
                    }
                    mFragmentMySubjectAddBinding.tvSubject.setText(mSubjectModel.getSubjectName());
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {

                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openSubSubject() {
        mListFragmentSubSubject = ListFragment.newInstance();
        mListFragmentSubSubject.setSubject(subListSubject, getString(R.string.sub_category));

        mListFragmentSubSubject.setCallBack(new ListCallBack() {
            @Override
            public void callBackSubject(SubjectModel mSubjectModel) {
                MySubjectAddFragment.this.mSubModel = mSubjectModel;
                mFragmentMySubjectAddBinding.tvSubSubject.setText(mSubjectModel.getSubjectName());
            }
            @Override
            public void callCurriculum(CurriculumModel mCurriculumModel) {

            }

            @Override
            public void callGrade(GradeModel mGradeModel) {

            }
        });
        mListFragmentSubSubject.show(getParentFragmentManager());
    }

    @Override
    public void openCurriculum() {
        if (listCurriculumModel == null) {
            showLoading();
            mMySubjectAddViewModel.getCurriculum();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setCurriculum(listCurriculumModel, getString(R.string.curriculum));
            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                    MySubjectAddFragment.this.mCurriculumModel = mCurriculumModel;
                    mFragmentMySubjectAddBinding.edtCurriculum.setText(mCurriculumModel.getCurriculumName());
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openGrade() {
        if (listGrade == null) {
            showLoading();
            mMySubjectAddViewModel.getGrade();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setGrade(listGrade, getString(R.string.grade));
            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {

                }

                @Override
                public void callGrade(GradeModel mGradeModel) {
                    MySubjectAddFragment.this.mGradeModel = mGradeModel;
                    mFragmentMySubjectAddBinding.edtGrade.setText(mGradeModel.getGradeName());
                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void btnDone() {
        hideKeyboard();
        if (checkData()) {
            if (mAddCourseCallBack != null) {
                AddCourseModel addCourseModel = new AddCourseModel();
                if(mSubjectModel != null && mSubjectModel.getSubSubject() != null && mSubjectModel.getSubSubject().isEmpty()){
                    addCourseModel.setSubjectId(mSubjectModel.getSubjectId());
                }else if (mSubModel != null){
                    addCourseModel.setSubjectId(mSubModel.getSubjectId());
                }else{
                    addCourseModel.setSubjectId(mCourseModel.getSubjectId());
                }
                addCourseModel.setAllowGroup(false);
                addCourseModel.setPrice(mFragmentMySubjectAddBinding.edtAmount.getText().toString());
                addCourseModel.setDescription("");
                addCourseModel.setRating(0);
                addCourseModel.setTrainerId(dataManager.getCurrentUserId());

                List<Integer> grad = new ArrayList<>();
                grad.add(mGradeModel.getGradeId());
                addCourseModel.setGradeIds(grad);

                List<Integer> curriculumList = new ArrayList<>();
                curriculumList.add(mCurriculumModel.getCurriculumId());
                addCourseModel.setCurriculumIds(curriculumList);
                if (mCourseModel == null) {
                    mAddCourseCallBack.onCallBack(addCourseModel, false);
                } else {
                    addCourseModel.setTrainerCourseId(mCourseModel.getTrainerCourseId());
                    mAddCourseCallBack.onCallBack(addCourseModel, true);
                }
                dismiss();
            }
        }
    }

    private boolean checkData() {
        if (mSubjectModel == null) {
            mFragmentMySubjectAddBinding.tvSubject.setError(getString(R.string.select_subject));
            Toast.makeText(getContext(), "" + getString(R.string.select_subject), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mCurriculumModel == null) {
            mFragmentMySubjectAddBinding.edtCurriculum.setError(getString(R.string.select_curriculum));
            Toast.makeText(getContext(), "" + getString(R.string.select_curriculum), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mGradeModel == null) {
            mFragmentMySubjectAddBinding.edtGrade.setError(getString(R.string.select_grade));
            Toast.makeText(getContext(), "" + getString(R.string.select_grade), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mFragmentMySubjectAddBinding.edtAmount.getText().toString().isEmpty()) {
            mFragmentMySubjectAddBinding.edtAmount.setError(getString(R.string.enter_ammount_per_hour));
            Toast.makeText(getContext(), "" + getString(R.string.enter_ammount_per_hour), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void handleError(Throwable throwable) {
        hideLoading();
    }

    @Override
    public void onSuccessSubjectResponse(SubjectResponse mSubjectResponse) {
        hideLoading();
        if (mSubjectResponse.getIsSuccess()) {
            listSubject = mSubjectResponse.getData().getSubjects();
            openSubject();
        } else {
            Toast.makeText(getContext(), "" + mSubjectResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessCurriculumResponse(CurriculaResponse mCurriculaResponse) {
        hideLoading();
        if (mCurriculaResponse.getIsSuccess()) {
            listCurriculumModel = mCurriculaResponse.getData().getCurriculums();
            openCurriculum();
        } else {
            Toast.makeText(getContext(), "" + mCurriculaResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessGradeResponse(GradesResponse mGradesResponse) {
        hideLoading();
        if (mGradesResponse.getIsSuccess()) {
            listGrade = mGradesResponse.getData().getGrades();
            openGrade();
        } else {
            Toast.makeText(getContext(), "" + mGradesResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}