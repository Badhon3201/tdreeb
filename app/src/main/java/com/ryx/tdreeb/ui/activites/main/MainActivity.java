package com.ryx.tdreeb.ui.activites.main;


import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.ActivityMainBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {

    ActivityMainBinding mActivityMainBinding;
    private CardView cdLogout, cdLanguage;
    private CardView cdHome, cdFindCourse,cdFindTrainer,cdLiveCourse,cdVideoCourse,cdMySession,cdAllRequest,cdResource,cdMyChild,cdReportCard,cdChat,cdFavorites;
    private TextView tvName;
    private ImageView imgUserProfile;

    @Inject
    DataManager dataManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        setLocale(dataManager.getLanguage());

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
        }

        tvName = findViewById(R.id.tv_name);
        imgUserProfile = findViewById(R.id.img_profile);
        tvName.setText(dataManager.getCurrentUserName());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(imgUserProfile);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(mActivityMainBinding.bottomNavigation,
                navHostFragment.getNavController());


//        mActivityMainBinding.bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.maps_sv_error_icon);
//        mActivityMainBinding.bottomNavigation.getMenu().getItem(2).setTitle(R.string.home);

        if(dataManager.getUnderEighteen()){
            mActivityMainBinding.bottomNavigation.getMenu().removeItem(R.id.trainerChatFragment);
        }else{
            mActivityMainBinding.bottomNavigation.getMenu().removeItem(R.id.myResourceFragment);
        }
        cdHome.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.homeParentFragment);
            closeDrawer();
        });

        cdFindCourse.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.findCourseFragment);
            closeDrawer();
        });

        cdFindTrainer.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.findTrainerFragment);
            closeDrawer();
        });

        cdLiveCourse.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.liveCoursesFragment);
            closeDrawer();
        });

        cdVideoCourse.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.videoCourseFragment);
            closeDrawer();
        });

        cdMySession.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.mySessionParentFragment3);
            closeDrawer();
        });

//        cdAllSession.setOnClickListener(v -> {
//            navHostFragment.getNavController().navigate(R.id.mySessionParentFragment3);
//            closeDrawer();
//        });

        cdResource.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.myResourceFragment);
            closeDrawer();
        });

        cdMyChild.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.childrenListFragment);
            closeDrawer();
        });

        cdReportCard.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.reportCardFragment);
            closeDrawer();
        });

        cdChat.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.trainerChatFragment);
            closeDrawer();
        });

        cdFavorites.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.myFavoritesFragment);
            closeDrawer();
        });

        cdLogout.setOnClickListener(v -> {
            logout();
        });
        cdLanguage.setOnClickListener(v -> {
            onClickLanguage();
        });
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.fragmentContainerView)
                .getCurrentDestination().getId() == R.id.homeParentFragment) {
            exitApp();
            return;
        }
        super.onBackPressed();
    }

    public void openDrawer() {
        mActivityMainBinding.drawerView.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        mActivityMainBinding.drawerView.closeDrawers();
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
}