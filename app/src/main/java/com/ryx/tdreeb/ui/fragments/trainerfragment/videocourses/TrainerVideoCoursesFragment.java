package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.RequestAdapter;
import com.ryx.tdreeb.adapters.VideoCourseTrainerAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.databinding.TrainerVideoCoursesFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.SimpleDialogClick;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.LiveCourseCallBack;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.TrainerLiveCoursesFragmentDirections;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.AddVideoCourseFragment;

import javax.inject.Inject;

public class TrainerVideoCoursesFragment extends BaseFragment<TrainerVideoCoursesFragmentBinding, TrainerVideoCoursesViewModel> implements TrainerVideoCoursesNavigator {

    public TrainerVideoCoursesFragment() {
    }

    TrainerVideoCoursesFragmentBinding mTrainerVideoCoursesFragmentBinding;
    private NavController navController;
    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    @Inject
    VideoCourseTrainerAdapter mVideoCourseTrainerAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public static TrainerVideoCoursesFragment newInstance() {
        TrainerVideoCoursesFragment fragment = new TrainerVideoCoursesFragment();
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
        return R.layout.trainer_video_courses_fragment;
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
        mTrainerVideoCoursesFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerVideoCoursesFragmentBinding.getRoot());

        tvToolBarTitle = mTrainerVideoCoursesFragmentBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mTrainerVideoCoursesFragmentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerVideoCoursesFragmentBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_video_courses_toolbar_title));
        backImg.setPadding(10, 10, 10, 10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerVideoCoursesFragmentBinding.rvVideoCourse.setLayoutManager(mLinearLayoutManager);
        mTrainerVideoCoursesFragmentBinding.rvVideoCourse.addItemDecoration(new EqualSpacingItemDecoration(20));
        mTrainerVideoCoursesFragmentBinding.rvVideoCourse.setItemAnimator(new DefaultItemAnimator());
        mTrainerVideoCoursesFragmentBinding.rvVideoCourse.setAdapter(mVideoCourseTrainerAdapter);
        mVideoCourseTrainerAdapter.setListener(new LiveCourseCallBack() {
            @Override
            public void onClickEdit(LiveCourseModel mLiveCourseModel) {
                TrainerVideoCoursesFragmentDirections.ActionTrainerVideoCoursesFragmentToAddVideoCourseFragment action = TrainerVideoCoursesFragmentDirections.actionTrainerVideoCoursesFragmentToAddVideoCourseFragment();
                action.setVideoModelData(mLiveCourseModel);
                navController.navigate(action);
            }

            @Override
            public void onClickDelete(LiveCourseModel mLiveCourseModel) {
                getBaseActivity().showLoading();
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.delete_data))
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            getBaseActivity().showLoading();
                            mViewModel.removeVideoCourse(mLiveCourseModel.getLiveCourseId());
                        })
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        getBaseActivity().showLoading();
        mViewModel.getVideoCoursesByID(dataManager.getCurrentUserId());
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openAddVideoCourse() {
        navController.navigate(R.id.action_trainerVideoCoursesFragment_to_addVideoCourseFragment);
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
            mVideoCourseTrainerAdapter.addItems(mLiveCourseResponse.getData().getResult());
        } else {
            Toast.makeText(getContext(), "" + mLiveCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessRemoveVideoCourse(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        if (mLiveCourseResponse.getIsSuccess()) {
            getBaseActivity().openDialog(mLiveCourseResponse.getMessage(), () -> {
                getBaseActivity().showLoading();
                mViewModel.getVideoCoursesByID(dataManager.getCurrentUserId());
            });
        } else {
            Toast.makeText(getContext(), "" + mLiveCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
