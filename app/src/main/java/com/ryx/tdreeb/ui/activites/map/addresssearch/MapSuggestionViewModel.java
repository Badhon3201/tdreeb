package com.ryx.tdreeb.ui.activites.map.addresssearch;


import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.mapmodel.MapPlaceModel;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionModel;
import com.ryx.tdreeb.ui.base.BaseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MapSuggestionViewModel extends BaseViewModel<MapSuggestionNavigator> {
    @Inject
    public MapSuggestionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onMapSuggestionData(String input, String components, String key) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGoogleSuggestionApiCall(new MapSuggestionModel(input, components, key))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().successSuggestionData(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onMapPlacenData(String place, String key) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGooglePlaceApiCall(new MapPlaceModel(place, key))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().successPlaceData(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
