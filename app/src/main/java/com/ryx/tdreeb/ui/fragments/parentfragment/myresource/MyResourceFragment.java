package com.ryx.tdreeb.ui.fragments.parentfragment.myresource;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyResourceAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.FragmentMyResourceBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.io.File;

import javax.inject.Inject;

public class MyResourceFragment extends BaseFragment<FragmentMyResourceBinding, MyResourceViewModel> implements MyResourceNavigator {

    public MyResourceFragment() {
        // Required empty public constructor
    }

    FragmentMyResourceBinding mFragmentMyResourceBinding;
    private NavController navController;
    @Inject
    MyResourceAdapter mMyResourceAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    public static MyResourceFragment newInstance() {
        MyResourceFragment fragment = new MyResourceFragment();
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
        return R.layout.fragment_my_resource;
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
        mFragmentMyResourceBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentMyResourceBinding.getRoot());

        tvToolBarTitle = mFragmentMyResourceBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentMyResourceBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentMyResourceBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.my_resources));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        if(dataManager.getUnderEighteen()){
            mFragmentMyResourceBinding.tvAddLiveTitle.setVisibility(View.GONE);
        }

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentMyResourceBinding.rvResource.setLayoutManager(mLinearLayoutManager);
        mFragmentMyResourceBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentMyResourceBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mFragmentMyResourceBinding.rvResource.setAdapter(mMyResourceAdapter);
        mMyResourceAdapter.setListener(new AddResourceCallBack() {
            @Override
            public void onClickFileRemoveItem(int pos, File file) {

            }

            @Override
            public void onClickEdit(ResourceModel mResourceModel) {

            }

            @Override
            public void onClickDelete(ResourceModel mResourceModel) {

            }
        });
        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "Resource");
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openFindResource() {
        navController.navigate(R.id.action_myResourceFragment_to_findResourceFragment);
    }

    @Override
    public void handleError(Throwable throwable) {
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mMyResourceAdapter.addItemsTwo(mSessionResponse.getData().getSessions());
        }
    }
}