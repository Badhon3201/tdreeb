package com.ryx.tdreeb.ui.fragments.parentfragment.mysession;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.databinding.FragmentMySessionParentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.ViewPagerAdapter;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession.CompletedSessionFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession.UpcomingSessionFragment;

import javax.inject.Inject;


public class MySessionParentFragment extends BaseFragment<FragmentMySessionParentBinding, MySessionParentViewModel> implements MySessionParentNavigator {

    FragmentMySessionParentBinding mFragmentMySessionParentBinding;

    private NavController navController;
    private UpcomingSessionFragment upcomingSessionFragment;
    private SessionModel mSessionModel;

    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    public MySessionParentFragment() {
        // Required empty public constructor
    }

    public static MySessionParentFragment newInstance() {
        MySessionParentFragment fragment = new MySessionParentFragment();
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
        return R.layout.fragment_my_session_parent;
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
        mFragmentMySessionParentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentMySessionParentBinding.getRoot());

        tvToolBarTitle = mFragmentMySessionParentBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentMySessionParentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentMySessionParentBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.my_resources));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

//      TODO:- Tablayout SetUP
        upcomingSessionFragment = UpcomingSessionFragment.getInstance();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(upcomingSessionFragment, "Upcoming");
        viewPagerAdapter.addFragment(CompletedSessionFragment.getInstance(), "Completed");
        mFragmentMySessionParentBinding.viewPager.setAdapter(viewPagerAdapter);
        mFragmentMySessionParentBinding.tabLayout.setupWithViewPager(mFragmentMySessionParentBinding.viewPager);
        wrapTabIndicatorToTitle(mFragmentMySessionParentBinding.tabLayout, 90, 90);

        upcomingSessionFragment.setListener(mSessionModel -> {
            MySessionParentFragment.this.mSessionModel = mSessionModel;
            openSession();
        });
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSession() {
        MySessionParentFragmentDirections.ActionMySessionParentFragment3ToTrainerSessionDetailsFragment action = MySessionParentFragmentDirections.actionMySessionParentFragment3ToTrainerSessionDetailsFragment();
        action.setSessionData(mSessionModel);
        navController.navigate(action);
    }
}