package com.ryx.tdreeb.ui.fragments.parentfragment.trainerdetails;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.MyResourceAdapter;
import com.ryx.tdreeb.adapters.TrainerResourceAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.databinding.FragmentTrainerDetailsBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;


public class TrainerDetailsFragment extends BaseFragment<FragmentTrainerDetailsBinding, TrainerDetailsViewModel> implements TrainerDetailsNavigator {

    FragmentTrainerDetailsBinding mFragmentTrainerDetailsBinding;
    private NavController navController;
    private CourseModel mCourseModel;
    private boolean isCourse = true;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    MyResourceAdapter mMyResourceAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public TrainerDetailsFragment() {
        // Required empty public constructor
    }

    public static TrainerDetailsFragment newInstance() {
        TrainerDetailsFragment fragment = new TrainerDetailsFragment();
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
        return R.layout.fragment_trainer_details;
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
        mFragmentTrainerDetailsBinding = getViewDataBinding();
        if (getArguments() != null) {
            TrainerDetailsFragmentArgs args = TrainerDetailsFragmentArgs.fromBundle(getArguments());
            mCourseModel = args.getCourseData();
            isCourse = args.getIsCourse();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentTrainerDetailsBinding.getRoot());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);

        tvToolBarTitle = mFragmentTrainerDetailsBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentTrainerDetailsBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentTrainerDetailsBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.teacher_profile));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentTrainerDetailsBinding.rvResource.setLayoutManager(mLinearLayoutManager);
        mFragmentTrainerDetailsBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentTrainerDetailsBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mFragmentTrainerDetailsBinding.rvResource.setAdapter(mMyResourceAdapter);

        if (mCourseModel != null) {
            Glide.with(this).load(mCourseModel.getTrainer().getImage()).apply(options).into(mFragmentTrainerDetailsBinding.trainerImage);
            mFragmentTrainerDetailsBinding.tvName.setText(mCourseModel.getTrainer().getName());
            mFragmentTrainerDetailsBinding.ratingBar.setRating((float) mCourseModel.getTrainer().getRating());
            mFragmentTrainerDetailsBinding.tvRateCount.setText("(" + mCourseModel.getTrainer().getRatingCounter() + ")");
            mFragmentTrainerDetailsBinding.tvSubjects.setText(" - " + mCourseModel.getSubjectName());
            mFragmentTrainerDetailsBinding.tvAge.setText(mCourseModel.getTrainer().getDob());
            mFragmentTrainerDetailsBinding.tvGender.setText(mCourseModel.getTrainer().getGender());

            getBaseActivity().showLoading();
            mViewModel.getResourceData(mCourseModel.getTrainerId());

            mFragmentTrainerDetailsBinding.tvTravel.setText("");
            mFragmentTrainerDetailsBinding.tvExp.setText("");
            mFragmentTrainerDetailsBinding.tvAbout.setText("");
            mFragmentTrainerDetailsBinding.tvAvailability.setText("");
            mFragmentTrainerDetailsBinding.tvCourse.setText("");
            mFragmentTrainerDetailsBinding.tvResource.setText("");
            mFragmentTrainerDetailsBinding.tvLanguages.setText("");

            if (mCourseModel.getTrainer().getLocation().getStreetAddress() != null) {
                mFragmentTrainerDetailsBinding.tvLocationTwo.setText(mCourseModel.getTrainer().getLocation().getStreetAddress());
            } else {
                mFragmentTrainerDetailsBinding.tvLocationTwo.setText("");
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mFragmentTrainerDetailsBinding.tvAmountHour.setText(Html.fromHtml(getString(R.string.per_hr_price, mCourseModel.getPrice() + ""), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mFragmentTrainerDetailsBinding.tvAmountHour.setText(Html.fromHtml(getString(R.string.per_hr_price, mCourseModel.getPrice() + "")));
            }
        }

        wrapTabIndicatorToTitle(mFragmentTrainerDetailsBinding.tabLayout, 90, 90);

        mFragmentTrainerDetailsBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mFragmentTrainerDetailsBinding.card.setVisibility(View.VISIBLE);
                        mFragmentTrainerDetailsBinding.rvResource.setVisibility(View.GONE);
                        break;
                    case 1:
                        mFragmentTrainerDetailsBinding.card.setVisibility(View.GONE);
                        mFragmentTrainerDetailsBinding.rvResource.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openReview() {
        navController.navigate(R.id.action_trainerDetailsFragment_to_reviewFragment);
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
    public void onSuccessGetResource(ResourceResponse mResourceResponse) {
        getBaseActivity().hideLoading();
        if (mResourceResponse.getIsSuccess()) {
            mMyResourceAdapter.addItems(mResourceResponse.getData().getResult());
        } else {
            Toast.makeText(getContext(), "" + mResourceResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openBook() {
        if(mCourseModel!= null){
            TrainerDetailsFragmentDirections.ActionTrainerDetailsFragmentToTrainerBookingFragment action = TrainerDetailsFragmentDirections.actionTrainerDetailsFragmentToTrainerBookingFragment();
            action.setCourseModel(mCourseModel);
            action.setIsCourse(isCourse);
            navController.navigate(action);
        }
    }
}