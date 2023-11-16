package com.ryx.tdreeb.ui.fragments.parentfragment.home;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyChildHomeAdapter;
import com.ryx.tdreeb.adapters.TrainerHomeSessionAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.databinding.FragmentHomeParentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

public class HomeParentFragment extends BaseFragment<FragmentHomeParentBinding, HomeParentViewModel> implements HomeParentNavigator {

    FragmentHomeParentBinding mFragmentHomeParentBinding;
    private NavController navController;
    private ImageView profileImage, drawerIcon;

    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    TrainerHomeSessionAdapter mTrainerHomeSession;
    @Inject
    MyChildHomeAdapter mMyChildHomeAdapter;
    @Inject
    LinearLayoutManager hLinearLayoutManager;
    @Inject
    DataManager dataManager;

    List<ChildenModel> list;
    ChildenModel childenModel;

    public HomeParentFragment() {
    }

    public static HomeParentFragment newInstance() {
        HomeParentFragment fragment = new HomeParentFragment();
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
        return R.layout.fragment_home_parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getBaseActivity().getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.app_color));
//        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mFragmentHomeParentBinding = getViewDataBinding();
        navController = Navigation.findNavController(view);
        setUp();
    }

    private void setUp() {
        drawerIcon = mFragmentHomeParentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImage = mFragmentHomeParentBinding.getRoot().findViewById(R.id.profile_image);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImage);

        //TODO:- Action Button
        //Adapter Setup
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomeParentBinding.rvCommingSession.setLayoutManager(mLinearLayoutManager);
        mFragmentHomeParentBinding.rvCommingSession.addItemDecoration(new EqualSpacingItemDecoration(20));
        mFragmentHomeParentBinding.rvCommingSession.setItemAnimator(new DefaultItemAnimator());
        mFragmentHomeParentBinding.rvCommingSession.setAdapter(mTrainerHomeSession);
        mTrainerHomeSession.setListener(mSessionModel -> {
            HomeParentFragmentDirections.ActionHomeParentFragmentToTrainerSessionDetailsFragment action = HomeParentFragmentDirections.actionHomeParentFragmentToTrainerSessionDetailsFragment();
            action.setSessionData(mSessionModel);
            navController.navigate(action);
        });

        hLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentHomeParentBinding.rvChild.setLayoutManager(hLinearLayoutManager);
        mFragmentHomeParentBinding.rvChild.addItemDecoration(new EqualSpacingItemDecoration(30, EqualSpacingItemDecoration.HORIZONTAL));
        mFragmentHomeParentBinding.rvChild.setItemAnimator(new DefaultItemAnimator());
        mFragmentHomeParentBinding.rvChild.setAdapter(mMyChildHomeAdapter);
        mMyChildHomeAdapter.setListener(new ChildrenCallBack() {
            @Override
            public void onClickItem() {
                childenModel = null;
                openChildren();
            }

            @Override
            public void onClickItemView(ChildenModel childenModel) {
                HomeParentFragment.this.childenModel = childenModel;
                openChildren();
            }
        });

        drawerIcon.setOnClickListener(v -> {
            openMainDrawer();
        });

        getBaseActivity().showLoading();
        mViewModel.getResource(dataManager.getCurrentUserId(), "");

        if (dataManager.getUserType() == DataManager.UserInMode.STUDENT.getType()) {
            mFragmentHomeParentBinding.rvChild.setVisibility(View.GONE);
            mFragmentHomeParentBinding.textView3.setVisibility(View.GONE);
            mFragmentHomeParentBinding.textView2.setVisibility(View.GONE);
            mFragmentHomeParentBinding.imgReportCard.setImageResource(R.drawable.ic_report_card);
            mFragmentHomeParentBinding.tvReportCard.setText(R.string.report_card_new_line);

            if(dataManager.getUnderEighteen()){
                mFragmentHomeParentBinding.imgEducation.setImageResource(R.drawable.sessions_icon);
                mFragmentHomeParentBinding.tvEducation.setText(R.string.my_session);

                mFragmentHomeParentBinding.imgFindTeacher.setImageResource(R.drawable.my_resources);
                mFragmentHomeParentBinding.tvFindTeacher.setText(R.string.all_resource);

                mFragmentHomeParentBinding.imgLiveCoursesLinear.setImageResource(R.drawable.ic_report_card);
                mFragmentHomeParentBinding.tvLiveCourses.setText(R.string.report_card);

                mFragmentHomeParentBinding.third.setVisibility(View.GONE);
                mFragmentHomeParentBinding.forth.setVisibility(View.GONE);
//                mFragmentHomeParentBinding.allContentRelative.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.app_color));
            }else {
                mFragmentHomeParentBinding.imgEducation.setScaleType(ImageView.ScaleType.FIT_XY);
                mFragmentHomeParentBinding.imgFindTeacher.setScaleType(ImageView.ScaleType.FIT_XY);
            }

        } else {
            mFragmentHomeParentBinding.imgEducation.setScaleType(ImageView.ScaleType.FIT_XY);
            mFragmentHomeParentBinding.imgFindTeacher.setScaleType(ImageView.ScaleType.FIT_XY);
            mFragmentHomeParentBinding.rvChild.setVisibility(View.VISIBLE);
            mFragmentHomeParentBinding.textView3.setVisibility(View.VISIBLE);
            mFragmentHomeParentBinding.textView2.setVisibility(View.VISIBLE);
            mFragmentHomeParentBinding.imgReportCard.setImageResource(R.drawable.my_child);
            mFragmentHomeParentBinding.tvReportCard.setText(R.string.my_children_new_line);
            getBaseActivity().showLoading();
            mViewModel.getChildren(dataManager.getCurrentUserId());
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    //  TODO:- Click To Open Fragment
    @Override
    public void openFindCourse() {
        navController.navigate(R.id.action_homeParentFragment_to_findCourseFragment);
    }

    @Override
    public void openFindTrainer() {
        navController.navigate(R.id.action_homeParentFragment_to_findTrainerFragment);
    }

    @Override
    public void openSession() {
        navController.navigate(R.id.action_homeParentFragment_to_mySessionParentFragment3);
    }

    @Override
    public void openChat() {
        navController.navigate(R.id.action_homeParentFragment_to_trainerChatFragment);
    }

    @Override
    public void openChildrenList() {
        if (dataManager.getUserType() == DataManager.UserInMode.STUDENT.getType()) {
            navController.navigate(R.id.action_homeParentFragment_to_reportCardFragment);
        } else {
            HomeParentFragmentDirections.ActionHomeParentFragmentToChildrenListFragment action = HomeParentFragmentDirections.actionHomeParentFragmentToChildrenListFragment();
            navController.navigate(action);
        }
    }

    @Override
    public void openChildren() {
        HomeParentFragmentDirections.ActionHomeParentFragmentToAddChildrenFragment action = HomeParentFragmentDirections.actionHomeParentFragmentToAddChildrenFragment();
        if (childenModel == null) {
            navController.navigate(action);
        } else {
            action.setChildrenData(childenModel);
            navController.navigate(action);
        }
    }

    @Override
    public void openResource() {
        navController.navigate(R.id.action_homeParentFragment_to_myResourceFragment);
    }

    @Override
    public void openFavorites() {
        navController.navigate(R.id.action_homeParentFragment_to_myFavoritesFragment);
    }

    @Override
    public void openVideoCourse() {
        navController.navigate(R.id.action_homeParentFragment_to_videoCourseFragment);
    }

    @Override
    public void openLiveCourse() {
        navController.navigate(R.id.action_homeParentFragment_to_liveCoursesFragment);
    }

    @Override
    public void openProfile() {
        navController.navigate(R.id.action_homeParentFragment_to_profileFragment);
    }

    @Override
    public void openMainDrawer() {
        getBaseActivity().openDrawer();
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
            list = mAddChildResponse.getData().getChilds();
            mMyChildHomeAdapter.addItems(list);
        }
    }

    @Override
    public void onSuccessResource(SessionResponse mSessionResponse) {
        getBaseActivity().hideLoading();
        if (mSessionResponse.getCode() == 200) {
            mTrainerHomeSession.addItems(mSessionResponse.getData().getSessions());
        }
    }
}