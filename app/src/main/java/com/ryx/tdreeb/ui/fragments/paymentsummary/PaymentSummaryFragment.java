package com.ryx.tdreeb.ui.fragments.paymentsummary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.databinding.FragmentPaymentSummaryBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.utils.BindingUtils;

import javax.inject.Inject;

public class PaymentSummaryFragment extends BaseFragment<FragmentPaymentSummaryBinding, PaymentSummaryViewModel> implements PaymentSummaryNavigator {

    FragmentPaymentSummaryBinding mFragmentPaymentSummaryBinding;
    private NavController navController;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    DataManager dataManager;

    public PaymentSummaryFragment() {
        // Required empty public constructor
    }

    private ChildenModel childenModel;
    private LiveCourseModel mLiveCourseModel;
    private ResourceModel mResourceModel;

    public static PaymentSummaryFragment newInstance() {
        PaymentSummaryFragment fragment = new PaymentSummaryFragment();
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
        return R.layout.fragment_payment_summary;
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
        mFragmentPaymentSummaryBinding = getViewDataBinding();
        if (getArguments() != null) {
            PaymentSummaryFragmentArgs args = PaymentSummaryFragmentArgs.fromBundle(getArguments());
            childenModel = args.getChildenData();
            mLiveCourseModel = args.getLiveCourseData();
            mResourceModel = args.getResourceData();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentPaymentSummaryBinding.getRoot());

        tvToolBarTitle = mFragmentPaymentSummaryBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentPaymentSummaryBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentPaymentSummaryBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.payment_summary));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        if (mLiveCourseModel != null) {
            mFragmentPaymentSummaryBinding.tvName.setText(mLiveCourseModel.getTrainerResponse().getName());
            mFragmentPaymentSummaryBinding.tvSources.setText(mLiveCourseModel.getCourseSubject());
            mFragmentPaymentSummaryBinding.tvCourseHour.setText("AED: " + mLiveCourseModel.getPrice());
            mFragmentPaymentSummaryBinding.tvAmount.setText("AED: " + mLiveCourseModel.getPrice());
            mFragmentPaymentSummaryBinding.tvOrderTotalAmount.setText("AED: " + mLiveCourseModel.getPrice());
            BindingUtils.setImageUrl(mFragmentPaymentSummaryBinding.courseImage, mLiveCourseModel.getTrainerResponse().getImage());
        } else if (mResourceModel != null) {
            mFragmentPaymentSummaryBinding.tvName.setText(mResourceModel.getTrainer().getName());

            if (mResourceModel.getSubject() != null) {
                mFragmentPaymentSummaryBinding.tvSources.setText(mResourceModel.getSubject().getSubjectName());
            } else {
                mFragmentPaymentSummaryBinding.tvSources.setText(mResourceModel.getResourceName());
            }
            mFragmentPaymentSummaryBinding.tvCourseHour.setText("AED: " + mResourceModel.getPrice());
            mFragmentPaymentSummaryBinding.tvAmount.setText("AED: " + mResourceModel.getPrice());
            mFragmentPaymentSummaryBinding.tvOrderTotalAmount.setText("AED: " + mResourceModel.getPrice());
            mFragmentPaymentSummaryBinding.tvCountry.setText(mResourceModel.getTrainer().getLocation());
            BindingUtils.setImageUrl(mFragmentPaymentSummaryBinding.courseImage, mResourceModel.getTrainer().getImage());
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openPayment() {
        PaymentSummaryFragmentDirections.ActionPaymentSummaryFragmentToPaymentMethodFragment action = PaymentSummaryFragmentDirections.actionPaymentSummaryFragmentToPaymentMethodFragment("");
        action.setChildrenData(childenModel);
        if (mLiveCourseModel != null) {
            action.setLiveCourseData(mLiveCourseModel);
            action.setCourseType("online");
        } else if (mResourceModel != null) {
            action.setCourseType("Resource");
            action.setResourceData(mResourceModel);
        }
        navController.navigate(action);
    }
}