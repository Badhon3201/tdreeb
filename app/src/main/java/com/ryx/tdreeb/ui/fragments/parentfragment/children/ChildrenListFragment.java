package com.ryx.tdreeb.ui.fragments.parentfragment.children;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ChildrenListAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.FragmentChildrenListBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

public class ChildrenListFragment extends BaseFragment<FragmentChildrenListBinding, ChildrenListViewModel> implements ChildrenListNavigator {

    FragmentChildrenListBinding mFragmentChildrenListBinding;
    private NavController navController;
    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    List<ChildenModel> list;
    ChildenModel mChildenModel;
    @Inject
    DataManager dataManager;
    @Inject
    ChildrenListAdapter mChildrenListAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    public ChildrenListFragment() {
        // Required empty public constructor
    }

    public static ChildrenListFragment newInstance() {
        ChildrenListFragment fragment = new ChildrenListFragment();
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
        return R.layout.fragment_children_list;
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
        mFragmentChildrenListBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        tvToolBarTitle = mFragmentChildrenListBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentChildrenListBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentChildrenListBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.my_children));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentChildrenListBinding.getRoot());
        //Adapter Setup
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentChildrenListBinding.rvList.setLayoutManager(mLinearLayoutManager);
        mFragmentChildrenListBinding.rvList.addItemDecoration(new EqualSpacingItemDecoration(30));
        mFragmentChildrenListBinding.rvList.setItemAnimator(new DefaultItemAnimator());
        mFragmentChildrenListBinding.rvList.setAdapter(mChildrenListAdapter);
        mChildrenListAdapter.setListener(new ChildrenCallBack() {
            @Override
            public void onClickItem() {

            }

            @Override
            public void onClickItemView(ChildenModel childenModel) {
                ChildrenListFragment.this.mChildenModel = childenModel;
                openProfile();
            }
        });
        mChildenModel = null;
        getBaseActivity().showLoading();
        mViewModel.getChildren(dataManager.getCurrentUserId());
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openProfile() {
        if (mChildenModel == null){
            ChildrenListFragmentDirections.ActionChildrenListFragmentToAddChildrenFragment action = ChildrenListFragmentDirections.actionChildrenListFragmentToAddChildrenFragment();
            navController.navigate(action);
        }else{
            ChildrenListFragmentDirections.ActionChildrenListFragmentToAddChildrenFragment action = ChildrenListFragmentDirections.actionChildrenListFragmentToAddChildrenFragment();
            action.setChildrenData(mChildenModel);
            navController.navigate(action);
        }

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
    public void onSuccessAddChildResponse(AddChildResponse mAddChildResponse) {
        getBaseActivity().hideLoading();
        if (mAddChildResponse.getCode() == 200){
            list = mAddChildResponse.getData().getChilds();
            mChildrenListAdapter.addItems(list);
        }
    }
}