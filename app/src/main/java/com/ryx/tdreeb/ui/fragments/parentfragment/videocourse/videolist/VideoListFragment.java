package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.videolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyVideoCourseAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.ParentAllVideoList.Data;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.FragmentVideoListBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.filterbottomsheet.FilterBottomSheetFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseFragmentDirections;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist.TrainerListFragmentDirections;

import java.util.ArrayList;

import javax.inject.Inject;


public class VideoListFragment extends BaseFragment<FragmentVideoListBinding, VideoListViewModel> implements VideoListNavigator {

    FragmentVideoListBinding mFragmentVideoListBinding;

    @Inject
    MyVideoCourseAdapter mMyVideoCourseAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;
    private SubjectModel subjectName = null;
    private LiveCourseModel liveCourseModel;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    public VideoListFragment() {
        // Required empty public constructor
    }

    public static VideoListFragment newInstance() {
        VideoListFragment fragment = new VideoListFragment();
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
        return R.layout.fragment_video_list;
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
        mFragmentVideoListBinding = getViewDataBinding();
        if (getArguments() != null) {
            VideoListFragmentArgs args = VideoListFragmentArgs.fromBundle(getArguments());
            subjectName = args.getSubjectName();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentVideoListBinding.getRoot());

        tvToolBarTitle = mFragmentVideoListBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentVideoListBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentVideoListBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.video_courses));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentVideoListBinding.rvVideo.setLayoutManager(mLinearLayoutManager);
        mFragmentVideoListBinding.rvVideo.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentVideoListBinding.rvVideo.setItemAnimator(new DefaultItemAnimator());
        mFragmentVideoListBinding.rvVideo.setAdapter(mMyVideoCourseAdapter);
        mMyVideoCourseAdapter.setListener((pos, liveCourseModel) -> {
            VideoListFragment.this.liveCourseModel = liveCourseModel;
            getBaseActivity().hideKeyboard();
            openBuy();
        });

//        mFragmentVideoListBinding.e.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                mMyVideoCourseAdapter.getFilter().filter(s.toString());
//            }
//        });

        if(subjectName.getSubSubjectResponse()!=null){
            mFragmentVideoListBinding.tvCategory.setText(subjectName.getSubjectName());
            mFragmentVideoListBinding.tvSubCategory.setText(subjectName.getSubSubjectResponse().getSubjectName());
            getBaseActivity().showLoading();
            mViewModel.getVideoCourseCourses(subjectName.getSubSubjectResponse().getSubjectName());
        }else{
            mFragmentVideoListBinding.tvCategory.setText(subjectName.getSubjectName());
            getBaseActivity().showLoading();
            mViewModel.getVideoCourseCourses(subjectName.getSubjectName());
        }


    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openBuy() {
        if (liveCourseModel != null) {
            VideoListFragmentDirections.ActionVideoListFragmentToBuyTrainerFragment action = VideoListFragmentDirections.actionVideoListFragmentToBuyTrainerFragment();
            action.setLiveCourses(liveCourseModel);
            navController.navigate(action);
        }
    }

    @Override
    public void openMap() {
//        VideoListFragmentDirections.ActionVideoListFragmentToMapFragment action = VideoListFragmentDirections.actionVideoListFragmentToMapFragment();
//        if(subjectName.getSubSubjectResponse()!=null) {
//            action.setIsCourse(false);
//            action.setSubjectName(subjectName.getSubSubjectResponse().getSubjectName());
//            navController.navigate(action);
//        }else{
//            action.setIsCourse(false);
//            action.setSubjectName(subjectName.getSubjectName());
//            navController.navigate(action);
//        }
    }

    @Override
    public void openBottomSheet() {
        FilterBottomSheetFragment carModelBottomSheetFragment = new FilterBottomSheetFragment();
        carModelBottomSheetFragment.setData(new ArrayList<>());
        carModelBottomSheetFragment.setListener(str -> {

        });
        carModelBottomSheetFragment.show(getChildFragmentManager(), "CarModelBottomSheetFragment");
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
    public void onSuccessVideoCourse(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        if (mLiveCourseResponse.getIsSuccess()) {
            mMyVideoCourseAdapter.addItems(mLiveCourseResponse.getData().getResult());
        }

    }
}