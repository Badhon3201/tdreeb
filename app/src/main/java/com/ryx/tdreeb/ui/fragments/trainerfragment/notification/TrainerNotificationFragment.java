package com.ryx.tdreeb.ui.fragments.trainerfragment.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.TrainerNotificationAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.TrainerNotificationBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrainerNotificationFragment extends BaseFragment<TrainerNotificationBinding, TrainerNotificationViewModel> implements TrainerNotificationNavigator {

    public TrainerNotificationFragment() {}

    TrainerNotificationBinding mTrainerNotificationBinding;

    private ImageView backImg,profileImg;

    @Inject
    TrainerNotificationAdapter mTrainerNotificationAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public static TrainerNotificationFragment newInstance() {
        TrainerNotificationFragment fragment = new TrainerNotificationFragment();
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
        return R.layout.trainer_notification;
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
        mTrainerNotificationBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        backImg = mTrainerNotificationBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerNotificationBinding.getRoot().findViewById(R.id.profile_image);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerNotificationBinding.rvNotification.setLayoutManager(mLinearLayoutManager);
        mTrainerNotificationBinding.rvNotification.addItemDecoration(new EqualSpacingItemDecoration(20));
        mTrainerNotificationBinding.rvNotification.setItemAnimator(new DefaultItemAnimator());
        mTrainerNotificationBinding.rvNotification.setAdapter(mTrainerNotificationAdapter);
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
