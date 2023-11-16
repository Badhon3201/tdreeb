package com.ryx.tdreeb.ui.fragments.reportcard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ReportCardAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.report.ReportResponse;
import com.ryx.tdreeb.databinding.FragmentReportCardBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class ReportCardFragment extends BaseFragment<FragmentReportCardBinding, ReportCardViewModel> implements ReportCardNavigator {

    FragmentReportCardBinding mFragmentReportCardBinding;
    @Inject
    ReportCardAdapter mReportCardAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public ReportCardFragment() {
        // Required empty public constructor
    }

    public static ReportCardFragment newInstance(String param1, String param2) {
        ReportCardFragment fragment = new ReportCardFragment();
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
        return R.layout.fragment_report_card;
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
        mFragmentReportCardBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentReportCardBinding.rvReportCard.setLayoutManager(mLinearLayoutManager);
        mFragmentReportCardBinding.rvReportCard.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentReportCardBinding.rvReportCard.setItemAnimator(new DefaultItemAnimator());
        mFragmentReportCardBinding.rvReportCard.setAdapter(mReportCardAdapter);
        getBaseActivity().showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("studentId", dataManager.getCurrentUserId() + "");
        mViewModel.getReport(map);
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
    public void onSuccessReport(ReportResponse mReportResponse) {
        getBaseActivity().hideLoading();
        mReportCardAdapter.addItems(mReportResponse.getData().getReportCard());
    }
}