package com.ryx.tdreeb.ui.fragments.parentfragment.findteacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.FindTrainerAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.TrainingModel.TrainingResponseModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.db.ImgTextModel;
import com.ryx.tdreeb.databinding.FragmentFindTrainerBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.GridSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class FindTrainerFragment extends BaseFragment<FragmentFindTrainerBinding, FindTrainerViewModel> implements FindTrainerNavigator {

    FragmentFindTrainerBinding mFragmentFindTrainerBinding;

    @Inject
    FindTrainerAdapter mFindTrainerAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;
    private SubjectModel subjectName = null;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    public FindTrainerFragment() {
    }

    public static FindTrainerFragment newInstance(String param1, String param2) {
        FindTrainerFragment fragment = new FindTrainerFragment();
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
        return R.layout.fragment_find_trainer;
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
        mFragmentFindTrainerBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentFindTrainerBinding.getRoot());

        tvToolBarTitle = mFragmentFindTrainerBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentFindTrainerBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentFindTrainerBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.find_trainer));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mGridLayoutManager.setSpanCount(4);
        mFragmentFindTrainerBinding.rvTrainer.setLayoutManager(mGridLayoutManager);
        int spanCount = 4;
        int spacing = 10;
        boolean includeEdge = true;
        mFragmentFindTrainerBinding.rvTrainer.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mFragmentFindTrainerBinding.rvTrainer.setAdapter(mFindTrainerAdapter);

        mFindTrainerAdapter.setListener(new OnClickFindCourse() {
            @Override
            public void onClickItem(String subjectName) {
                FindTrainerFragment.this.subjectName = new SubjectModel();
                FindTrainerFragment.this.subjectName.setSubjectName(subjectName);
                openSubSubject();
            }

            @Override
            public void onClickItemModel(SubjectModel subjectName) {

            }
        });

        mFragmentFindTrainerBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFindTrainerAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getBaseActivity().showLoading();
        mViewModel.getAllTraining();
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openSubSubject() {
        FindTrainerFragmentDirections.ActionFindTrainerFragmentToTrainerListFragment action = FindTrainerFragmentDirections.actionFindTrainerFragmentToTrainerListFragment();
        action.setSubjectName(subjectName);
        action.setIsCourse(false);
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
    public void onSuccessTraining(TrainingResponseModel mTrainingResponseModel) {
        getBaseActivity().hideLoading();
        mFindTrainerAdapter.addItems(mTrainingResponseModel.getData().getSubjects());
    }
}