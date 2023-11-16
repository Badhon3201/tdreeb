package com.ryx.tdreeb.ui.fragments.parentfragment.findresource;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyResourceAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteSubmitResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.Data;
import com.ryx.tdreeb.databinding.FragmentFindResourceBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer.BuyTrainerFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class FindResourceFragment extends BaseFragment<FragmentFindResourceBinding, FindResourceViewModel> implements FindResourceNavigator {

    FragmentFindResourceBinding mFragmentFindResourceBinding;
    @Inject
    MyResourceAdapter mMyResourceAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    private NavController navController;

    private ChooseKidsFragment dialog;

    private List<ChildenModel> listChildrenModel;
    private ChildenModel childenModel;
    private ResourceModel mResourceModel;

    public FindResourceFragment() {
        // Required empty public constructor
    }

    public static FindResourceFragment newInstance(String param1, String param2) {
        FindResourceFragment fragment = new FindResourceFragment();
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
        return R.layout.fragment_find_resource;
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
        mFragmentFindResourceBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentFindResourceBinding.getRoot());

        tvToolBarTitle = mFragmentFindResourceBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentFindResourceBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentFindResourceBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.find_resource));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFindResourceBinding.rvResource.setLayoutManager(mLinearLayoutManager);
        mFragmentFindResourceBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentFindResourceBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mFragmentFindResourceBinding.rvResource.setAdapter(mMyResourceAdapter);
        mMyResourceAdapter.setListener(new AddResourceCallBack() {
            @Override
            public void onClickFileRemoveItem(int pos, File file) {

            }

            @Override
            public void onClickEdit(ResourceModel mResourceModel) {
                FindResourceFragment.this.mResourceModel = mResourceModel;
                if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                    openChildren();
                } else {
                    openPaymentSummary();
                }
            }

            @Override
            public void onClickDelete(ResourceModel mResourceModel) {
                Map<String, String> map = new HashMap<>();
                map.put("favorite", "Resource");
                map.put("favoriteId", mResourceModel.getTrainerResourcesId() + "");
                if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                    map.put("favoriteBy", "Parent");
                } else {
                    map.put("favoriteBy", "Student");
                }
                map.put("favoriteById", dataManager.getCurrentUserId() + "");
                if (mResourceModel.isFavorite()) {
                    getBaseActivity().showLoading();
                    mViewModel.addFavorite(map);
                } else {
                    getBaseActivity().showLoading();
                    mViewModel.removeFavorite(map);
                }
            }
        });

        mFragmentFindResourceBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMyResourceAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog = ChooseKidsFragment.newInstance();
        dialog.setCallBack((childenModel) -> {
            FindResourceFragment.this.childenModel = childenModel;
            openPaymentSummary();
        });

        Map<String, String> map = new HashMap<>();
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            map.put("FavoriteBy", "Parent");
            map.put("FavoriteById", dataManager.getCurrentUserId() + "");
            getBaseActivity().showLoading();
            mViewModel.getAllResourceData(map);
        } else {
            map.put("FavoriteBy", "Student");
            map.put("FavoriteById", dataManager.getCurrentUserId() + "");
            getBaseActivity().showLoading();
            mViewModel.getAllResourceData(map);
        }

    }

    private void openChildren() {
        if (listChildrenModel != null) {
            dialog.setData(listChildrenModel);
            dialog.show(getBaseActivity().getSupportFragmentManager());
        } else {
            getBaseActivity().showLoading();
            mViewModel.getChildren(dataManager.getCurrentUserId());
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openPaymentSummary() {
        FindResourceFragmentDirections.ActionFindResourceFragmentToPaymentSummaryFragment action = FindResourceFragmentDirections.actionFindResourceFragmentToPaymentSummaryFragment();
        if (childenModel != null && mResourceModel != null) {
            action.setChildenData(childenModel);
            action.setResourceData(mResourceModel);
        } else {
            action.setResourceData(mResourceModel);
        }
        navController.navigate(action);
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
    public void onSuccessGetResource(ResourceResponse mResourceResponse) {
        if (mResourceResponse.getIsSuccess()) {
            if (mResourceResponse.getData().getResult() != null) {
                getBaseActivity().hideLoading();
                mMyResourceAdapter.addItems(mResourceResponse.getData().getResult());
            }
        } else {
            getBaseActivity().hideLoading();
            Toast.makeText(getContext(), "" + mResourceResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessAddChildResponse(AddChildResponse mAddChildResponse) {
        getBaseActivity().hideLoading();
        if (mAddChildResponse.getCode() == 200) {
            listChildrenModel = mAddChildResponse.getData().getChilds();
            openChildren();
        }
    }

    @Override
    public void onSuccessAddFavorite(FavoriteSubmitResponse mFavoriteSubmitResponse) {
        getBaseActivity().hideLoading();
        if (mFavoriteSubmitResponse.getData().getFavourite() != null) {
            Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
        } else {
            if (mFavoriteSubmitResponse.getData().isResult()) {
                Toast.makeText(getContext(), "Remove Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Remove Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}