package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.FindCourseAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.data.model.db.ImgTextModel;
import com.ryx.tdreeb.databinding.FragmentFindCourseBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.GridSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FindCourseFragment extends BaseFragment<FragmentFindCourseBinding, FindCourseViewModel> implements FindCourseNavigator {

    FragmentFindCourseBinding mFragmentFindCourseBinding;
    private NavController navController;
    private SubjectModel subjectName;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    @Inject
    FindCourseAdapter mFindCourseAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    DataManager dataManager;

    public FindCourseFragment() {
        // Required empty public constructor
    }

    public static FindCourseFragment newInstance() {
        FindCourseFragment fragment = new FindCourseFragment();
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
        return R.layout.fragment_find_course;
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
        mFragmentFindCourseBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentFindCourseBinding.getRoot());
        mGridLayoutManager.setSpanCount(4);
        mFragmentFindCourseBinding.rvCourse.setLayoutManager(mGridLayoutManager);
        int spanCount = 4;
        int spacing = 10;
        boolean includeEdge = true;
        mFragmentFindCourseBinding.rvCourse.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mFragmentFindCourseBinding.rvCourse.setAdapter(mFindCourseAdapter);
        mFindCourseAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {

            }

            @Override
            public void onClickItemModel(SubjectModel subjectName) {
                FindCourseFragment.this.subjectName = subjectName;
                getBaseActivity().hideKeyboard();
                if(!subjectName.getSubSubject().isEmpty()){
                    openSubSubject();
                }else {
                    openTrainerList();
                }

            }
        });
        mFragmentFindCourseBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mFindCourseAdapter.getFilter().filter(s.toString());
            }
        });

        tvToolBarTitle = mFragmentFindCourseBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentFindCourseBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentFindCourseBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.find_course));
//        backImg.setImageResource(R.drawable.ic_arrow_left);
//        backImg.setOnClickListener(v -> navController.popBackStack());
        backImg.setPadding(10,10,10,10);
        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        getBaseActivity().showLoading();
        mViewModel.getSubject();
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSubSubject() {
        FindCourseFragmentDirections.ActionFindCourseFragmentToSubCategoryFragment action = FindCourseFragmentDirections.actionFindCourseFragmentToSubCategoryFragment();
        action.setSubjectName(subjectName);
        action.setIsCourse(true);
        navController.navigate(action);
    }

    @Override
    public void openTrainerList() {
        FindCourseFragmentDirections.ActionFindCourseFragmentToTrainerListFragment action = FindCourseFragmentDirections.actionFindCourseFragmentToTrainerListFragment();
        action.setSubjectName(subjectName);
        action.setIsCourse(true);
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
    }
}