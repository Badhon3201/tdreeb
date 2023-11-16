package com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.TrainerAllRequestBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.ViewPagerAdapter;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept.AcceptedRequestFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest.NewRequestFragment;

import javax.inject.Inject;

public class TrainerAllRequestFragment extends BaseFragment<TrainerAllRequestBinding, TrainerAllRequestViewModel> implements TrainerAllRequestNavigator {


    TrainerAllRequestBinding mTrainerAllRequestBinding;
    private ImageView backImg, profileImg;

    @Inject
    DataManager dataManager;

    public TrainerAllRequestFragment(){}

    public static TrainerAllRequestFragment newInstance() {
        TrainerAllRequestFragment fragment = new TrainerAllRequestFragment();
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
        return R.layout.trainer_all_request;
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
        mTrainerAllRequestBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        backImg = mTrainerAllRequestBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerAllRequestBinding.getRoot().findViewById(R.id.profile_image);
        backImg.setOnClickListener(v -> {
            getBaseActivity().openDrawer();
        });

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(NewRequestFragment.getInstance(),"New Request");
        viewPagerAdapter.addFragment(AcceptedRequestFragment.getInstance(),"Accepted");
        mTrainerAllRequestBinding.viewPager.setAdapter(viewPagerAdapter);
        mTrainerAllRequestBinding.tabLayout.setupWithViewPager(mTrainerAllRequestBinding.viewPager);
        wrapTabIndicatorToTitle(mTrainerAllRequestBinding.tabLayout,90,90);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(, container, false);
//
////        tabLayout = view.findViewById(R.id.tabLayout);
////        viewPager = view.findViewById(R.id.viewPager);
////        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager() != null ? getFragmentManager() : null);
////        viewPagerAdapter.addFragment(NewRequestFragment.getInstance(),"New Request");
////        viewPagerAdapter.addFragment(AcceptedRequestFragment.getInstance(),"Accepted");
////        viewPager.setAdapter(viewPagerAdapter);
////        tabLayout.setupWithViewPager(viewPager);
//
//        return  view;
//    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
