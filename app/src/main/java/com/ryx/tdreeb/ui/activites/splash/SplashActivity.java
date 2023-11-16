package com.ryx.tdreeb.ui.activites.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.ActivitySplashBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.activites.login.LoginActivity;
import com.ryx.tdreeb.ui.activites.main.MainActivity;
import com.ryx.tdreeb.ui.activites.trainer.TrainerMainActivity;
import com.ryx.tdreeb.ui.base.BaseActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    ActivitySplashBinding mActivitySplashBinding;
    @Inject
    DataManager dataManager;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(2000);
        mActivitySplashBinding.logo.setAnimation(fadeIn);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER.getType()) {
                if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                    openTrainer();
                } else {
                    openMain();
                }
            } else {
                openLogin();
            }

        }, 3000);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public void openMain() {
        startActivity(MainActivity.newIntent(this));
        finish();
    }

    @Override
    public void openTrainer() {
        startActivity(TrainerMainActivity.newIntent(this));
        finish();
    }
}