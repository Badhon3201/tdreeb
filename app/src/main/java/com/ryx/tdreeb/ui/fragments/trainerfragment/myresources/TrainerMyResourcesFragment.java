package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.TrainerResourceAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.databinding.TrainerMyResourcesFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.io.File;

import javax.inject.Inject;

public class TrainerMyResourcesFragment extends BaseFragment<TrainerMyResourcesFragmentBinding, TrainerMyResourcesViewModel> implements TrainerMyResourcesNavigator {

    TrainerMyResourcesFragmentBinding mTrainerMyResourcesFragmentBinding;
    private NavController navController;
    private ImageView profileImage, drawerIcon;

    @Inject
    TrainerResourceAdapter mTrainerResourceAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public TrainerMyResourcesFragment() {
    }

    public static TrainerMyResourcesFragment newInstance() {
        TrainerMyResourcesFragment fragment = new TrainerMyResourcesFragment();
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
        return R.layout.trainer_my_resources_fragment;
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
        mTrainerMyResourcesFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerMyResourcesFragmentBinding.getRoot());

        profileImage = mTrainerMyResourcesFragmentBinding.getRoot().findViewById(R.id.profile_image);
        drawerIcon = mTrainerMyResourcesFragmentBinding.getRoot().findViewById(R.id.drawer_icon);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImage);
        drawerIcon.setOnClickListener(v -> {
            getBaseActivity().openDrawer();
        });

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerMyResourcesFragmentBinding.rvResource.setLayoutManager(mLinearLayoutManager);
        mTrainerMyResourcesFragmentBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(2));
        mTrainerMyResourcesFragmentBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mTrainerMyResourcesFragmentBinding.rvResource.setAdapter(mTrainerResourceAdapter);
        mTrainerResourceAdapter.setListener(new AddResourceCallBack() {
            @Override
            public void onClickFileRemoveItem(int pos, File file) {

            }

            @Override
            public void onClickEdit(ResourceModel mResourceModel) {
                TrainerMyResourcesFragmentDirections.
                        ActionTrainerMyResourcesFragmentToAddResourceFragment action =
                        TrainerMyResourcesFragmentDirections.actionTrainerMyResourcesFragmentToAddResourceFragment();
                action.setMyResources(mResourceModel);
                navController.navigate(action);
            }

            @Override
            public void onClickDelete(ResourceModel mResourceModel) {
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.delete_data))
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            getBaseActivity().showLoading();
                            mViewModel.removeResourceDataById(mResourceModel.getTrainerResourcesId());
                        })
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        getBaseActivity().showLoading();
        mViewModel.getResourceData(dataManager.getCurrentUserId());
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openAddResource() {
        navController.navigate(R.id.action_trainerMyResourcesFragment_to_addResourceFragment);
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
                mTrainerResourceAdapter.addItems(mResourceResponse.getData().getResult());
            } else {
                Toast.makeText(getContext(), "" + mResourceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                getBaseActivity().showLoading();
                mViewModel.getResourceData(dataManager.getCurrentUserId());
            }
        } else {
            getBaseActivity().hideLoading();
            Toast.makeText(getContext(), "" + mResourceResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
