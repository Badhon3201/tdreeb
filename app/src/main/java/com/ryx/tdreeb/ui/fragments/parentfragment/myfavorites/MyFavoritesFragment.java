package com.ryx.tdreeb.ui.fragments.parentfragment.myfavorites;

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
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyResourceAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteSubmitResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.databinding.FragmentMyFavoritesBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class MyFavoritesFragment extends BaseFragment<FragmentMyFavoritesBinding, MyFavoritesViewModel> implements MyFavoritesNavigator {

    FragmentMyFavoritesBinding mFragmentMyFavoritesBinding;
    @Inject
    MyResourceAdapter mMyResourceAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    private ChooseKidsFragment dialog;
    private NavController navController;

    private List<ChildenModel> listChildrenModel;
    private ChildenModel childenModel;
    private ResourceModel mResourceModel;

    public MyFavoritesFragment() {
        // Required empty public constructor
    }

    public static MyFavoritesFragment newInstance(String param1, String param2) {
        MyFavoritesFragment fragment = new MyFavoritesFragment();
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
        return R.layout.fragment_my_favorites;
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
        mFragmentMyFavoritesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentMyFavoritesBinding.getRoot());

        tvToolBarTitle = mFragmentMyFavoritesBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentMyFavoritesBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentMyFavoritesBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.my_resources));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentMyFavoritesBinding.rvResource.setLayoutManager(mLinearLayoutManager);
        mFragmentMyFavoritesBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentMyFavoritesBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mFragmentMyFavoritesBinding.rvResource.setAdapter(mMyResourceAdapter);
        mMyResourceAdapter.setListener(new AddResourceCallBack() {
            @Override
            public void onClickFileRemoveItem(int pos, File file) {

            }

            @Override
            public void onClickEdit(ResourceModel mResourceModel) {
                MyFavoritesFragment.this.mResourceModel = mResourceModel;
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
//                    getBaseActivity().showLoading();
//                    mViewModel.addFavorite(map);
                } else {
                    getBaseActivity().showLoading();
                    mViewModel.removeFavorite(map);
                }
            }
        });
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            getBaseActivity().showLoading();
            mViewModel.getAllFavorite(dataManager.getCurrentUserId(), "Parent");
        } else {
            getBaseActivity().showLoading();
            mViewModel.getAllFavorite(dataManager.getCurrentUserId(), "Student");
        }

        dialog = ChooseKidsFragment.newInstance();
        dialog.setCallBack((childenModel) -> {
            MyFavoritesFragment.this.childenModel = childenModel;
            openPaymentSummary();
        });
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
    public void onSuccessFavorite(FavoriteResponse mFavoriteResponse) {
        getBaseActivity().hideLoading();
        if (mFavoriteResponse.getIsSuccess()) {
            mMyResourceAdapter.addItemsThree(mFavoriteResponse.getData().getFavourite());
        }
    }

    @Override
    public void onSuccessAddFavorite(FavoriteSubmitResponse mFavoriteSubmitResponse) {
        getBaseActivity().hideLoading();
        if (mFavoriteSubmitResponse.getData().getFavourite() != null) {
            Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
        } else {
            if (mFavoriteSubmitResponse.getData().isResult()) {
                if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                    getBaseActivity().showLoading();
                    mViewModel.getAllFavorite(dataManager.getCurrentUserId(), "Parent");
                } else {
                    getBaseActivity().showLoading();
                    mViewModel.getAllFavorite(dataManager.getCurrentUserId(), "Student");
                }
                Toast.makeText(getContext(), "Remove Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Remove Failed", Toast.LENGTH_SHORT).show();
            }
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

    private void openChildren() {
        if (listChildrenModel != null) {
            dialog.setData(listChildrenModel);
            dialog.show(getBaseActivity().getSupportFragmentManager());
        } else {
            getBaseActivity().showLoading();
            mViewModel.getChildren(dataManager.getCurrentUserId());
        }
    }

    private void openPaymentSummary() {
        MyFavoritesFragmentDirections.ActionMyFavoritesFragmentToPaymentSummaryFragment action = MyFavoritesFragmentDirections.actionMyFavoritesFragmentToPaymentSummaryFragment();
        if (childenModel != null && mResourceModel != null) {
            action.setChildenData(childenModel);
            action.setResourceData(mResourceModel);
        } else {
            action.setResourceData(mResourceModel);
        }
        navController.navigate(action);
    }
}