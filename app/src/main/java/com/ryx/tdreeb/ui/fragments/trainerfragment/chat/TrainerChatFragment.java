package com.ryx.tdreeb.ui.fragments.trainerfragment.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.TrainerChatAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatReceiverModel;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatHistoryResponse;
import com.ryx.tdreeb.databinding.TrainerChatBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrainerChatFragment extends BaseFragment<TrainerChatBinding, TrainerChatViewModel> implements TrainerChatNavigator, ChatCallBack {

    TrainerChatBinding mTrainerChatBinding;

    ImageView backImg,profileImg;

    @Inject
    TrainerChatAdapter mTrainerChatAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;


    String userType = "Trainer";

    public TrainerChatFragment() {
    }

    public static TrainerChatFragment newInstance() {
        TrainerChatFragment fragment = new TrainerChatFragment();
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
        return R.layout.trainer_chat;
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
        mTrainerChatBinding = getViewDataBinding();
        navController = Navigation.findNavController(view);
        setUp();
        getBaseActivity().showLoading();
        mViewModel.getChatList(userType, dataManager.getCurrentUserId());
    }

    private void setUp() {
        navController = Navigation.findNavController(mTrainerChatBinding.getRoot());

        backImg = mTrainerChatBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerChatBinding.getRoot().findViewById(R.id.profile_image);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerChatBinding.rvChat.setLayoutManager(mLinearLayoutManager);
        mTrainerChatBinding.rvChat.addItemDecoration(new EqualSpacingItemDecoration(40));
        mTrainerChatBinding.rvChat.setItemAnimator(new DefaultItemAnimator());
        mTrainerChatBinding.rvChat.setAdapter(mTrainerChatAdapter);
        mTrainerChatAdapter.setListener(this);
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
    public void onSuccessChat(ChatResponse mChatResponse) {
        getBaseActivity().hideLoading();
        if (mChatResponse.getCode() == 200) {
            mTrainerChatAdapter.addItems(mChatResponse.getData().getHistories());
        } else {
            Toast.makeText(getContext(), "" + mChatResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickChat(ChatReceiverModel mChatReceiverModel) {
        TrainerChatFragmentDirections.ActionNavigationChatToChatDetailsFragment action = TrainerChatFragmentDirections.actionNavigationChatToChatDetailsFragment();
        action.setChatDetails(mChatReceiverModel);
        navController.navigate(action);
    }
}
