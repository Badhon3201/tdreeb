package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.FindCourseAdapter;
import com.ryx.tdreeb.adapters.MyVideoCourseAdapter;
import com.ryx.tdreeb.adapters.slider.TheSlideitemsPagerAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoHomeAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.allvideocourse.AllVideoCourseResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.data.model.api.videoslider.SliderModel;
import com.ryx.tdreeb.data.model.api.videoslider.SliderResponse;
import com.ryx.tdreeb.databinding.FragmentVideoCourseBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.inject.Inject;


public class VideoCourseFragment extends BaseFragment<FragmentVideoCourseBinding,
        VideoCourseViewModel> implements VideoCourseNavigator {

    FragmentVideoCourseBinding mFragmentVideoCourseBinding;
    private NavController navController;
    private FindVideoCourseFragment mFindVideoCourseFragment;
    private SubjectModel subjectName;
    private LiveCourseModel mLiveCourseModel;

    @Inject
    FindCourseAdapter mFindCourseAdapter;
    @Inject
    MyVideoCourseAdapter mMyVideoCourseAdapter;
    @Inject
    VideoHomeAdapter mVideoHomeAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    LinearLayoutManager mLinearLayoutManagerTwo;
    @Inject
    LinearLayoutManager mLinearLayoutManagerThree;

    private List<SliderModel> listItems;
    TheSlideitemsPagerAdapter itemsPager_adapter;

    @Inject
    DataManager dataManager;

    private TextView tvToolBarTitle;
    private ImageView backImg;
    private LinearLayoutCompat myVideoLL;

    public VideoCourseFragment() {
        // Required empty public constructor
    }

    public static VideoCourseFragment newInstance() {
        VideoCourseFragment fragment = new VideoCourseFragment();
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
        return R.layout.fragment_video_course;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getBaseActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getContext().getColor(R.color.app_color_two));
            }
        }
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mFragmentVideoCourseBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        listItems = new ArrayList<>();
        itemsPager_adapter = new TheSlideitemsPagerAdapter(getContext(), listItems);
        mFragmentVideoCourseBinding.myPager.setAdapter(itemsPager_adapter);

        // The_slide_timer
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TheSlideTimer(), 4000, 5000);
        mFragmentVideoCourseBinding.myTablayout.setupWithViewPager(mFragmentVideoCourseBinding.myPager, true);

        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentVideoCourseBinding.getRoot());
        mFindVideoCourseFragment = FindVideoCourseFragment.newInstance();

        tvToolBarTitle = mFragmentVideoCourseBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentVideoCourseBinding.getRoot().findViewById(R.id.drawer_icon);
        myVideoLL = mFragmentVideoCourseBinding.getRoot().findViewById(R.id.my_video_ll);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentVideoCourseBinding.rvCategory.setLayoutManager(mLinearLayoutManager);
        mFragmentVideoCourseBinding.rvCategory.addItemDecoration(new EqualSpacingItemDecoration(0));
        mFragmentVideoCourseBinding.rvCategory.setItemAnimator(new DefaultItemAnimator());
        mFragmentVideoCourseBinding.rvCategory.setAdapter(mFindCourseAdapter);
        mFindCourseAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {

            }

            @Override
            public void onClickItemModel(SubjectModel subjectName) {
                VideoCourseFragment.this.subjectName = subjectName;
                if (!subjectName.getSubSubject().isEmpty()) {
                    VideoCourseFragmentDirections.ActionVideoCourseFragmentToSubCategoryFragment2 action = VideoCourseFragmentDirections.actionVideoCourseFragmentToSubCategoryFragment2();
                    action.setSubjectName(subjectName);
                    action.setIsCourse(false);
                    navController.navigate(action);
                } else {
                    openVideoList();
                }
            }
        });

        mLinearLayoutManagerThree.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentVideoCourseBinding.rv.setLayoutManager(mLinearLayoutManagerThree);
        mFragmentVideoCourseBinding.rv.addItemDecoration(new EqualSpacingItemDecoration(0));
        mFragmentVideoCourseBinding.rv.setItemAnimator(new DefaultItemAnimator());
        mFragmentVideoCourseBinding.rv.setAdapter(mVideoHomeAdapter);
        mVideoHomeAdapter.setListener((pos, liveCourseModel) -> {
            if(liveCourseModel != null){
                VideoCourseFragment.this.mLiveCourseModel = liveCourseModel;
                openBuyTrainer();
            }
        });

        mLinearLayoutManagerTwo.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentVideoCourseBinding.rvTopIt.setLayoutManager(mLinearLayoutManagerTwo);
        mFragmentVideoCourseBinding.rvTopIt.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentVideoCourseBinding.rvTopIt.setItemAnimator(new DefaultItemAnimator());
        mFragmentVideoCourseBinding.rvTopIt.setAdapter(mMyVideoCourseAdapter);

        getBaseActivity().showLoading();
        mViewModel.getSlider();

        tvToolBarTitle.setText(getString(R.string.video_courses));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());

        myVideoLL.setOnClickListener(v -> {
            navController.navigate(VideoCourseFragmentDirections.actionVideoCourseFragmentToMyVideoFragment());
        });


