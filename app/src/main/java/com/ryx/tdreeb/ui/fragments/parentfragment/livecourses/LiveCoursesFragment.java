package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.databinding.FragmentLiveCoursesBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive.MyLiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive.UpCommingLiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.ViewPagerAdapter;

import javax.inject.Inject;


public class LiveCoursesFragment extends BaseFragment<FragmentLiveCoursesBinding, LiveCoursesViewModel> implements LiveCoursesNavigator {

    FragmentLiveCoursesBinding mFragmentLiveCoursesBinding;
    private NavController navController;
    private UpCommingLiveCoursesFragment mUpCommingLiveCoursesFragment;
    private MyLiveCoursesFragment mMyLiveCoursesFragment;
    private LiveCourseModel mLiveCourseModel;
    private SessionModel mSessionModel;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    DataManager dataManager;

    public LiveCoursesFragment() {
        // Required empty public constructor
    }

    public static LiveCoursesFragment newInstance() {
        LiveCoursesFragment fragment = new LiveCoursesFragment();
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
        return R.layout.fragment_live_courses;
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
        mFragmentLiveCoursesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentLiveCoursesBinding.getRoot());

        tvToolBarTitle = mFragmentLiveCoursesBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentLiveCoursesBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentLiveCoursesBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_live_courses_title));
        backImg.setPadding(10,10,10,10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mUpCommingLiveCoursesFragment = UpCommingLiveCoursesFragment.newInstance();
        mMyLiveCoursesFragment = MyLiveCoursesFragment.newInstance();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(mUpCommingLiveCoursesFragment, "Upcoming Live Courses");
        viewPagerAdapter.addFragment(mMyLiveCoursesFragment, "My Live Courses");
        mFragmentLiveCoursesBinding.viewPager.setAdapter(viewPagerAdapter);
        mFragmentLiveCoursesBinding.tabLayout.setupWithViewPager(mFragmentLiveCoursesBinding.viewPager);
        wrapTabIndicatorToTitle(mFragmentLiveCoursesBinding.tabLayout, 40, 40);

        mUpCommingLiveCoursesFragment.setListener((pos, liveCourseModel) -> {
            LiveCoursesFragment.this.mLiveCourseModel = liveCourseModel;
            openBuy();
        });

        mMyLiveCoursesFragment.setListener(new MyResourceCallBack() {
            @Override
            public void myResourceFvrt(SessionModel mSessionModel) {
                LiveCoursesFragment.this.mSessionModel = mSessionModel;
                openSession();
            }
        });
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openBuy() {
        LiveCoursesFragmentDirections.ActionLiveCoursesFragmentToBuyTrainerFragment action = LiveCoursesFragmentDirections.actionLiveCoursesFragmentToBuyTrainerFragment();
        action.setLiveCourses(mLiveCourseModel);
        navController.navigate(action);
    }

    @Override
    public void openSession() {
        LiveCoursesFragmentDirections.ActionLiveCoursesFragmentToTrainerSessionDetailsFragment action = LiveCoursesFragmentDirections.actionLiveCoursesFragmentToTrainerSessionDetailsFragment();
        action.setSessionData(mSessionModel);
        navController.navigate(action);
    }
}