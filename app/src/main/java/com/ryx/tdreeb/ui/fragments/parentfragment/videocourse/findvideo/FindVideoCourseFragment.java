package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.FindVideoCourseAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.databinding.FragmentFindVideoCourseBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.GridSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseFragmentDirections;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;

import javax.inject.Inject;

public class FindVideoCourseFragment extends BaseFragment<FragmentFindVideoCourseBinding,
        FindVideoCourseViewModel> implements FindVideoCourseNavigator {

    FragmentFindVideoCourseBinding mFragmentFindVideoCourseBinding;
    @Inject
    FindVideoCourseAdapter mFindVideoCourseAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    DataManager dataManager;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;
    private SubjectModel subjectName;

    private NavController navController;

    public FindVideoCourseFragment() {
        // Required empty public constructor
    }

    public static FindVideoCourseFragment newInstance() {
        FindVideoCourseFragment fragment = new FindVideoCourseFragment();
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
        return R.layout.fragment_find_video_course;
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
        mFragmentFindVideoCourseBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentFindVideoCourseBinding.getRoot());

        tvToolBarTitle = mFragmentFindVideoCourseBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentFindVideoCourseBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentFindVideoCourseBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.video_courses));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        backImg.setPadding(10,10,10,10);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);


        mGridLayoutManager.setSpanCount(4);
        mFragmentFindVideoCourseBinding.rvVideoCourse.setLayoutManager(mGridLayoutManager);
        int spanCount = 4;
        int spacing = 10;
        boolean includeEdge = true;
        mFragmentFindVideoCourseBinding.rvVideoCourse.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mFragmentFindVideoCourseBinding.rvVideoCourse.setAdapter(mFindVideoCourseAdapter);
        mFindVideoCourseAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {
            }
            @Override
            public void onClickItemModel(SubjectModel subjectName) {
                FindVideoCourseFragment.this.subjectName = subjectName;
                if(!subjectName.getSubSubject().isEmpty()){
                    onSubSection();
                }else{
                    onVideoList();
                }
                getBaseActivity().hideKeyboard();
            }
        });

        mFragmentFindVideoCourseBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mFindVideoCourseAdapter.getFilter().filter(s.toString());
            }
        });

        getBaseActivity().showLoading();
        mViewModel.getVideoCourseCourses("");
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onSubSection() {
        FindVideoCourseFragmentDirections.ActionFindVideoCourseFragmentToSubCategoryFragment action = FindVideoCourseFragmentDirections.actionFindVideoCourseFragmentToSubCategoryFragment();
        action.setSubjectName(subjectName);
        action.setIsCourse(false);
        navController.navigate(action);
    }

    @Override
    public void onVideoList() {
        FindVideoCourseFragmentDirections.ActionFindVideoCourseFragmentToVideoListFragment action = FindVideoCourseFragmentDirections.actionFindVideoCourseFragmentToVideoListFragment();
        action.setSubjectName(subjectName);
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
    public void onSuccessVideoCourse(SubjectResponse allVideoCourseResponse) {
        getBaseActivity().hideLoading();
        if (allVideoCourseResponse.getIsSuccess()) {
            mFindVideoCourseAdapter.addItems(allVideoCourseResponse.getData().getSubjects());
        } else {
            Toast.makeText(getContext(), allVideoCourseResponse.getMessage(), Toast.LENGTH_LONG);
        }
    }
}