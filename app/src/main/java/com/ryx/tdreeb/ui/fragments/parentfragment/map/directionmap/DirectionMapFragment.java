package com.ryx.tdreeb.ui.fragments.parentfragment.map.directionmap;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.error.ANError;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.customui.MapMarkerBounce;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.mapdirectionmodel.DirectionResponses;
import com.ryx.tdreeb.databinding.FragmentDirectionMapBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DirectionMapFragment extends BaseFragment<FragmentDirectionMapBinding, DirectionMapViewModel> implements DirectionMapNavigator, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    FragmentDirectionMapBinding mFragmentDirectionMapBinding;

    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap mMap;
    private NavController navController;

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private long SMALLEST_DISPLACEMENT = 30;
    private LatLng latLng, teacherLatLng;

    public static int REQUEST_CHECK_SETTINGS = 199;

    private SessionModel mSessionModel;

    LatLngBounds adelaideBounds;

    public DirectionMapFragment() {
        // Required empty public constructor
    }

    public static DirectionMapFragment newInstance() {
        DirectionMapFragment fragment = new DirectionMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_direction_map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildGoogleApiClient();
        mViewModel.setNavigator(this);
        mFragmentDirectionMapBinding = getViewDataBinding();
        if (getArguments() != null) {
            DirectionMapFragmentArgs args = DirectionMapFragmentArgs.fromBundle(getArguments());
            mSessionModel = args.getSessionData();
        }
        setUp();
    }

    private void setUp() {
        navController = Navigation.findNavController(mFragmentDirectionMapBinding.getRoot());
        mFragmentDirectionMapBinding.maplayout.setCornerRadius(60);

        if (mSessionModel != null) {
            teacherLatLng = new LatLng(Double.parseDouble(mSessionModel.getTrainer().getLocation().getLatitude()), Double.parseDouble(mSessionModel.getTrainer().getLocation().getLongitude()));
        }

        if (!getBaseActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getBaseActivity().requestPermissionsSafely(permission, 500);
        } else {
            loadMap();
        }
    }

    private void loadMap() {
        getBaseActivity().showLoading();
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setSmallestDisplacement(SMALLEST_DISPLACEMENT);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void getLocation() {
        startLocationUpdates();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation == null) {
            startLocationUpdates();
        } else {
            Log.e(this.toString(), "onConnected: " + "Location not detected");
        }
    }

    private void setCamra() {
        if (latLng != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        //setCamra();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (latLng != null && teacherLatLng != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .anchor(0.5f, 0.5f)
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_gps)));
            mMap.addMarker(new MarkerOptions()
                    .position(teacherLatLng)
                    .anchor(0.5f, 0.5f)
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_gps)));

            mMap.setOnMarkerClickListener(new MapMarkerBounce());

            String origin = String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
            String destination = String.valueOf(teacherLatLng.latitude) + "," + String.valueOf(teacherLatLng.longitude);
            mViewModel.getDirectionData(origin, destination, getString(R.string.google_maps_key));

            double xDiff = teacherLatLng.longitude - latLng.longitude;
            double yDiff = teacherLatLng.latitude - latLng.latitude;

            CameraPosition cameraPosition = new CameraPosition.Builder().target(midPoint(latLng.latitude, latLng.longitude, teacherLatLng.latitude, teacherLatLng.longitude)).zoom(9).bearing(90).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(latLng);
            builder.include(teacherLatLng);
            LatLngBounds bound = builder.build();

            googleMap.setMinZoomPreference(6.0f);
            googleMap.setMaxZoomPreference(14.0f);
            googleMap.setLatLngBoundsForCameraTarget(bound);

            googleMap.setOnCameraIdleListener(() -> {
                LatLng center = googleMap.getCameraPosition().target;
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(center.latitude);
                temp.setLongitude(center.longitude);
            });
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_gps);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(80, 400, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private LatLng midPoint(double lat1, double long1, double lat2, double long2) {
        return new LatLng((lat1 + lat2) / 2, (long1 + long2) / 2);
    }

    private double angleBteweenCoordinate(double lat1, double long1, double lat2,
                                          double long2) {
        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.atan2(y, x);
        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng;
        return brng;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 500:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                } else {
                    loadMap();
                    isGpsEnable();
                }
                break;
        }
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onBackTimeLine() {
        navController.popBackStack();
    }

    @Override
    public void handleError(Throwable throwable) {
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessDirection(DirectionResponses mDirectionResponses) {
        getBaseActivity().hideLoading();
        drawPolyline(mDirectionResponses);
    }

    private void drawPolyline(@NonNull DirectionResponses response) {
        if (response != null) {
            String shape = response.getRoutes().get(0).getOverviewPolyline().getPoints();
            PolylineOptions polyline = new PolylineOptions()
                    .addAll(PolyUtil.decode(shape))
                    .width(8f)
                    .color(getResources().getColor(R.color.app_map_line));
            mMap.addPolyline(polyline);
        }
    }


    @Override
    public void onConnected(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        getBaseActivity().hideLoading();
        setMapLocation();
    }

    public void setMapLocation() {
        if (mFragmentDirectionMapBinding.mapContainer != null) {
            mFragmentDirectionMapBinding.mapContainer.onCreate(null);
            mFragmentDirectionMapBinding.mapContainer.onResume();
            mFragmentDirectionMapBinding.mapContainer.getMapAsync(this);
            getBaseActivity().hideLoading();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
//            googleApiClient.connect();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (googleApiClient.isConnected()) {
//            googleApiClient.disconnect();
//        }
//    }


    private void isGpsEnable() {
        locationManager = (LocationManager) getBaseActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            SettingsClient client = LocationServices.getSettingsClient(getContext());
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(getBaseActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    // All location settings are satisfied. The client can initialize
                    // location requests here.
                    // ...
                }
            });

            task.addOnFailureListener(getBaseActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(getBaseActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {

                        }
                    }
                }
            });
        }
    }

}