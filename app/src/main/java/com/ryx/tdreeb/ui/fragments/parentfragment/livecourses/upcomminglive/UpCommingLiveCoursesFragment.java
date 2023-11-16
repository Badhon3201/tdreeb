package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.UpcomingLiveCourseAdapter;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.databinding.FragmentUpCommingLiveCoursesBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;


public class UpCommingLiveCoursesFragment extends BaseFragment<FragmentUpCommingLiveCoursesBinding, UpCommingLiveCoursesViewModel> implements UpCommingLiveCoursesNavigator {

    FragmentUpCommingLiveCoursesBinding mFragmentUpCommingLiveCoursesBinding;
    private LiveCourseModelPass mListener;

    @Inject
    UpcomingLiveCourseAdapter mUpcomingLiveCourseAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    public UpCommingLiveCoursesFragment() {
        // Required empty public constructor
    }

    public static UpCommingLiveCoursesFragment newInstance() {
        UpCommingLiveCoursesFragment fragment = new UpCommingLiveCoursesFragment();
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
        return R.layout.fragment_up_comming_live_courses;
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
        mFragmentUpCommingLiveCoursesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentUpCommingLiveCoursesBinding.rvUpcoming.setLayoutManager(mLinearLayoutManager);
        mFragmentUpCommingLiveCoursesBinding.rvUpcoming.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentUpCommingLiveCoursesBinding.rvUpcoming.setItemAnimator(new DefaultItemAnimator());
        mFragmentUpCommingLiveCoursesBinding.rvUpcoming.setAdapter(mUpcomingLiveCourseAdapter);
        mUpcomingLiveCourseAdapter.setListener(new LiveCourseModelPass() {
            @Override
            public void onClickLiveCourses(int pos, LiveCourseModel liveCourseModel) {
                if (mListener != null) {
                    mListener.onClickLiveCourses(pos, liveCourseModel);
                }
            }
        });

        getBaseActivity().showLoading();
        mViewModel.getLiveCourses();
    }

    public void setListener(LiveCourseModelPass listener) {
        this.mListener = listener;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
    public void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        if (mLiveCourseResponse.getIsSuccess()) {
            mUpcomingLiveCourseAdapter.addItems(mLiveCourseResponse.getData().getResult());
        } else {
            Toast.makeText(getContext(), "" + mLiveCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}