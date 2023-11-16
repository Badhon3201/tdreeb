package com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ChatDetailsAdapter;
import com.ryx.tdreeb.adapters.ChatListAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatReceiverModel;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatHistoryResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.SendChatModel;
import com.ryx.tdreeb.databinding.FragmentChatDetailsBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.ChatCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;


public class ChatDetailsFragment extends BaseFragment<FragmentChatDetailsBinding, ChatDetailsViewModel> implements ChatDetailsNavigation, ChatCallBack {

    FragmentChatDetailsBinding mFragmentChatDetailsBinding;

    private ChatReceiverModel mChatReceiverModel;

    @Inject
    ChatListAdapter mChatListAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    LinearLayoutManager mLinearLayoutManagerTwo;
    @Inject
    DataManager dataManager;
    @Inject
    ChatDetailsAdapter mChatDetailsAdapter;

    String userType = "Trainer";

    public ChatDetailsFragment() {
        // Required empty public constructor
    }

    public static ChatDetailsFragment newInstance() {
        ChatDetailsFragment fragment = new ChatDetailsFragment();
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
        return R.layout.fragment_chat_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentChatDetailsBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        if (getArguments() != null) {
            ChatDetailsFragmentArgs args = ChatDetailsFragmentArgs.fromBundle(getArguments());
            mChatReceiverModel = args.getChatDetails();
            getBaseActivity().showLoading();
            mViewModel.getChatDetails(userType, dataManager.getCurrentUserId(), mChatReceiverModel.getUserType(), mChatReceiverModel.getId());
        }
        setup();
    }

    private void setup() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentChatDetailsBinding.rvChat.setLayoutManager(mLinearLayoutManager);
        mFragmentChatDetailsBinding.rvChat.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentChatDetailsBinding.rvChat.setItemAnimator(new DefaultItemAnimator());
        mFragmentChatDetailsBinding.rvChat.setAdapter(mChatListAdapter);
        mChatListAdapter.setListener(this);

        mLinearLayoutManagerTwo.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentChatDetailsBinding.rvChatDetails.setLayoutManager(mLinearLayoutManagerTwo);
        mFragmentChatDetailsBinding.rvChatDetails.addItemDecoration(new EqualSpacingItemDecoration(15));
        mFragmentChatDetailsBinding.rvChatDetails.setItemAnimator(new DefaultItemAnimator());
        mFragmentChatDetailsBinding.rvChatDetails.setAdapter(mChatDetailsAdapter);
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void sendMez() {
        if (!mFragmentChatDetailsBinding.edTypeMez.getText().toString().isEmpty()) {
            SendChatModel sendChatModel = new SendChatModel();
            sendChatModel.setSenderType(userType);
            sendChatModel.setSenderId(dataManager.getCurrentUserId());
            sendChatModel.setReceiverId(mChatReceiverModel.getId());
            sendChatModel.setReceiverType(mChatReceiverModel.getUserType());
            sendChatModel.setMessage(mFragmentChatDetailsBinding.edTypeMez.getText().toString());
            mViewModel.sendMessage(sendChatModel);
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
    public void onSuccessChat(ChatResponse mChatResponse) {
        getBaseActivity().hideLoading();
        if (mChatResponse.getCode() == 200) {
            mChatListAdapter.addItems(mChatResponse.getData().getHistories());
        } else {
            Toast.makeText(getContext(), "" + mChatResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessChatDetails(ChatHistoryResponse mChatHistoryResponse) {
        if (mChatHistoryResponse.getCode() == 200) {
            mFragmentChatDetailsBinding.edTypeMez.setText("");
            mChatDetailsAdapter.addItems(mChatHistoryResponse.getData().getMessages());
            int newMsgPosition = mChatHistoryResponse.getData().getMessages().size() - 1;
            mFragmentChatDetailsBinding.rvChatDetails.scrollToPosition(newMsgPosition);
        } else {
            Toast.makeText(getContext(), "" + mChatHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickChat(ChatReceiverModel mChatReceiverModel) {
        this.mChatReceiverModel = mChatReceiverModel;
        mChatDetailsAdapter.addItems(new ArrayList<>());
        getBaseActivity().showLoading();
        mViewModel.getChatDetails(userType, dataManager.getCurrentUserId(), mChatReceiverModel.getUserType(), mChatReceiverModel.getId());
    }
}