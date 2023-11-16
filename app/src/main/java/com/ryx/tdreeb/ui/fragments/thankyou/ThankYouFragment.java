package com.ryx.tdreeb.ui.fragments.thankyou;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.databinding.FragmentThankYouBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;


public class ThankYouFragment extends BaseFragment<FragmentThankYouBinding, ThankYouViewModel> implements ThankYouNavigator {

    FragmentThankYouBinding mFragmentThankYouBinding;
    private NavController navController;
    private String invoiceNumber;

    public ThankYouFragment() {
        // Required empty public constructor
    }

    public static ThankYouFragment newInstance() {
        ThankYouFragment fragment = new ThankYouFragment();
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
        return R.layout.fragment_thank_you;
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
        mFragmentThankYouBinding = getViewDataBinding();
        if (getArguments() != null) {
            ThankYouFragmentArgs args = ThankYouFragmentArgs.fromBundle(getArguments());
            invoiceNumber = args.getInvoiceId();
            mFragmentThankYouBinding.tvInvoice.setText(getString(R.string.invoice_number, "#" + invoiceNumber));
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentThankYouBinding.getRoot());
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openHome() {
        navController.navigate(R.id.action_thankYouFragment_to_homeParentFragment);
    }
}