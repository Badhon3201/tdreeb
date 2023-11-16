package com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatMassageModel;

public class ChatDetailsItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> message;

    public final ObservableField<Boolean> isImgVisible;

    public final ChatMassageModel mChatMassageModel;

    public ChatDetailsItemViewModel(ChatMassageModel mChatMassageModel, String img,boolean isImgVisible) {
        this.mChatMassageModel = mChatMassageModel;
        this.image = new ObservableField<>(img);
        this.isImgVisible = new ObservableField<>(isImgVisible);
        this.message = new ObservableField<>(mChatMassageModel.getMessage());
    }
}