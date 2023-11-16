package com.ryx.tdreeb.ui.fragments.trainerfragment.mypayment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.PackagePlanAdapter;
import com.ryx.tdreeb.databinding.TrainerMyPaymentFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrainerMyPaymentFragment extends BaseFragment<TrainerMyPaymentFragmentBinding, TrainerMyPaymentViewModel> implements TrainerMyPaymentNavigator {

    public TrainerMyPaymentFragment() {
    }

    TrainerMyPaymentFragmentBinding mTrainerMyPaymentFragmentBinding;
    @Inject
    PackagePlanAdapter mPackagePlanAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    public static TrainerMyPaymentFragment newInstance() {
        TrainerMyPaymentFragment fragment = new TrainerMyPaymentFragment();
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
        return R.layout.trainer_my_payment_fragment;
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
        mTrainerMyPaymentFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerMyPaymentFragmentBinding.rvPlan.setLayoutManager(mLinearLayoutManager);
        mTrainerMyPaymentFragmentBinding.rvPlan.addItemDecoration(new EqualSpacingItemDecoration(10));
        mTrainerMyPaymentFragmentBinding.rvPlan.setItemAnimator(new DefaultItemAnimator());
        mTrainerMyPaymentFragmentBinding.rvPlan.setAdapter(mPackagePlanAdapter);
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
