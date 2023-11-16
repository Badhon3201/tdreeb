package com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.CourseListAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.databinding.TrainerMyCoursesFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.mysubject.MySubjectAddFragment;

import javax.inject.Inject;

public class TrainerMyCoursesFragment extends BaseFragment<TrainerMyCoursesFragmentBinding, TrainerMyCoursesViewModel> implements TrainerMyCoursesNavigator, AddCourseCallBack {

    public TrainerMyCoursesFragment() {
    }

    TrainerMyCoursesFragmentBinding mTrainerMyCoursesFragmentBinding;
    private ImageView profileImage, drawerIcon;

    @Inject
    CourseListAdapter mCourseListAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;
    private boolean isSubmit = false;

    MySubjectAddFragment mMySubjectAddFragment;

    public static TrainerMyCoursesFragment newInstance() {
        TrainerMyCoursesFragment fragment = new TrainerMyCoursesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.trainer_my_courses_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mTrainerMyCoursesFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        profileImage = mTrainerMyCoursesFragmentBinding.getRoot().findViewById(R.id.profile_image);
        drawerIcon = mTrainerMyCoursesFragmentBinding.getRoot().findViewById(R.id.drawer_icon);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImage);
        drawerIcon.setOnClickListener(v -> {
            getBaseActivity().openDrawer();
        });

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerMyCoursesFragmentBinding.rvSubject.setLayoutManager(mLinearLayoutManager);
        mTrainerMyCoursesFragmentBinding.rvSubject.addItemDecoration(new EqualSpacingItemDecoration(6));
        mTrainerMyCoursesFragmentBinding.rvSubject.setItemAnimator(new DefaultItemAnimator());
        mTrainerMyCoursesFragmentBinding.rvSubject.setAdapter(mCourseListAdapter);
        mCourseListAdapter.setListener(this);
        isSubmit = false;
        getBaseActivity().showLoading();
        mViewModel.getCoursesByID(dataManager.getCurrentUserId());
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void addSubject() {
        mMySubjectAddFragment = MySubjectAddFragment.newInstance();
        mMySubjectAddFragment.setCallBack(this);
        mMySubjectAddFragment.show(getParentFragmentManager());
    }

    @Override
    public void handleError(Throwable throwable) {
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessCourses(CoursesResponse mCoursesResponse) {
        getBaseActivity().hideLoading();
        if (isSubmit) {
            getBaseActivity().openDialog(mCoursesResponse.getMessage(), () -> {
                isSubmit = false;
                getBaseActivity().showLoading();
                mViewModel.getCoursesByID(dataManager.getCurrentUserId());
            });
        } else {
            if (mCoursesResponse.getIsSuccess()) {
                mCourseListAdapter.addItems(mCoursesResponse.getData().getCourses());
            } else {
                Toast.makeText(getContext(), "" + mCoursesResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onCallBack(AddCourseModel addCourseModel, boolean isUpdate) {
        isSubmit = true;
        getBaseActivity().showLoading();
        if (isUpdate) {
            mViewModel.updateCourseByParams(addCourseModel);
        } else {
            mViewModel.addCourseByParams(addCourseModel);
        }


    }

    @Override
    public void onCallBackUpdate(CourseModel mCourseModell) {
        mMySubjectAddFragment = MySubjectAddFragment.newInstance();
        mMySubjectAddFragment.setData(mCourseModell);
        mMySubjectAddFragment.setCallBack(this);
        mMySubjectAddFragment.show(getParentFragmentManager());
    }

    @Override
    public void onCallDelete(CourseModel mCourseModel) {
        new AlertDialog.Builder(getContext())
                .setMessage(getString(R.string.delete_data))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    isSubmit = true;
                    getBaseActivity().showLoading();
                    AddCourseModel addCourseModel = new AddCourseModel();
                    addCourseModel.setTrainerId(mCourseModel.getTrainerId());
                    addCourseModel.setTrainerCourseId(mCourseModel.getTrainerCourseId());
                    mViewModel.addRemoveByParams(addCourseModel);
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
