package com.ryx.tdreeb.ui.activites.map.addresssearch;


import com.ryx.tdreeb.data.model.api.mapmodel.MapPlaceResponse;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionResponse;

public interface MapSuggestionNavigator {
    void submitSuggestionData(String input, String components, String key);

    void successSuggestionData(MapSuggestionResponse response);

    void submitPlaceData(String place_id, String key);

    void successPlaceData(MapPlaceResponse response);

    void handleError(Throwable throwable);
}
