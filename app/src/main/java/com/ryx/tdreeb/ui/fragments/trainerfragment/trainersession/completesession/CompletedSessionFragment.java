package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.SessionAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.CompletedMySessionBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class CompletedSessionFragment extends BaseFragment<CompletedMySessionBinding, CompletedSessionViewModel> implements CompletedSessionNavigator {

    CompletedMySessionBinding mCompletedMySessionBinding;

    @Inject
    SessionAdapter mSessionAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public CompletedSessionFragment() {
    }

    public static CompletedSessionFragment getInstance() {
        return new CompletedSessionFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.completed_my_session;
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
        mCompletedMySessionBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCompletedMySessionBinding.rvComplete.setLayoutManager(mLinearLayoutManager);
        mCompletedMySessionBinding.rvComplete.addItemDecoration(new EqualSpacingItemDecoration(20));
        mCompletedMySessionBinding.rvComplete.setItemAnimator(new DefaultItemAnimator());
        mCompletedMySessionBinding.rvComplete.setAdapter(mSessionAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId());
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getView() != null){
        }
    }

    @Override
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mSessionAdapter.addItems(mSessionResponse.getData().getSessions());
        }
    }
}
