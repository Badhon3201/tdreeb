package com.ryx.tdreeb.ui.activites.map.addresssearch;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.maps.model.LatLng;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MapSuggestionAdapter;
import com.ryx.tdreeb.data.model.api.mapmodel.MapPlaceResponse;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionResponse;
import com.ryx.tdreeb.databinding.ActivityMapSuggestionBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapSuggestionActivity extends BaseActivity<ActivityMapSuggestionBinding, MapSuggestionViewModel> implements MapSuggestionNavigator, MapCallBack, TextWatcher {

    ActivityMapSuggestionBinding mActivityMapSuggestionBinding;

    public static ProgressBar progressHorizontal;

    private String country;
    protected GoogleApiClient googleApiClient;
    private LatLng myAddressLatLng;
    private String address;

    public static Intent newIntent(Context context) {
        return new Intent(context, MapSuggestionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_suggestion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMapSuggestionBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUp();
    }

    private void setUp() {
        mActivityMapSuggestionBinding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
        progressHorizontal = mActivityMapSuggestionBinding.progressHorizontal;
        mActivityMapSuggestionBinding.edtSearch.addTextChangedListener(this);
        mActivityMapSuggestionBinding.recyclerViewMap.setLayoutManager(new LinearLayoutManager(this));
        mActivityMapSuggestionBinding.recyclerViewMap.addItemDecoration(new EqualSpacingItemDecoration(25));

    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!charSequence.toString().equals("") && googleApiClient.isConnected()) {
            submitSuggestionData(charSequence.toString(), country, getString(R.string.google_maps_key));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void submitSuggestionData(String input, String components, String key) {
        mViewModel.onMapSuggestionData(input, components, key);
    }

    @Override
    public void successSuggestionData(MapSuggestionResponse mapSuggestionResponse) {
        mActivityMapSuggestionBinding.recyclerViewMap.setAdapter(new MapSuggestionAdapter(MapSuggestionActivity.this, mapSuggestionResponse));
    }

    @Override
    public void submitPlaceData(String place_id, String key) {
        mViewModel.onMapPlacenData(place_id, key);
    }

    @Override
    public void successPlaceData(MapPlaceResponse response) {
        if (response.getStatus().equals("OK")) {
//            SharedPreferenceHelper.setData(MapSuggestionActivity.this, SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_LAT, response.getResult().getGeometry().getLocation().getLat().toString());
//            SharedPreferenceHelper.setData(MapSuggestionActivity.this, SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_LNG, response.getResult().getGeometry().getLocation().getLng().toString());
//            SharedPreferenceHelper.setData(MapSuggestionActivity.this,SharedPreferenceHelper.MAP_LOCATION_SELECTED_TABLE,SharedPreferenceHelper.MAP_LOCATION_SELECTED_STATUS,getString(R.string.ok));
            getAddress(Double.parseDouble(response.getResult().getGeometry().getLocation().getLat()), Double.parseDouble(response.getResult().getGeometry().getLocation().getLng()));
            //onBackPressed();
        }
    }


    @Override
    public void onMethodCallback(String description, String place_id) {
        //SharedPreferenceHelper.setData(MapSuggestionActivity.this, SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_ADDRESS, description);
        submitPlaceData(place_id, getString(R.string.google_maps_key));
    }


    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient != null) {
            if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else {
            buildGoogleApiClient();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                myAddressLatLng = new LatLng(latitude, longitude);
                if (address == null) {
                    Toast.makeText(this, "" + getString(R.string.wrong_address), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (myAddressLatLng == null) {
                    Toast.makeText(this, "" + getString(R.string.lat_lng_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.putExtra("myAddressLatLng", myAddressLatLng);
                setResult(500, intent);
                finish();
            }
//            SharedPreferenceHelper.setData(MapSuggestionActivity.this, SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_CITY, city);
//            SharedPreferenceHelper.setData(MapSuggestionActivity.this, SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_COUNTRY, country);

//            if (SharedPreferenceHelper.checkData(getContext(), SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_LAT) && SharedPreferenceHelper.checkData(getContext(), SharedPreferenceHelper.MAP_TEMPORARY_LOCATION_TABLE, SharedPreferenceHelper.MAP_LOCATION_LNG)) {
//                onBackPressed();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setprogressHorizontalLoading() {
        progressHorizontal.setVisibility(View.VISIBLE);
    }

    public static void setProgressHorizontalHide() {
        progressHorizontal.setVisibility(View.GONE);
    }

    @Override
    public void handleError(Throwable throwable) {
        hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            handleApiError(anError);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
    }
}