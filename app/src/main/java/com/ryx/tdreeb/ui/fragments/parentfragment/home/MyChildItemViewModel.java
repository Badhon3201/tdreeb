package com.ryx.tdreeb.ui.fragments.parentfragment.home;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatReceiverModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.ChatCallBack;

public class MyChildItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final ChildenModel childenModel;

    public final ChildrenCallBack mChildrenCallBack;

    public final ChatCallBack mChatCallBack;

    public final ChatReceiverModel mChatReceiverModel;

    public final ObservableField<Boolean> isTitleColor;


    public MyChildItemViewModel(ChildenModel childenModel, ChildrenCallBack mChildrenCallBack) {
        this.mChildrenCallBack = mChildrenCallBack;
        this.isTitleColor = new ObservableField<>(true);
        this.mChatReceiverModel = null;
        this.mChatCallBack = null;
        this.childenModel = childenModel;
        this.image = new ObservableField<>(childenModel.getImage());
        this.name = new ObservableField<>(childenModel.getFirstName() + " " + childenModel.getLastName());
    }

    public MyChildItemViewModel(ChatReceiverModel mChatReceiverModel, ChatCallBack mChatCallBack) {
        this.mChildrenCallBack = null;
        this.mChatCallBack = mChatCallBack;
        this.childenModel = null;
        this.isTitleColor = new ObservableField<>(false);
        this.mChatReceiverModel = mChatReceiverModel;
        this.image = new ObservableField<>(mChatReceiverModel.getImage());
        this.name = new ObservableField<>(mChatReceiverModel.getFirstName());
    }

    public void onClickProfile() {
        if (mChildrenCallBack != null) {
            mChildrenCallBack.onClickItemView(childenModel);
        } else if (mChatCallBack != null) {
            mChatCallBack.onClickChat(mChatReceiverModel);
        }
    }
}