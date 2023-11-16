package com.ryx.tdreeb.ui.activites.registration;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;
import com.ryx.tdreeb.data.model.api.registration.SendRegistrationData;
import com.ryx.tdreeb.databinding.ActivityRegistrationBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.activites.login.LoginActivity;
import com.ryx.tdreeb.ui.activites.otp.OtpActivity;
import com.ryx.tdreeb.ui.base.BaseActivity;

import javax.inject.Inject;


public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel> implements RegistrationNavigator {

    ActivityRegistrationBinding mActivityRegistrationBinding;

    private String userGender = "Male";

    @Inject
    DataManager dataManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegistrationBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openOtp() {
        startActivity(OtpActivity.newIntent(this));
    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.newIntent(this));
    }

    @Override
    public void submitRegistrationData() {
        hideKeyboard();
        int selectedId = mActivityRegistrationBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMale) {
            userGender = "Male";
        } else {
            userGender = "Female";
        }

        if (checkData()) {
            if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                showLoading();
                SendRegistrationData mSendRegistrationData = new SendRegistrationData();
                mSendRegistrationData.setPhoneNo(mActivityRegistrationBinding.edtPhone.getText().toString().trim());
                mSendRegistrationData.setPassword(mActivityRegistrationBinding.edtPass.getText().toString());
                mSendRegistrationData.setFirstName(mActivityRegistrationBinding.edtFirstName.getText().toString());
                mSendRegistrationData.setLastName(mActivityRegistrationBinding.edtLastName.getText().toString());
                mSendRegistrationData.setEmail(mActivityRegistrationBinding.edtEmail.getText().toString());
                mSendRegistrationData.setUserName(mActivityRegistrationBinding.edtUsername.getText().toString());
                mSendRegistrationData.setGender(userGender);
                mSendRegistrationData.setStatus("STEP_ONE");
                mViewModel.userParentReg(mSendRegistrationData);
            } else if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                showLoading();
                SendRegistrationData mSendRegistrationData = new SendRegistrationData();
                mSendRegistrationData.setPhoneNo(mActivityRegistrationBinding.edtPhone.getText().toString().trim());
                mSendRegistrationData.setPassword(mActivityRegistrationBinding.edtPass.getText().toString());
                mSendRegistrationData.setFirstName(mActivityRegistrationBinding.edtFirstName.getText().toString());
                mSendRegistrationData.setLastName(mActivityRegistrationBinding.edtLastName.getText().toString());
                mSendRegistrationData.setEmail(mActivityRegistrationBinding.edtEmail.getText().toString());
                mSendRegistrationData.setUserName(mActivityRegistrationBinding.edtUsername.getText().toString());
                mSendRegistrationData.setGender(userGender);
                mSendRegistrationData.setStatus("STEP_ONE");
                mViewModel.userTeacherReg(mSendRegistrationData);
            }else {
                showLoading();
                SendRegistrationData mSendRegistrationData = new SendRegistrationData();
                mSendRegistrationData.setPhoneNo(mActivityRegistrationBinding.edtPhone.getText().toString().trim());
                mSendRegistrationData.setPassword(mActivityRegistrationBinding.edtPass.getText().toString());
                mSendRegistrationData.setFirstName(mActivityRegistrationBinding.edtFirstName.getText().toString());
                mSendRegistrationData.setLastName(mActivityRegistrationBinding.edtLastName.getText().toString());
                mSendRegistrationData.setEmail(mActivityRegistrationBinding.edtEmail.getText().toString());
                mSendRegistrationData.setUserName(mActivityRegistrationBinding.edtUsername.getText().toString());
                mSendRegistrationData.setGender(userGender);
                mSendRegistrationData.setStatus("STEP_ONE");
                mViewModel.userStudentReg(mSendRegistrationData);
            }
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            handleApiError(anError);
        }
    }

    @Override
    public void onSuccessLogin(RegistrationResponse mRegistrationResponse) {
        hideLoading();
        if (mRegistrationResponse.getIsSuccess()) {
            if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                dataManager.setUserProfile(mRegistrationResponse.getData().getUser());
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getParentId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getFirstName() + " " + mRegistrationResponse.getData().getUser().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                openOtp();
            } else if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                dataManager.setUserProfile(null);
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getTrainerId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                openOtp();
            }else{
                dataManager.setUserProfile(mRegistrationResponse.getData().getUser());
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getStudentId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getFirstName() + " " + mRegistrationResponse.getData().getUser().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                openOtp();
            }
        } else {
            Toast.makeText(this, "" + mRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


//  TODO:- User define Function
    private void setUp() {
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            mActivityRegistrationBinding.tvRegTitle.setText(R.string.guardian_reg_title);
        }else if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
            mActivityRegistrationBinding.tvRegTitle.setText(R.string.trainer_reg_title);
        }else {
            mActivityRegistrationBinding.tvRegTitle.setText(R.string.trainee_reg_title);
        }
    }


    private boolean checkData() {
        if (mActivityRegistrationBinding.edtFirstName.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtFirstName.setError(getString(R.string.enter_first_name));
            Toast.makeText(this, "" + getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityRegistrationBinding.edtPhone.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtPhone.setError(getString(R.string.enter_phone_number));
            Toast.makeText(this, "" + getString(R.string.enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mActivityRegistrationBinding.edtUsername.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtUsername.setError(getString(R.string.enter_username));
            Toast.makeText(this, "" + getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mActivityRegistrationBinding.edtEmail.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtEmail.setError(getString(R.string.enter_email));
            Toast.makeText(this, "" + getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mActivityRegistrationBinding.edtLastName.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtLastName.setError(getString(R.string.enter_last_name));
            Toast.makeText(this, "" + getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mActivityRegistrationBinding.edtPass.getText().toString().isEmpty()) {
            mActivityRegistrationBinding.edtPass.setError(getString(R.string.enter_your_password));
            Toast.makeText(this, "" + getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mActivityRegistrationBinding.edtPass.getText().toString().equals(mActivityRegistrationBinding.edtConfirmPass.getText().toString())) {
            mActivityRegistrationBinding.edtConfirmPass.setError(getString(R.string.not_match_password));
            Toast.makeText(this, "" + getString(R.string.not_match_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}