package com.ryx.tdreeb.ui.activites.trainer;


import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.ActivityTrainerMainBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.base.BaseActivity;

import javax.inject.Inject;

public class TrainerMainActivity extends BaseActivity<ActivityTrainerMainBinding, TrainerMainViewModel> implements TrainerMainNavigator {

    ActivityTrainerMainBinding mActivityTrainerMainBinding;
    private CardView cdLogout,cdLanguage;
    private CardView cdHome, cdFindCourse,cdFindTrainer,cdLiveCourse,cdVideoCourse,cdMySession,cdAllRequest,cdResource,cdMyChild,cdReportCard,cdChat,cdFavorites;
    private TextView tvName;
    private ImageView imgUserProfile;
    private NavHostFragment navHostFragment;

    @Inject
    DataManager dataManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, TrainerMainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trainer_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityTrainerMainBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navHostFragment);
        NavigationUI.setupWithNavController(mActivityTrainerMainBinding.bottomNavView,
                navHostFragment.getNavController());

        setUp();

    }

    private void setUp() {
        cdLogout = findViewById(R.id.cd_logout);
        cdLanguage = findViewById(R.id.cd_language);
        cdHome = findViewById(R.id.cd_home);
        cdFindCourse = findViewById(R.id.cd_my_request);
        cdFindTrainer = findViewById(R.id.cd_my_profile);
        cdLiveCourse = findViewById(R.id.cd_notification);
        cdVideoCourse = findViewById(R.id.cd_contact_us);
        cdMySession = findViewById(R.id.cd_privacy_policy);
        cdAllRequest = findViewById(R.id.cd_all_request);
        cdResource = findViewById(R.id.cd_resource);
        cdMyChild = findViewById(R.id.cd_my_child);
        cdReportCard = findViewById(R.id.cd_report_card);
        cdChat = findViewById(R.id.cd_chat);
        cdFavorites = findViewById(R.id.cd_favorites);

        if(dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()){
            cdReportCard.setVisibility(View.GONE);
            cdAllRequest.setVisibility(View.GONE);
        }else if(dataManager.getUserType() == DataManager.UserInMode.STUDENT.getType()){
            cdMyChild.setVisibility(View.GONE);
            cdAllRequest.setVisibility(View.GONE);
        }else{
            cdReportCard.setVisibility(View.GONE);
            cdMyChild.setVisibility(View.GONE);
            cdFindCourse.setVisibility(View.GONE);
            cdFindTrainer.setVisibility(View.GONE);
            cdFavorites.setVisibility(View.GONE);
        }

        cdHome.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.navigation_home);
            closeDrawer();
        });


        cdLiveCourse.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerLiveCoursesFragment);
            closeDrawer();
        });

        cdVideoCourse.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerVideoCoursesFragment);
            closeDrawer();
        });

        cdMySession.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerMySessionFragment);
            closeDrawer();
        });

        cdAllRequest.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerAllRequestFragment);
            closeDrawer();
        });

        cdResource.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerMyResourcesFragment);
            closeDrawer();
        });


        cdChat.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.navigation_chat);
            closeDrawer();
        });

//        cdFavorites.setOnClickListener(v -> {
//            navHostFragment.getNavController().navigate(R.id.myFavoritesFragment);
//            closeDrawer();
//        });

        cdLogout.setOnClickListener(v -> {
            logout();
        });
        cdLanguage.setOnClickListener(v -> {
            onClickLanguage();
        });

        tvName = findViewById(R.id.tv_name);
        imgUserProfile = findViewById(R.id.img_profile);
        tvName.setText(dataManager.getCurrentUserName());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(imgUserProfile);
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.navHostFragment)
                .getCurrentDestination().getId() == R.id.navigation_home) {
            exitApp();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void logout() {
        signOut();
        closeDrawer();
    }

    @Override
    public void onClickLanguage() {
        languageDialog(new SetLanguage() {
            @Override
            public void languageEN() {
                setUp();
                recreate();
            }

            @Override
            public void languageAR() {
                setUp();
                recreate();
            }
        });
        closeDrawer();
    }

    public void openDrawer() {
        mActivityTrainerMainBinding.drawerView.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        mActivityTrainerMainBinding.drawerView.closeDrawers();
    }
}