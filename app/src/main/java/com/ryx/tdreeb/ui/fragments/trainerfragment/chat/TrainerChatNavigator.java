package com.ryx.tdreeb.ui.fragments.trainerfragment.chat;

import com.ryx.tdreeb.data.model.api.chatmodel.ChatResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatHistoryResponse;

public interface TrainerChatNavigator {

    void handleError(Throwable throwable);

    void onSuccessChat(ChatResponse mChatResponse);
}
