package com.ryx.tdreeb.ui.fragments.parentfragment.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.model.BitmapDescriptorFactory;
import com.google.android.libraries.maps.model.CameraPosition;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.Marker;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.databinding.FragmentMapBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> implements MapNavigator, OnMapReadyCallback {

    FragmentMapBinding mFragmentMapBinding;
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap mMap;
    private NavController navController;
    private String subjectName;
    private boolean isCourse = true;
    private CoursesResponse mCoursesResponse;

    @Inject
    DataManager dataManager;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
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
        return R.layout.fragment_map;
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
        mViewModel.setNavigator(this);
        mFragmentMapBinding = getViewDataBinding();
        if (getArguments() != null) {
            MapFragmentArgs args = MapFragmentArgs.fromBundle(getArguments());
            subjectName = args.getSubjectName();
            isCourse = args.getIsCourse();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentMapBinding.getRoot());

        tvToolBarTitle = mFragmentMapBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentMapBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentMapBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.select_loaction));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);


        mFragmentMapBinding.maplayout.setCornerRadius(60);
        if (isCourse) {
            getBaseActivity().showLoading();
            mViewModel.getCourse(subjectName);
        }else {
            getBaseActivity().showLoading();
            mViewModel.getTraining(subjectName);
        }
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void loadMap() {
        if (mFragmentMapBinding.mapContainer != null) {
            mFragmentMapBinding.mapContainer.onCreate(null);
            mFragmentMapBinding.mapContainer.onResume();
            mFragmentMapBinding.mapContainer.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = 24.252775154664523;
        double lng = 89.91786003112793;

        for (int i = 0; i < mCoursesResponse.getData().getCourses().size(); i++) {
            if (mCoursesResponse.getData().getCourses().get(i).getTrainer().getLocation().getLatitude() != null && mCoursesResponse.getData().getCourses().get(i).getTrainer().getLocation().getLongitude() != null) {
                lat = Double.parseDouble(mCoursesResponse.getData().getCourses().get(i).getTrainer().getLocation().getLatitude());
                lng = Double.parseDouble(mCoursesResponse.getData().getCourses().get(i).getTrainer().getLocation().getLongitude());
                createMarker(lat, lng, mCoursesResponse.getData().getCourses().get(i).getTrainer().getName(), "", R.drawable.ic_map_pin);
            }
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
//        CameraPosition cameraPosition;// = new CameraPosition(latLng, 0, 0, 0);
//        cameraPosition = CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), (float) 14.0);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 500:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                } else {
                    loadMap();
                }
                break;
        }
    }

    @Override
    public void openTrainerList() {
        navController.popBackStack();
//        MapFragmentDirections.ActionMapFragmentToTrainerListFragment action = MapFragmentDirections.actionMapFragmentToTrainerListFragment();
//        action.setSubjectName(subjectName);
//        action.setIsCourse(isCourse);
//        navController.navigate(action);
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
    public void onSuccessCourse(CoursesResponse mCoursesResponse) {
        getBaseActivity().hideLoading();
        this.mCoursesResponse = mCoursesResponse;
        if (!getBaseActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getBaseActivity().requestPermissionsSafely(permission, 500);
        } else {
            loadMap();
        }
    }
}
