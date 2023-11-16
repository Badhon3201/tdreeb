package com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.ryx.tdreeb.BuildConfig;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.FindVideoCourseAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoCourseAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.FragmentBuyTrainerBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.GridSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsFragment;
import com.ryx.tdreeb.ui.dialogs.videoplayer.VideoPlayerFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.utils.BindingUtils;

import java.util.List;

import javax.inject.Inject;

public class BuyTrainerFragment extends BaseFragment<FragmentBuyTrainerBinding, BuyTrainerViewModel> implements BuyTrainerNavigator {

    FragmentBuyTrainerBinding mFragmentBuyTrainerBinding;
    @Inject
    VideoCourseAdapter mVideoCourseAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    DataManager dataManager;

    private VideoPlayerFragment mVideoPlayerFragment;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    private ChooseKidsFragment dialog;
    private NavController navController;

    private List<ChildenModel> listChildrenModel;
    private ChildenModel childenModel;
    private LiveCourseModel mLiveCourseModel;
    private boolean isMyVideo;

    public BuyTrainerFragment() {
        // Required empty public constructor
    }

    public static BuyTrainerFragment newInstance() {
        BuyTrainerFragment fragment = new BuyTrainerFragment();
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
        return R.layout.fragment_buy_trainer;
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
        mFragmentBuyTrainerBinding = getViewDataBinding();
        if (getArguments() != null) {
            BuyTrainerFragmentArgs args = BuyTrainerFragmentArgs.fromBundle(getArguments());
            mLiveCourseModel = args.getLiveCourses();
            isMyVideo = args.getIsMyvideo();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentBuyTrainerBinding.getRoot());
        mVideoPlayerFragment = VideoPlayerFragment.newInstance();

        tvToolBarTitle = mFragmentBuyTrainerBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentBuyTrainerBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentBuyTrainerBinding.getRoot().findViewById(R.id.profile_image);

        if (mLiveCourseModel.getCourseType().equals("LiveCourse")) {
            tvToolBarTitle.setText(getString(R.string.trainer_live_courses_title));
        } else {
            tvToolBarTitle.setText(getString(R.string.video_courses));
        }
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        dialog = ChooseKidsFragment.newInstance();
        dialog.setCallBack((childenModel) -> {
            BuyTrainerFragment.this.childenModel = childenModel;
            openPaymentSummary();
        });
        wrapTabIndicatorToTitle(mFragmentBuyTrainerBinding.tabLayout, 90, 90);

        if(isMyVideo){
            mFragmentBuyTrainerBinding.btnBuy.setVisibility(View.GONE);
        }else{
            mFragmentBuyTrainerBinding.btnBuy.setVisibility(View.VISIBLE);
        }

        mGridLayoutManager.setSpanCount(1);
        mFragmentBuyTrainerBinding.rvVideo.setLayoutManager(mGridLayoutManager);
        int spanCount = 1;
        int spacing = 30;
        boolean includeEdge = true;
        mFragmentBuyTrainerBinding.rvVideo.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mFragmentBuyTrainerBinding.rvVideo.setAdapter(mVideoCourseAdapter);
        mVideoCourseAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {
                if(isMyVideo){
                    openVideoPlayer(subjectName);
                }
            }

            @Override
            public void onClickItemModel(SubjectModel subjectName) {

            }
        });

        if (mLiveCourseModel != null) {
            mFragmentBuyTrainerBinding.tvCourseTitle.setText(mLiveCourseModel.getCourseTitle());
            mFragmentBuyTrainerBinding.tvName.setText(mLiveCourseModel.getTrainerResponse().getName());
            mFragmentBuyTrainerBinding.tvSubjects.setText(" - " + mLiveCourseModel.getCourseSubject());
            mFragmentBuyTrainerBinding.tvAmountHour.setText("AED: " + mLiveCourseModel.getPrice());
            mFragmentBuyTrainerBinding.tvDetails.setText(mLiveCourseModel.getMeetingDetails());
            mFragmentBuyTrainerBinding.tvSubjectsHeader.setText(getString(R.string.online_arg_class, mLiveCourseModel.getCourseSubject()));
            mFragmentBuyTrainerBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        mFragmentBuyTrainerBinding.tvDetails.setText(mLiveCourseModel.getMeetingDetails());
                    } else {
                        mFragmentBuyTrainerBinding.tvDetails.setText(mLiveCourseModel.getMeetingDescription());
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            BindingUtils.setImageUrl(mFragmentBuyTrainerBinding.trainerImage, mLiveCourseModel.getTrainerResponse().getImage());
            mFragmentBuyTrainerBinding.tvCourseInclude.setVisibility(View.GONE);

            BindingUtils.set_image_from_url(mFragmentBuyTrainerBinding.imgHeader,mLiveCourseModel.getUploadLink());

            if (mLiveCourseModel.getVedioCourseFiles() != null && mLiveCourseModel.getVedioCourseFiles().size() > 0) {

                if(mLiveCourseModel.getOverviewVideo()!=null){
                    mFragmentBuyTrainerBinding.view5.setOnClickListener(v -> {
                        openVideoPlayer(mLiveCourseModel.getOverviewVideo());
                    });
                }
                mFragmentBuyTrainerBinding.btnBook.setVisibility(View.GONE);
                mFragmentBuyTrainerBinding.tvCourseInclude.setVisibility(View.VISIBLE);
                mVideoCourseAdapter.addItems(mLiveCourseModel.getVedioCourseFiles());
            }else if(mLiveCourseModel.getVedioCourseFilesResponse() != null && mLiveCourseModel.getVedioCourseFilesResponse().size() > 0){

                if(mLiveCourseModel.getOverviewVideo()!=null){
                    mFragmentBuyTrainerBinding.view5.setOnClickListener(v -> {
                        openVideoPlayer(mLiveCourseModel.getOverviewVideo());
                    });
                }
                mFragmentBuyTrainerBinding.btnBook.setVisibility(View.GONE);
                mFragmentBuyTrainerBinding.tvCourseInclude.setVisibility(View.VISIBLE);
                mVideoCourseAdapter.addItems(mLiveCourseModel.getVedioCourseFilesResponse());
            }
        }

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openChooseKid() {
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            if (listChildrenModel != null) {
                dialog.setData(listChildrenModel);
                dialog.show(getBaseActivity().getSupportFragmentManager());
            } else {
                getBaseActivity().showLoading();
                mViewModel.getChildren(dataManager.getCurrentUserId());
            }
        } else {
            openPaymentSummary();
        }

    }

    @Override
    public void openPaymentSummary() {
        BuyTrainerFragmentDirections.ActionBuyTrainerFragmentToPaymentSummaryFragment action = BuyTrainerFragmentDirections.actionBuyTrainerFragmentToPaymentSummaryFragment();
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            if (childenModel != null) {
                action.setChildenData(childenModel);
                action.setLiveCourseData(mLiveCourseModel);
            } else {
                Toast.makeText(getContext(), "Select Children", Toast.LENGTH_SHORT).show();
            }
        } else {
            action.setLiveCourseData(mLiveCourseModel);
        }
        navController.navigate(action);
    }

    @Override
    public void openVideoPlayer(String url) {
        mVideoPlayerFragment.setData(url);
        mVideoPlayerFragment.show(getParentFragmentManager());
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
    public void onSuccessAddChildResponse(AddChildResponse mAddChildResponse) {
        getBaseActivity().hideLoading();
        if (mAddChildResponse.getCode() == 200) {
            listChildrenModel = mAddChildResponse.getData().getChilds();
            openChooseKid();
        }
    }

}