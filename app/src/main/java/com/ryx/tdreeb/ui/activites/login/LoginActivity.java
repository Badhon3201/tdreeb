package com.ryx.tdreeb.ui.activites.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;
import com.ryx.tdreeb.databinding.ActivityLoginBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.interfaces.EighteenCallBack;
import com.ryx.tdreeb.ui.activites.main.MainActivity;
import com.ryx.tdreeb.ui.activites.otp.OtpActivity;
import com.ryx.tdreeb.ui.activites.registration.RegistrationActivity;
import com.ryx.tdreeb.ui.activites.trainer.TrainerMainActivity;
import com.ryx.tdreeb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator, AdapterView.OnItemSelectedListener {

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    ActivityLoginBinding mActivityLoginBinding;
    @Inject
    DataManager dataManager;
    private int userType = -1;
    private boolean eighteenPlus = true;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        mActivityLoginBinding.loginTypeSpinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select User Type");
        categories.add("I am a Guardian");
        categories.add("I am a Personal Trainer");
        categories.add("I am a Trainee");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        mActivityLoginBinding.loginTypeSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String item = parent.getItemAtPosition(position).toString();
        if (position == 0) {
            userType = -1;
        } else if (position == 1) {
            eighteenPlus = true;
            userType = 0;
            dataManager.setUserType(DataManager.UserInMode.PARENT_MODE);
            changeEighteen();
        } else if (position == 2) {
            eighteenPlus = true;
            userType = 1;
            dataManager.setUserType(DataManager.UserInMode.TRAINER);
            changeEighteen();
        } else {
            userType = 2;
            dataManager.setUserType(DataManager.UserInMode.STUDENT);
            openEighteenDialog(new EighteenCallBack() {
                @Override
                public void onClickYes() {
                    eighteenPlus = true;
                    dataManager.setUnderEighteen(false);
                    changeEighteen();
                }

                @Override
                public void onClickNo() {
                    eighteenPlus = false;
                    dataManager.setUnderEighteen(true);
                    mActivityLoginBinding.llPhone.setVisibility(View.GONE);
                    mActivityLoginBinding.llPhoneF.setVisibility(View.GONE);
                    mActivityLoginBinding.llUsername.setVisibility(View.VISIBLE);
                    mActivityLoginBinding.llEtUsername.setVisibility(View.VISIBLE);
                    mActivityLoginBinding.regBtn.setText(getString(R.string.change_user));
                    mActivityLoginBinding.submitButton.setText(getString(R.string.login_title));
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void changeEighteen(){
        mActivityLoginBinding.regBtn.setText(getString(R.string.new_user_registration));
        mActivityLoginBinding.submitButton.setText(getString(R.string.submit_btn_title));
        mActivityLoginBinding.llPhone.setVisibility(View.VISIBLE);
        mActivityLoginBinding.llPhoneF.setVisibility(View.VISIBLE);
        mActivityLoginBinding.llUsername.setVisibility(View.GONE);
        mActivityLoginBinding.llEtUsername.setVisibility(View.GONE);
    }

    @Override
    public void openOtp() {
        if (userType != -1) {
            startActivity(OtpActivity.newIntent(this));
        } else {
            Toast.makeText(this, "" + getString(R.string.select_user_type, "Login"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openReg() {
        if(eighteenPlus){
            if (userType != -1) {
                startActivity(RegistrationActivity.newIntent(this));
            } else {
                Toast.makeText(this, "" + getString(R.string.select_user_type, "Registration"), Toast.LENGTH_SHORT).show();
            }
        }else {
            changeEighteen();
        }
    }

    @Override
    public void openUnderEighteen() {

    }

    @Override
    public void submitLoginData() {
        hideKeyboard();
        if(eighteenPlus) {
            if (checkData()) {
                if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                    showLoading();
                    Map<String, String> params = new HashMap<>();
                    params.put("userName", mActivityLoginBinding.edtPhone.getText().toString());
                    params.put("password", mActivityLoginBinding.edtPassword.getText().toString());
                    params.put("token", "");
                    params.put("firebaseToken", "");
                    mViewModel.userParentLogin(params);
                } else if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                    showLoading();
                    Map<String, String> params = new HashMap<>();
                    params.put("userName", mActivityLoginBinding.edtPhone.getText().toString());
                    params.put("password", mActivityLoginBinding.edtPassword.getText().toString());
                    params.put("token", "");
                    params.put("firebaseToken", "");
                    mViewModel.userTeacherLogin(params);
                } else {
                    showLoading();
                    Map<String, String> params = new HashMap<>();
                    params.put("userName", mActivityLoginBinding.edtPhone.getText().toString());
                    params.put("password", mActivityLoginBinding.edtPassword.getText().toString());
                    params.put("token", "");
                    params.put("firebaseToken", "");
                    mViewModel.userStudentLogin(params);
                }
            }
        }else{
            if(checkData()){
                showLoading();
                Map<String, String> params = new HashMap<>();
                params.put("userName", mActivityLoginBinding.edtUsername.getText().toString());
                params.put("password", mActivityLoginBinding.edtPassword.getText().toString());
                params.put("token", "");
                params.put("firebaseToken", "");
                mViewModel.userStudentLogin(params);
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

    private boolean checkData() {
        if(eighteenPlus){
            if (mActivityLoginBinding.edtPhone.getText().toString().isEmpty()) {
                Toast.makeText(this, "" + getString(R.string.enter_phone_number), Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            if (mActivityLoginBinding.edtUsername.getText().toString().isEmpty()) {
                Toast.makeText(this, "" + getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (mActivityLoginBinding.edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "" + getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onSuccessLogin(RegistrationResponse mRegistrationResponse) {
        hideLoading();
        if (mRegistrationResponse.getIsSuccess()){
            dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
            if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                dataManager.setUserProfile(mRegistrationResponse.getData().getUser());
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getParentId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getFirstName() + " " + mRegistrationResponse.getData().getUser().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                startActivity(MainActivity.newIntent(this));
                finishAffinity();
            }else if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()){
                dataManager.setUserProfile(null);
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getTrainerId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                startActivity(TrainerMainActivity.newIntent(this));
                finishAffinity();
            }else{
                dataManager.setUserProfile(mRegistrationResponse.getData().getUser());
                dataManager.setCurrentUserId(mRegistrationResponse.getData().getUser().getStudentId());
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getUser().getFirstName() + " " + mRegistrationResponse.getData().getUser().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getUser().getImage());
                startActivity(MainActivity.newIntent(this));
                finishAffinity();
            }
        }else {
            Toast.makeText(this, ""+mRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}