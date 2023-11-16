

package com.ryx.tdreeb.ui.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.ApiError;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.di.component.DaggerActivityComponent;
import com.ryx.tdreeb.di.module.ActivityModule;
import com.ryx.tdreeb.interfaces.EighteenCallBack;
import com.ryx.tdreeb.interfaces.SimpleDialogClick;
import com.ryx.tdreeb.ui.activites.login.LoginActivity;
import com.ryx.tdreeb.utils.AppConstants;
import com.ryx.tdreeb.utils.CommonUtils;
import com.ryx.tdreeb.utils.NetworkUtils;


import java.util.Locale;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities

    private ProgressDialog mProgressDialog;

    private T mViewDataBinding;

    @Inject
    protected V mViewModel;
    @Inject
    DataManager dataManager;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        setLocale(dataManager.getLanguage());
        performDataBinding();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    private ActivityComponent getBuildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((MvvmApp) getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    public abstract void performDependencyInjection(ActivityComponent buildComponent);

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    public void openDialog(String msg, SimpleDialogClick mSimpleDialogClick) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.mez_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        ImageView dialogButton = dialog.findViewById(R.id.img_close);
        dialogButton.setOnClickListener(v -> {
            dialog.dismiss();
            mSimpleDialogClick.onClose();
        });

        dialog.show();
    }

    public void openEighteenDialog(EighteenCallBack mEighteenCallBack) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.under_eighteen_dialog);

        AppCompatButton btnClose = dialog.findViewById(R.id.btn_close);
        AppCompatButton btnYes = dialog.findViewById(R.id.btn_yes);
        AppCompatButton btnNo = dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            mEighteenCallBack.onClickYes();
        });

        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
            mEighteenCallBack.onClickNo();
        });

        dialog.show();
    }

    public void openNoInternetDialog(SimpleDialogClick mSimpleDialogClick) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_dialog);

        AppCompatButton btnTry = dialog.findViewById(R.id.btn_try);
        btnTry.setOnClickListener(v -> {
            if (isNetworkConnected()) {
                dialog.dismiss();
                mSimpleDialogClick.onClose();
            }
        });
        dialog.show();
    }
//
//
//    public void openDialog(String msg, PackageModel packageModel, BuyPackageDialogClick mBuyPackageDialogClick) {
//        final Dialog dialog = new Dialog(this);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.buy_package_dialog);
//
//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        TextView txtPrice = (TextView) dialog.findViewById(R.id.tv_price);
//        TextView txtDiscountPrice = (TextView) dialog.findViewById(R.id.tv_discount_price);
//        TextView tvValidity = (TextView) dialog.findViewById(R.id.tv_validity);
//
//        if (packageModel.getDuration() != null) {
//            String duration = (Integer.parseInt(packageModel.getDuration()) / 30) + "";
//            tvValidity.setText(getString(R.string.validity, CommonUtils.getBDDigit(duration)));
//        }
//
//        if (packageModel.getDiscount() != null) {
//            txtPrice.setText(CommonUtils.getBDDigit(packageModel.getDiscount()));
//            txtDiscountPrice.setText(CommonUtils.getBDDigit(packageModel.getPrice()));
//        } else {
//            txtPrice.setText(CommonUtils.getBDDigit(packageModel.getPrice()));
//            txtDiscountPrice.setText("");
//        }
//        text.setText(msg);
//
//        ImageView dialogButton = dialog.findViewById(R.id.img_close);
//        AppCompatButton btnBuy = dialog.findViewById(R.id.btn_buy);
//
//        btnBuy.setOnClickListener(v -> {
//            dialog.dismiss();
//            mBuyPackageDialogClick.onPackage(packageModel);
//        });
//
//        dialogButton.setOnClickListener(v -> {
//            dialog.dismiss();
//            mBuyPackageDialogClick.onClose();
//        });
//
//        dialog.show();
//    }
//
//    public void openDialogForStartExam(String msg, StartExam mStartExam) {
//        final Dialog dialog = new Dialog(this);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.start_exam_dialog);
//
//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        text.setText(msg);
//
//        ImageView dialogButton = dialog.findViewById(R.id.img_close);
//        AppCompatButton btnBuy = dialog.findViewById(R.id.btn_buy);
//
//        btnBuy.setOnClickListener(v -> {
//            dialog.dismiss();
//            mStartExam.onSureWantExam();
//        });
//
//        dialogButton.setOnClickListener(v -> {
//            dialog.dismiss();
//            mStartExam.onClose();
//        });
//
//        dialog.show();
//    }

    public void handleApiError(ANError error) {

        if (error == null || error.getErrorBody() == null) {
            //getMvpView().onError(R.string.api_default_error);
            Toast.makeText(this, "" + R.string.api_default_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            //getMvpView().onError(R.string.connection_error);
            Toast.makeText(this, "" + R.string.connection_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            //getMvpView().onError(R.string.api_retry_error);
            Toast.makeText(this, "" + R.string.api_retry_error, Toast.LENGTH_SHORT).show();
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();


        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                //getMvpView().onError(R.string.api_default_error);
                Toast.makeText(this, "" + R.string.api_default_error, Toast.LENGTH_SHORT).show();
                return;
            }

            switch (error.getErrorCode()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    openActivityOnTokenExpire();
                    break;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                case HttpsURLConnection.HTTP_NOT_FOUND:
                default:
                    Toast.makeText(this, "" + apiError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            // Log.e(TAG, "handleApiError", e);
            //getMvpView().onError(R.string.api_default_error);
        }

    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }


    public void openDrawer() {

    }

    public void closeDrawer() {

    }

    public interface SetLanguage{
        void languageEN();
        void languageAR();
    }

    public void signOut() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.sign_out_prompt))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    dataManager.setUserAsLoggedOut();
                    dataManager.setUnderEighteen(false);
                    openActivityOnTokenExpire();
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void languageDialog(SetLanguage mSetLanguage) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.change_language))
                .setPositiveButton(R.string.english, (dialog, which) -> {
                    if (mSetLanguage != null){
                        dataManager.setPrefLanguage("en");
                        mSetLanguage.languageEN();
                    }
                })
                .setNegativeButton(R.string.arabic, (dialog, which) -> {
                    if (mSetLanguage != null){
                        dataManager.setPrefLanguage("ar");
                        mSetLanguage.languageEN();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void setLocale(String lng){
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        dataManager.setPrefLanguage(lng);
        //Log.e("LANGUAGEDATA", "setLocale: "+ dataManager.getLanguage());
    }


    public void exitApp() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.exit_app_prompt))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