//        if(dataManager.getUnderEighteen()){
//            mFragmentVideoCourseBinding.tabLayout.setVisibility(View.GONE);
//            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//            viewPagerAdapter.addFragment(MyVideoFragment.newInstance(), "");
//            mFragmentVideoCourseBinding.viewPager.setAdapter(viewPagerAdapter);
//        }else {
////            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
////            viewPagerAdapter.addFragment(mFindVideoCourseFragment, "Find Video Course");
////            viewPagerAdapter.addFragment(MyVideoFragment.newInstance(), "My Video Course");
////            mFragmentVideoCourseBinding.viewPager.setAdapter(viewPagerAdapter);
////            mFragmentVideoCourseBinding.tabLayout.setupWithViewPager(mFragmentVideoCourseBinding.viewPager);
////            wrapTabIndicatorToTitle(mFragmentVideoCourseBinding.tabLayout, 90, 90);
////            mFindVideoCourseFragment.setListener(new SelectPostionAdapterDataInterface() {
////                @Override
////                public void selectedStringData(String subjectName) {
////                    VideoCourseFragment.this.subjectName = subjectName;
////                    openVideoList();
////                }
////            });
//        }

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openVideoList() {
//        if(!subjectName.isEmpty()){
        VideoCourseFragmentDirections.ActionVideoCourseFragmentToVideoListFragment action = VideoCourseFragmentDirections.actionVideoCourseFragmentToVideoListFragment();
        action.setSubjectName(subjectName);
        navController.navigate(action);
//        }
    }

    @Override
    public void openCategory() {
        navController.navigate(R.id.action_videoCourseFragment_to_findVideoCourseFragment);
    }

    @Override
    public void openBuyTrainer() {
        VideoCourseFragmentDirections.ActionVideoCourseFragmentToBuyTrainerFragment action = VideoCourseFragmentDirections.actionVideoCourseFragmentToBuyTrainerFragment();
        action.setLiveCourses(mLiveCourseModel);
        navController.navigate(action);
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
    public void onSuccessSubject(SubjectResponse mSubjectResponse) {
        getBaseActivity().hideLoading();
        mFindCourseAdapter.addItems(mSubjectResponse.getData().getSubjects());
        getBaseActivity().showLoading();
        mViewModel.getAllVideoCourse();
    }

    @Override
    public void onSuccess(AllVideoCourseResponse mAllVideoCourseResponse) {
        getBaseActivity().hideLoading();
        if (mAllVideoCourseResponse.getIsSuccess()) {
            mVideoHomeAdapter.addItems(mAllVideoCourseResponse.getData().getResult());
        } else {
            Toast.makeText(getBaseActivity(), "" + mAllVideoCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessSlider(SliderResponse mSliderResponse) {
        getBaseActivity().hideLoading();
        if (mSliderResponse.getIsSuccess()) {
            listItems = mSliderResponse.getData().getSlider();
            itemsPager_adapter.addItem(listItems);
        } else {
            Toast.makeText(getBaseActivity(), "" + mSliderResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
        getBaseActivity().showLoading();
        mViewModel.getSubject();
    }

    //  TODO:
    public class TheSlideTimer extends TimerTask {
        @Override
        public void run() {

            getBaseActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mFragmentVideoCourseBinding.myPager.getCurrentItem() < listItems.size() - 1) {
                        mFragmentVideoCourseBinding.myPager.setCurrentItem(mFragmentVideoCourseBinding.myPager.getCurrentItem() + 1);
                    } else
                        mFragmentVideoCourseBinding.myPager.setCurrentItem(0);
                }
            });
        }
    }
}