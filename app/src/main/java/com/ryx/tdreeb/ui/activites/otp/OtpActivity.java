package com.ryx.tdreeb.ui.activites.otp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.ActivityOtpBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.activites.main.MainActivity;
import com.ryx.tdreeb.ui.activites.trainer.TrainerMainActivity;
import com.ryx.tdreeb.ui.base.BaseActivity;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpViewModel> implements OtpNavigator {

    ActivityOtpBinding mActivityOtpBinding;
    @Inject
    DataManager dataManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, OtpActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOtpBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openMain() {
        startActivity(MainActivity.newIntent(this));
        finishAffinity();
    }

    @Override
    public void openTrainerMain() {
        startActivity(TrainerMainActivity.newIntent(this));
        finishAffinity();
    }

    @Override
    public void onClickBtn() {
        dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
        if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
            openTrainerMain();
        } else {
            openMain();
        }
    }
}