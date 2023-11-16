package com.ryx.tdreeb.ui.activites.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.library.baseAdapters.BR;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.model.LatLng;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.databinding.ActivityMapBinding;
import com.ryx.tdreeb.di.component.ActivityComponent;
import com.ryx.tdreeb.ui.activites.map.addresssearch.MapSuggestionActivity;
import com.ryx.tdreeb.ui.base.BaseActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MapActivity extends BaseActivity<ActivityMapBinding, MapViewModel> implements MapNavigator, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    ActivityMapBinding mActivityMapBinding;
    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private long SMALLEST_DISPLACEMENT = 30;
    private LatLng latLng;
    private LatLng myAddressLatLng;
    private String address;
    public static int REQUEST_CHECK_SETTINGS = 199;
    public boolean isChange = false;

    @Inject
    DataManager dataManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        mActivityMapBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        backImg = findViewById(R.id.drawer_icon);
        tvToolBarTitle = findViewById(R.id.tv_title);
        profileImg = findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.location));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);
        backImg.setOnClickListener(v -> {
            onBackPressed();
        });


        mActivityMapBinding.maplayout.setCornerRadius(60);
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && !hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermissionsSafely(permission, 500);
        } else {
            loadMap();
        }
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void loadMap() {
        showLoading();
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setSmallestDisplacement(SMALLEST_DISPLACEMENT);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void getLocation() {
        startLocationUpdates();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation == null) {
            startLocationUpdates();
        } else {
            Log.e(this.toString(), "onConnected: " + "Location not detected");
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        //setCamra();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (latLng != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
            googleMap.setOnCameraIdleListener(() -> {
                LatLng center = googleMap.getCameraPosition().target;
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(center.latitude);
                temp.setLongitude(center.longitude);
                getAddress(center.latitude, center.longitude);
            });
        }
    }

    @Override
    public void openMap() {
        startActivityForResult(MapSuggestionActivity.newIntent(this), 500);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);
    }

    @Override
    public void onClickLocation() {
        setCamra();
    }

    @Override
    public void closeMap() {
        Intent intent = new Intent();
        intent.putExtra("address", address);
        intent.putExtra("myAddressLatLng", myAddressLatLng);
        setResult(1000, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("RequestCode", "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case 500:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapActivity.this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                } else {
                    buildGoogleApiClient();
                    loadMap();
                    isGpsEnable();
                }
                break;
        }
    }

    private void setCamra() {
        if (latLng != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        }
    }

    //  TODO: Google Map
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void isGpsEnable() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    // All location settings are satisfied. The client can initialize
                    // location requests here.
                    // ...
                }
            });

            task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(MapActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {

                        }
                    }
                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        setMapLocation();
    }

    public void setMapLocation() {
        if (mActivityMapBinding.mapContainer != null && !isChange) {
            mActivityMapBinding.mapContainer.onCreate(null);
            mActivityMapBinding.mapContainer.onResume();
            mActivityMapBinding.mapContainer.getMapAsync(this);
            isChange = true;
            hideLoading();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                mActivityMapBinding.tvLocation.setText(address);
                myAddressLatLng = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 500) {
            if (data != null) {
                address = data.getStringExtra("address");
                myAddressLatLng = data.getParcelableExtra("myAddressLatLng");
                latLng = myAddressLatLng;
                mActivityMapBinding.tvLocation.setText(address);
                setCamra();
            }
        }
    }
}