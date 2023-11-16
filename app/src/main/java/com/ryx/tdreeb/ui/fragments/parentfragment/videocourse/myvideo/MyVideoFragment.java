package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
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
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.FragmentMyVideoBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;


public class MyVideoFragment extends BaseFragment<FragmentMyVideoBinding,
        MyVideoViewModel> implements MyVideoNavigator {

    FragmentMyVideoBinding mFragmentMyVideoBinding;
    @Inject
    MyVideoCourseAdapter mMyVideoCourseAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;


    public MyVideoFragment() {
        // Required empty public constructor
    }

    public static MyVideoFragment newInstance() {
        MyVideoFragment fragment = new MyVideoFragment();
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
        return R.layout.fragment_my_video;
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
        mFragmentMyVideoBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
//TODO:- Action Button
        navController = Navigation.findNavController(mFragmentMyVideoBinding.getRoot());
        tvToolBarTitle = mFragmentMyVideoBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentMyVideoBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentMyVideoBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.video_courses));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentMyVideoBinding.rvVideo.setLayoutManager(mLinearLayoutManager);
        mFragmentMyVideoBinding.rvVideo.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentMyVideoBinding.rvVideo.setItemAnimator(new DefaultItemAnimator());
        mFragmentMyVideoBinding.rvVideo.setAdapter(mMyVideoCourseAdapter);
        mMyVideoCourseAdapter.setListener(new LiveCourseModelPass() {
            @Override
            public void onClickLiveCourses(int pos, LiveCourseModel liveCourseModel) {
                //MyVideoFragment.this.mLiveCourseModel = liveCourseModel;

                if(liveCourseModel != null){
                    MyVideoFragmentDirections.ActionMyVideoFragmentToBuyTrainerFragment action = MyVideoFragmentDirections.actionMyVideoFragmentToBuyTrainerFragment();
                    action.setLiveCourses(liveCourseModel);
                    action.setIsMyvideo(true);
                    navController.navigate(action);
                }
            }
        });

        if(dataManager.getUnderEighteen()){
            mFragmentMyVideoBinding.tvVideoCourse.setVisibility(View.VISIBLE);
        }

        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "VideoCourse");
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mMyVideoCourseAdapter.addItemsTwo(mSessionResponse.getData().getSessions());
        }
    }
}