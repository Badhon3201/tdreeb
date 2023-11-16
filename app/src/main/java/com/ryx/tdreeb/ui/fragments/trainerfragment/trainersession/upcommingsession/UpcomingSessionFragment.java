package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.SessionAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.UpcomingMySessionBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.inject.Inject;

public class UpcomingSessionFragment extends BaseFragment<UpcomingMySessionBinding, UpcomingSessionViewModel> implements UpcomingSessionNavigator {

    UpcomingMySessionBinding mUpcomingMySessionBinding;

    @Inject
    SessionAdapter mSessionAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private MyResourceCallBack mListener;
    private NavController navController;

    public UpcomingSessionFragment() {
    }

    public static UpcomingSessionFragment getInstance() {
        return new UpcomingSessionFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.upcoming_my_session;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mUpcomingMySessionBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getView() != null){

        }
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mUpcomingMySessionBinding.getRoot());

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mUpcomingMySessionBinding.rvUpcomming.setLayoutManager(mLinearLayoutManager);
        mUpcomingMySessionBinding.rvUpcomming.addItemDecoration(new EqualSpacingItemDecoration(20));
        mUpcomingMySessionBinding.rvUpcomming.setItemAnimator(new DefaultItemAnimator());
        mUpcomingMySessionBinding.rvUpcomming.setAdapter(mSessionAdapter);

        mSessionAdapter.setListener(new MyResourceCallBack() {
            @Override
            public void myResourceFvrt(SessionModel mSessionModel) {
                if(mListener!=null){
                    mListener.myResourceFvrt(mSessionModel);
                }
            }
        });

        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "");
    }

    public void setListener(MyResourceCallBack listener) {
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
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mSessionAdapter.addItems(mSessionResponse.getData().getSessions());
        }
    }
}
