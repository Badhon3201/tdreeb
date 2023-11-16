package com.ryx.tdreeb.ui.fragments.trainerfragment.chat;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class TrainerChatViewModel extends BaseViewModel<TrainerChatNavigator> {

    @Inject
    public TrainerChatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
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


}
