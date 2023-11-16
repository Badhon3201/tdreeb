package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.LiveAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.databinding.TrainerLiveCoursesFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrainerLiveCoursesFragment extends BaseFragment<TrainerLiveCoursesFragmentBinding, TrainerLiveCoursesViewModel> implements TrainerLiveCoursesNavigator {

    TrainerLiveCoursesFragmentBinding mTrainerLiveCoursesFragmentBinding;
    private NavController navController;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    @Inject
    LiveAdapter mLiveAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public TrainerLiveCoursesFragment() {
    }

    public static TrainerLiveCoursesFragment newInstance() {
        TrainerLiveCoursesFragment fragment = new TrainerLiveCoursesFragment();
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
        return R.layout.trainer_live_courses_fragment;
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
        mTrainerLiveCoursesFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerLiveCoursesFragmentBinding.getRoot());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerLiveCoursesFragmentBinding.rvLiveCourse.setLayoutManager(mLinearLayoutManager);
        mTrainerLiveCoursesFragmentBinding.rvLiveCourse.addItemDecoration(new EqualSpacingItemDecoration(40));
        mTrainerLiveCoursesFragmentBinding.rvLiveCourse.setItemAnimator(new DefaultItemAnimator());
        mTrainerLiveCoursesFragmentBinding.rvLiveCourse.setAdapter(mLiveAdapter);
        mLiveAdapter.setListener(new LiveCourseCallBack() {
            @Override
            public void onClickEdit(LiveCourseModel mLiveCourseModel) {
                TrainerLiveCoursesFragmentDirections.ActionTrainerLiveCoursesFragmentToAddLiveCourseFragment action = TrainerLiveCoursesFragmentDirections.actionTrainerLiveCoursesFragmentToAddLiveCourseFragment();
                action.setLiveCourseData(mLiveCourseModel);
                navController.navigate(action);
            }

            @Override
            public void onClickDelete(LiveCourseModel mLiveCourseModel) {

            }
        });
        getBaseActivity().showLoading();
        mViewModel.getLiveCoursesByID(dataManager.getCurrentUserId());

        tvToolBarTitle = mTrainerLiveCoursesFragmentBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mTrainerLiveCoursesFragmentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerLiveCoursesFragmentBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_live_courses_title));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openAddLiveCourse() {
        navController.navigate(R.id.action_trainerLiveCoursesFragment_to_addLiveCourseFragment);
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
    public void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        if (mLiveCourseResponse.getIsSuccess()) {
            mLiveAdapter.addItems(mLiveCourseResponse.getData().getResult());
        } else {
            Toast.makeText(getContext(), "" + mLiveCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
