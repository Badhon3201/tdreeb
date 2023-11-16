package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse;

import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class FindCourseViewModel extends BaseViewModel<FindCourseNavigator> {
    @Inject
    public FindCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getSubject() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGetSubjects(getDataManager().getLanguage())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().onSuccessSubject(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

}
