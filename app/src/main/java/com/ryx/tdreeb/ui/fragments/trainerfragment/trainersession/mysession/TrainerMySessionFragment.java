package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.mysession;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.ParentAllVideoList.Data;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.databinding.TrainerMySessionBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.ViewPagerAdapter;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession.UpcomingSessionFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession.CompletedSessionFragment;

import javax.inject.Inject;

public class TrainerMySessionFragment extends BaseFragment<TrainerMySessionBinding, TrainerMySessionViewModel> implements TrainerMySessionNavigator {

    TrainerMySessionBinding mTrainerMySessionBinding;

    private NavController navController;
    private UpcomingSessionFragment upcomingSessionFragment;
    private SessionModel mSessionModel;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    @Inject
    DataManager dataManager;

    public TrainerMySessionFragment() {

    }

    public static TrainerMySessionFragment newInstance() {
        TrainerMySessionFragment fragment = new TrainerMySessionFragment();
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
        return R.layout.trainer_my_session;
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
        mTrainerMySessionBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        //setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerMySessionBinding.getRoot());

        tvToolBarTitle = mTrainerMySessionBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mTrainerMySessionBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerMySessionBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_my_session_title));
        backImg.setPadding(10,10,10,10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

//      TODO:- Tablayout SetUP
        upcomingSessionFragment = new UpcomingSessionFragment();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(upcomingSessionFragment, "Upcoming");
        viewPagerAdapter.addFragment(new CompletedSessionFragment(), "Completed");
        mTrainerMySessionBinding.viewPager.setAdapter(viewPagerAdapter);
        mTrainerMySessionBinding.tabLayout.setupWithViewPager(mTrainerMySessionBinding.viewPager);
        wrapTabIndicatorToTitle(mTrainerMySessionBinding.tabLayout, 90, 90);

        upcomingSessionFragment.setListener(mSessionModel -> {
            TrainerMySessionFragment.this.mSessionModel = mSessionModel;
            openSession();
        });
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSession() {
        TrainerMySessionFragmentDirections.ActionTrainerMySessionFragmentToTrainerSessionDetailsFragment2 action = TrainerMySessionFragmentDirections.actionTrainerMySessionFragmentToTrainerSessionDetailsFragment2();
        action.setSessionData(mSessionModel);
        navController.navigate(action);
    }
}
