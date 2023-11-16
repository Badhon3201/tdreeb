package com.ryx.tdreeb.ui.fragments.parentfragment.review;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ReviewAdapter;
import com.ryx.tdreeb.databinding.FragmentReviewBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class ReviewFragment extends BaseFragment<FragmentReviewBinding, ReviewViewModel> implements ReviewNavigator {

    FragmentReviewBinding mFragmentReviewBinding;

    @Inject
    ReviewAdapter mReviewAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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
        return R.layout.fragment_review;
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
        mFragmentReviewBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentReviewBinding.rvReview.setLayoutManager(mLinearLayoutManager);
        mFragmentReviewBinding.rvReview.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentReviewBinding.rvReview.setItemAnimator(new DefaultItemAnimator());
        mFragmentReviewBinding.rvReview.setAdapter(mReviewAdapter);
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}