package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.RequestAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.AcceptedRequestLayoutBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class AcceptedRequestFragment extends BaseFragment<AcceptedRequestLayoutBinding, AcceptedRequestViewModel> implements AcceptedRequestNavigator {

    private AcceptedRequestLayoutBinding mAcceptedRequestLayoutBinding;

    @Inject
    RequestAdapter mRequestAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public AcceptedRequestFragment() {
    }

    public static AcceptedRequestFragment getInstance() {
        return new AcceptedRequestFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.accepted_request_layout;
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
        mAcceptedRequestLayoutBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAcceptedRequestLayoutBinding.rvAcceptRequest.setLayoutManager(mLinearLayoutManager);
        mAcceptedRequestLayoutBinding.rvAcceptRequest.addItemDecoration(new EqualSpacingItemDecoration(20));
        mAcceptedRequestLayoutBinding.rvAcceptRequest.setItemAnimator(new DefaultItemAnimator());
        mAcceptedRequestLayoutBinding.rvAcceptRequest.setAdapter(mRequestAdapter);

        getBaseActivity().showLoading();
        Map<String,String> map = new HashMap<>();
        map.put("trainerId",dataManager.getCurrentUserId()+"");
        map.put("status","Accept");
        mViewModel.getAllRequest(map);
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
    public void onSuccessAllRequest(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getIsSuccess()) {
            mRequestAdapter.addItems(mSessionResponse.getData().getSessions(),true);
        } else {
            Toast.makeText(getContext(), "" + mSessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
