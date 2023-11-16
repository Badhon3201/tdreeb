package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyLiveCoursesAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.FragmentMyLiveCoursesBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;


public class MyLiveCoursesFragment extends BaseFragment<FragmentMyLiveCoursesBinding, MyLiveCoursesViewModel> implements MyLiveCoursesNavigator {

    FragmentMyLiveCoursesBinding mFragmentMyLiveCoursesBinding;
    @Inject
    MyLiveCoursesAdapter mMyLiveCoursesAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private MyResourceCallBack mListener;

    public MyLiveCoursesFragment() {
        // Required empty public constructor
    }

    public static MyLiveCoursesFragment newInstance() {
        MyLiveCoursesFragment fragment = new MyLiveCoursesFragment();
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
        return R.layout.fragment_my_live_courses;
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
        mFragmentMyLiveCoursesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentMyLiveCoursesBinding.rvMyLive.setLayoutManager(mLinearLayoutManager);
        mFragmentMyLiveCoursesBinding.rvMyLive.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentMyLiveCoursesBinding.rvMyLive.setItemAnimator(new DefaultItemAnimator());
        mFragmentMyLiveCoursesBinding.rvMyLive.setAdapter(mMyLiveCoursesAdapter);
        mMyLiveCoursesAdapter.setListener(new MyResourceCallBack() {
            @Override
            public void myResourceFvrt(SessionModel mSessionModel) {
                if (mListener != null) {
                    mListener.myResourceFvrt(mSessionModel);
                }
            }
        });
        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "LiveCourse");
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void setListener(MyResourceCallBack listener) {
        this.mListener = listener;
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
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mMyLiveCoursesAdapter.addItems(mSessionResponse.getData().getSessions());
        }
    }
}