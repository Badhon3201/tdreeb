package com.ryx.tdreeb.ui.fragments.parentfragment.home;

import android.util.Log;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class HomeParentViewModel extends BaseViewModel<HomeParentNavigator> {

    @Inject
    public HomeParentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getChildren(int id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetChild(id,getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessAddChildResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void getResource(int id, String courseType) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetUpComingSession(id, courseType,getDataManager().getUserType(),getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessResource(response);
                }, throwable -> {
                    setIsLoading(false);
                    Log.e("ERRORDATA", "getResource: "+throwable.getMessage());
                    getNavigator().handleError(throwable);
                }));
    }


    //TODO:- Click View
    public void onClickFindCourse() {
        if(getDataManager().getUnderEighteen()){
            getNavigator().openSession();
        }else{
            getNavigator().openFindCourse();
        }
    }

    public void onClickFindTrainer() {
        if(getDataManager().getUnderEighteen()){
            getNavigator().openResource();
        }else {
            getNavigator().openFindTrainer();
        }
    }

    public void onClickSession() {
        getNavigator().openSession();
    }

    public void onClickChat() {
        getNavigator().openChat();
    }

    public void onClickChildrenList() {
        getNavigator().openChildrenList();
    }

    public void onClickMyResource() {
        getNavigator().openResource();
    }

    public void onClickFavorites() {
        getNavigator().openFavorites();
    }

    public void onClickVideoCourse() {
        getNavigator().openVideoCourse();
    }

    public void onClickLiveCourse() {
        if(getDataManager().getUnderEighteen()){
            getNavigator().openChildrenList();
        }else {
            getNavigator().openLiveCourse();
        }

    }

    public void onClickProfile() {
        getNavigator().openProfile();
    }

    public void onClickMenu() {
        getNavigator().openMainDrawer();
    }
}
