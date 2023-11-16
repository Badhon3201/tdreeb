package com.ryx.tdreeb.ui.fragments.paymentmethod;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.BookingResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.submitmodels.LiveCourseParentModel;
import com.ryx.tdreeb.databinding.FragmentPaymentMethodBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.interfaces.SimpleDialogClick;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;


public class PaymentMethodFragment extends BaseFragment<FragmentPaymentMethodBinding, PaymentMethodViewModel> implements PaymentMethodNavigator {

    FragmentPaymentMethodBinding mFragmentPaymentMethodBinding;
    private NavController navController;

    private ChildenModel childenModel;
    private LiveCourseModel mLiveCourseModel;
    private ResourceModel mResourceModel;
    private String courseType;
    private String paymentMethod, invoiceNumber;
    private LiveCourseParentModel mLiveCourseParentModel;

    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    DataManager mDataManager;


    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    public static PaymentMethodFragment newInstance() {
        PaymentMethodFragment fragment = new PaymentMethodFragment();
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
        return R.layout.fragment_payment_method;
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
        mFragmentPaymentMethodBinding = getViewDataBinding();
        if (getArguments() != null) {
            PaymentMethodFragmentArgs args = PaymentMethodFragmentArgs.fromBundle(getArguments());
            mLiveCourseModel = args.getLiveCourseData();
            childenModel = args.getChildrenData();
            mResourceModel = args.getResourceData();
            courseType = args.getCourseType();
            mLiveCourseParentModel = args.getCourseDataPass();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentPaymentMethodBinding.getRoot());

        tvToolBarTitle = mFragmentPaymentMethodBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentPaymentMethodBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentPaymentMethodBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.payment_method));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mFragmentPaymentMethodBinding.clAddress.setVisibility(View.GONE);
        mFragmentPaymentMethodBinding.tvAddLiveTitle.setVisibility(View.GONE);
        mFragmentPaymentMethodBinding.clCod.setVisibility(View.GONE);

        if (courseType.toLowerCase().equals("online") || courseType.toLowerCase().equals("resource")) {
            mFragmentPaymentMethodBinding.imgOnlinePayment.setBackgroundResource(R.drawable.checked2);
            mFragmentPaymentMethodBinding.imgCod.setBackgroundResource(R.drawable.checked3);
            paymentMethod = "online";

        }
        //TODO:- Online Select
        mFragmentPaymentMethodBinding.clOnlinePayment.setOnClickListener(v -> {
            mFragmentPaymentMethodBinding.imgOnlinePayment.setBackgroundResource(R.drawable.checked2);
            mFragmentPaymentMethodBinding.imgCod.setBackgroundResource(R.drawable.checked3);
            paymentMethod = "online";
        });

        //TODO:- COD Select
        mFragmentPaymentMethodBinding.clCod.setOnClickListener(v -> {
            mFragmentPaymentMethodBinding.imgOnlinePayment.setBackgroundResource(R.drawable.checked3);
            mFragmentPaymentMethodBinding.imgCod.setBackgroundResource(R.drawable.checked2);
            paymentMethod = "COD";
        });
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openThankYou() {
        if (paymentMethod != null) {
            if(mLiveCourseParentModel == null){
                LiveCourseParentModel params = new LiveCourseParentModel();
                if (mLiveCourseModel != null) {
                    params.setDate(mLiveCourseModel.getLiveDate());
                    params.setTime(mLiveCourseModel.getLiveTime());
                    params.setRate(mLiveCourseModel.getPrice());
                    params.setSubTotal(mLiveCourseModel.getPrice());
                    params.setGrandTotal(mLiveCourseModel.getPrice());
                    params.setTrainerId(mLiveCourseModel.getTrainerId());
                    params.setTrainerCourseId(mLiveCourseModel.getLiveCourseId());
                    params.setCourseType(mLiveCourseModel.getCourseType());
                } else if (mResourceModel != null) {
                    params.setDate("2021-06-06T19:00:00");
                    params.setTime("04:04 PM");
                    params.setRate(mResourceModel.getPrice());
                    params.setSubTotal(mResourceModel.getPrice());
                    params.setGrandTotal(mResourceModel.getPrice());
                    params.setTrainerId(mResourceModel.getTrainerId());
                    params.setTrainerCourseId(mResourceModel.getTrainerResourcesId());
                    params.setCourseType("Resource");
                }

                if (mDataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                    params.setBookByType("parent");
                    params.setBookForType("student");
                    params.setBookById(mDataManager.getCurrentUserId());
                    params.setBookForId(childenModel.getStudentId());
                }else {
                    params.setBookByType("student");
                    params.setBookForType("student");
                    params.setBookById(mDataManager.getCurrentUserId());
                    params.setBookForId(mDataManager.getCurrentUserId());
                }

                params.setIsGroupSession(true);
                params.setPaymentType(paymentMethod);

                //Log.e("DATASUBMIT", "openThankYou: " + params.toString());
                getBaseActivity().showLoading();
                mViewModel.getChildren(params);
            }else {
                getBaseActivity().showLoading();
                mViewModel.getChildren(mLiveCourseParentModel);
            }

        } else {
            Toast.makeText(getContext(), "Select payment method", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSuccess() {
        if (invoiceNumber != null) {
            PaymentMethodFragmentDirections.ActionPaymentMethodFragmentToThankYouFragment action = PaymentMethodFragmentDirections.actionPaymentMethodFragmentToThankYouFragment();
            action.setInvoiceId(invoiceNumber);
            navController.navigate(action);
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        Log.e("ERRORRESOURCE", "handleError: "+ throwable.getMessage());
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessOnlineLiveCourse(BookingResponse mBookingResponse) {
        getBaseActivity().hideLoading();
        if (mBookingResponse.getIsSuccess()) {
            invoiceNumber = mBookingResponse.getData().getSession().getInvoiceNumber();
            openSuccess();
        } else {
            getBaseActivity().openDialog(mBookingResponse.getMessage(), () -> {
            });
        }
    }
}