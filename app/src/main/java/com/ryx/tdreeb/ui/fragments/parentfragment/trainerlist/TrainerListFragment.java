package com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.TrainerListAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.FragmentTrainerListBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.CoursesCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.filterbottomsheet.FilterBottomSheetFragment;

import java.util.ArrayList;

import javax.inject.Inject;


public class TrainerListFragment extends BaseFragment<FragmentTrainerListBinding, TrainerListViewModel> implements TrainerListNavigator {

    FragmentTrainerListBinding mFragmentTrainerListBinding;
    private NavController navController;
    private SubjectModel subjectName;
    private boolean isCourse = true;
    private CourseModel mCourseModel;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    @Inject
    TrainerListAdapter mTrainerListAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    DataManager dataManager;

    public TrainerListFragment() {
        // Required empty public constructor
    }

    public static TrainerListFragment newInstance() {
        TrainerListFragment fragment = new TrainerListFragment();
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
        return R.layout.fragment_trainer_list;
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
        mFragmentTrainerListBinding = getViewDataBinding();
        if (getArguments() != null) {
            TrainerListFragmentArgs args = TrainerListFragmentArgs.fromBundle(getArguments());
            subjectName = args.getSubjectName();
            isCourse = args.getIsCourse();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentTrainerListBinding.getRoot());

        tvToolBarTitle = mFragmentTrainerListBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentTrainerListBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentTrainerListBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.find_trainer));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        //mFragmentTrainerListBinding.tvCategory.setText(subjectName);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentTrainerListBinding.rvList.setLayoutManager(mLinearLayoutManager);
        mFragmentTrainerListBinding.rvList.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentTrainerListBinding.rvList.setItemAnimator(new DefaultItemAnimator());
        mFragmentTrainerListBinding.rvList.setAdapter(mTrainerListAdapter);
        mTrainerListAdapter.setListener(new CoursesCallBack() {
            @Override
            public void onClickCourses(CourseModel mCourseModel) {
                TrainerListFragment.this.mCourseModel = mCourseModel;
                openTrainerDetails();
            }
        });

//        mFragmentTrainerListBinding.edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mTrainerListAdapter.getFilter().filter(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        if (isCourse) {
            if(subjectName.getSubSubjectResponse()!=null){
                mFragmentTrainerListBinding.tvCategory.setText(subjectName.getSubjectName());
                mFragmentTrainerListBinding.tvSubCategory.setText(subjectName.getSubSubjectResponse().getSubjectName());
                getBaseActivity().showLoading();
                mViewModel.getCourse(subjectName.getSubSubjectResponse().getSubjectName());
            }else{
                mFragmentTrainerListBinding.tvCategory.setText(subjectName.getSubjectName());
                getBaseActivity().showLoading();
                mViewModel.getCourse(subjectName.getSubjectName());
            }
        } else {
            if(subjectName.getSubSubjectResponse()!=null){
                mFragmentTrainerListBinding.tvCategory.setText(subjectName.getSubjectName());
                mFragmentTrainerListBinding.tvSubCategory.setText(subjectName.getSubSubjectResponse().getSubjectName());
                getBaseActivity().showLoading();
                mViewModel.getTraining(subjectName.getSubSubjectResponse().getSubjectName());
            }else{
                mFragmentTrainerListBinding.tvCategory.setText(subjectName.getSubjectName());
                getBaseActivity().showLoading();
                mViewModel.getTraining(subjectName.getSubjectName());
            }
        }

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openMap() {
        if(subjectName.getSubSubjectResponse()!=null) {
            TrainerListFragmentDirections.ActionTrainerListFragmentToMapFragment action = TrainerListFragmentDirections.actionTrainerListFragmentToMapFragment();
            action.setIsCourse(isCourse);
            action.setSubjectName(subjectName.getSubSubjectResponse().getSubjectName());
            navController.navigate(action);
        }else{
            TrainerListFragmentDirections.ActionTrainerListFragmentToMapFragment action = TrainerListFragmentDirections.actionTrainerListFragmentToMapFragment();
            action.setIsCourse(isCourse);
            action.setSubjectName(subjectName.getSubjectName());
            navController.navigate(action);
        }
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
    public void openTrainerDetails() {
        if (mCourseModel != null) {
            TrainerListFragmentDirections.ActionTrainerListFragmentToTrainerDetailsFragment action = TrainerListFragmentDirections.actionTrainerListFragmentToTrainerDetailsFragment();
            action.setCourseData(mCourseModel);
            action.setIsCourse(isCourse);
            navController.navigate(action);
        }
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
        if (mCoursesResponse.getIsSuccess()) {
            mTrainerListAdapter.addItems(mCoursesResponse.getData().getCourses());
        } else {
            Toast.makeText(getContext(), "" + mCoursesResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}