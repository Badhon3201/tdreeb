package com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails;

import com.ryx.tdreeb.data.model.api.chatmodel.ChatResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatHistoryResponse;

public interface ChatDetailsNavigation {

    void sendMez();

    void handleError(Throwable throwable);

    void onSuccessChat(ChatResponse mChatResponse);

    void onSuccessChatDetails(ChatHistoryResponse mChatHistoryResponse);
}
