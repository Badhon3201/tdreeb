package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.RequestAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.AllRequestUpdate;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.NewRequestLayoutBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AllRequestCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class NewRequestFragment extends BaseFragment<NewRequestLayoutBinding, NewRequestViewModel> implements NewRequestNavigator {

    private NewRequestLayoutBinding mNewRequestLayoutBinding;

    @Inject
    RequestAdapter mRequestAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public NewRequestFragment() {
    }

    public static NewRequestFragment getInstance() {
        return new NewRequestFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_request_layout;
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
        mNewRequestLayoutBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNewRequestLayoutBinding.rvNewRequest.setLayoutManager(mLinearLayoutManager);
        mNewRequestLayoutBinding.rvNewRequest.addItemDecoration(new EqualSpacingItemDecoration(20));
        mNewRequestLayoutBinding.rvNewRequest.setItemAnimator(new DefaultItemAnimator());
        mNewRequestLayoutBinding.rvNewRequest.setAdapter(mRequestAdapter);

        mRequestAdapter.setListener(new AllRequestCallBack() {
            @Override
            public void onClickView(SessionModel mSessionModel) {

            }

            @Override
            public void onClickAccept(SessionModel mSessionModel) {
                getBaseActivity().showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("sessionId", mSessionModel.getSessionId() + "");
                map.put("status", "Accept");
                mViewModel.getRequestUpdate(map);
            }

            @Override
            public void onClickCancel(SessionModel mSessionModel) {
                getBaseActivity().showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("sessionId", mSessionModel.getSessionId() + "");
                map.put("status", "Cancel");
                mViewModel.getRequestUpdate(map);
            }
        });
        getData();
    }

    private void getData() {
        getBaseActivity().showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("trainerId", dataManager.getCurrentUserId() + "");
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
            mRequestAdapter.addItems(mSessionResponse.getData().getSessions(), false);
        } else {
            Toast.makeText(getContext(), "" + mSessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessRequestUpdate(AllRequestUpdate mAllRequestUpdate) {
        getBaseActivity().hideLoading();
        if (mAllRequestUpdate.getIsSuccess()) {
            getData();
        } else {
            Toast.makeText(getContext(), "" + mAllRequestUpdate.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
