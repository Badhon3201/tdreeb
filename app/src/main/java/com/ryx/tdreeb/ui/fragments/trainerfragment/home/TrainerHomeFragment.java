package com.ryx.tdreeb.ui.fragments.trainerfragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.TrainerHomeSessionAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.TrainerHomeFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrainerHomeFragment extends BaseFragment<TrainerHomeFragmentBinding, TrainerHomeViewModel> implements TrainerHomeNavigator {

    TrainerHomeFragmentBinding mTrainerHomeFragmentBinding;
    private NavController navController;
    private ImageView profileImage, drawerIcon;

    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    TrainerHomeSessionAdapter mTrainerHomeSession;
    @Inject
    DataManager dataManager;

    public TrainerHomeFragment() {
    }

    public static TrainerHomeFragment newInstance() {
        TrainerHomeFragment fragment = new TrainerHomeFragment();
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
        return R.layout.trainer_home_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
//        mBlogAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mTrainerHomeFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        profileImage = mTrainerHomeFragmentBinding.getRoot().findViewById(R.id.profile_image);
        drawerIcon = mTrainerHomeFragmentBinding.getRoot().findViewById(R.id.drawer_icon);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImage);

        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerHomeFragmentBinding.getRoot());
        mTrainerHomeFragmentBinding.allRequestLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerAllRequestFragment);
        });
        mTrainerHomeFragmentBinding.mySessionsLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerMySessionFragment);
        });
        mTrainerHomeFragmentBinding.myCoursesLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerMyCoursesFragment);
        });

        mTrainerHomeFragmentBinding.liveCoursesLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerLiveCoursesFragment);
        });

        mTrainerHomeFragmentBinding.videoCoursesLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerVideoCoursesFragment);
        });

        mTrainerHomeFragmentBinding.chatLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_navigation_chat);
        });

        mTrainerHomeFragmentBinding.myResourcesLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerMyResourcesFragment);
        });

        mTrainerHomeFragmentBinding.myPaymentLinear.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerMyPaymentFragment);
        });

        mTrainerHomeFragmentBinding.manageScheduleLl.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_navigation_my_schedule);
        });
        mTrainerHomeFragmentBinding.tvViewAll.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerMySessionFragment);
        });

        profileImage.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_trainerProfileFragment);
        });

        drawerIcon.setOnClickListener(v -> {
            openMainDrawer();
        });

        //Adapter Setup
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerHomeFragmentBinding.rvCommingSession.setLayoutManager(mLinearLayoutManager);
        mTrainerHomeFragmentBinding.rvCommingSession.addItemDecoration(new EqualSpacingItemDecoration(20));
        mTrainerHomeFragmentBinding.rvCommingSession.setItemAnimator(new DefaultItemAnimator());
        mTrainerHomeFragmentBinding.rvCommingSession.setAdapter(mTrainerHomeSession);
        mTrainerHomeSession.setListener(mSessionModel -> {
            TrainerHomeFragmentDirections.ActionNavigationHomeToTrainerSessionDetailsFragment2 action = TrainerHomeFragmentDirections.actionNavigationHomeToTrainerSessionDetailsFragment2();
            action.setSessionData(mSessionModel);
            navController.navigate(action);
        });

//        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "");
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openMainDrawer() {
        getBaseActivity().openDrawer();
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
            mTrainerHomeSession.addItems(mSessionResponse.getData().getSessions());
        }
    }
}
