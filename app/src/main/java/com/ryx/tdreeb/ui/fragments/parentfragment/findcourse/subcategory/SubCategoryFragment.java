package com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.subcategory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.SubCategoryAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.FragmentSubCategoryBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.helper.GridSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseFragmentDirections;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.map.MapFragmentArgs;

import javax.inject.Inject;


public class SubCategoryFragment extends BaseFragment<FragmentSubCategoryBinding, SubCategoryViewModel> implements SubCategoryNavigator {

    FragmentSubCategoryBinding mFragmentSubCategoryBinding;

    @Inject
    DataManager dataManager;
    @Inject
    SubCategoryAdapter mSubCategoryAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    private NavController navController;

    private SubjectModel subjectName;
    private String subName;
    private boolean isCourse = true;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;


    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sub_category;
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
        mFragmentSubCategoryBinding = getViewDataBinding();
        if (getArguments() != null) {
            SubCategoryFragmentArgs args = SubCategoryFragmentArgs.fromBundle(getArguments());
            subjectName = args.getSubjectName();
            isCourse = args.getIsCourse();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentSubCategoryBinding.getRoot());

        tvToolBarTitle = mFragmentSubCategoryBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentSubCategoryBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentSubCategoryBinding.getRoot().findViewById(R.id.profile_image);

        mFragmentSubCategoryBinding.rvSubCetegory.setLayoutManager(mLinearLayoutManager);

        mFragmentSubCategoryBinding.rvSubCetegory.addItemDecoration(new EqualSpacingItemDecoration(20));
        mFragmentSubCategoryBinding.rvSubCetegory.setAdapter(mSubCategoryAdapter);

        mSubCategoryAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {
            }
            @Override
            public void onClickItemModel(SubjectModel subjectName) {
                SubCategoryFragment.this.subjectName.setSubSubjectResponse(subjectName);
                getBaseActivity().hideKeyboard();
                openSubjectView();
            }
        });

        tvToolBarTitle.setText(getString(R.string.sub_category));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);
        if (subjectName != null) {
            mFragmentSubCategoryBinding.tvCatName.setText(subjectName.getSubjectName());
            mSubCategoryAdapter.addItems(subjectName.getSubSubject());
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSubjectView() {
        if(isCourse){
            if(subjectName != null){
                SubCategoryFragmentDirections.ActionSubCategoryFragmentToTrainerListFragment action = SubCategoryFragmentDirections.actionSubCategoryFragmentToTrainerListFragment();
                action.setSubjectName(subjectName);
                action.setIsCourse(isCourse);
                navController.navigate(action);
            }
        }else{
            if(subjectName != null){
                SubCategoryFragmentDirections.ActionSubCategoryFragmentToVideoListFragment action = SubCategoryFragmentDirections.actionSubCategoryFragmentToVideoListFragment();
                action.setSubjectName(subjectName);
                navController.navigate(action);
            }
        }

    }
}