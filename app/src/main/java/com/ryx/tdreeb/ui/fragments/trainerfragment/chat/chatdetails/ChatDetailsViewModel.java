package com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.SendChatModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ChatDetailsViewModel extends BaseViewModel<ChatDetailsNavigation> {
    @Inject
    public ChatDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getChatList(String userType, int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetChatList(userType, id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessChat(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getChatDetails(String userType, int id, String receiverType, int receiverId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetChatHistory(userType, id, receiverType, receiverId,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getChatList(userType, id);
                    getNavigator().onSuccessChatDetails(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void sendMessage(SendChatModel sendChatModel) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doSendChat(sendChatModel)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessChatDetails(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onClickSend() {
        getNavigator().sendMez();
    }

}
