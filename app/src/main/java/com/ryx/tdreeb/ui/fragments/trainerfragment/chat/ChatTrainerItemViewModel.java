package com.ryx.tdreeb.ui.fragments.trainerfragment.chat;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.chatmodel.ChatHistoryModel;
import com.ryx.tdreeb.utils.CommonUtils;

public class ChatTrainerItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final ObservableField<String> phone;

    public final ObservableField<String> time;

    public final ChatHistoryModel mChatHistoryModel;

    public final ChatCallBack mListener;

    public ChatTrainerItemViewModel(ChatHistoryModel mChatHistoryModel, ChatCallBack mListener) {
        this.mListener = mListener;
        this.mChatHistoryModel = mChatHistoryModel;
        this.image = new ObservableField<>(mChatHistoryModel.getReceiver().getImage());
        this.name = new ObservableField<>(mChatHistoryModel.getReceiver().getName());
        this.phone = new ObservableField<>(mChatHistoryModel.getReceiver().getPhoneNo());
        this.time = new ObservableField<>(CommonUtils.getDateCurrentTimeZone(mChatHistoryModel.getLastModified()));
    }

    public void onClickItem() {
        if (mListener != null) {
            mListener.onClickChat(mChatHistoryModel.getReceiver());
        }
    }
}