package com.ryx.tdreeb.ui.dialogs.choosekid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ChooseKidAdapter;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.FragmentChooseKidsBinding;
import com.ryx.tdreeb.di.component.DaggerDialogComponent;
import com.ryx.tdreeb.di.component.DialogComponent;
import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

public class ChooseKidsFragment extends BaseDialog implements ChooseKidsNavigator {

    private static final String TAG = "ChooseKidsFragment";

    FragmentChooseKidsBinding mFragmentChooseKidsBinding;
    @Inject
    ChooseKidAdapter mChooseKidAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ChooseKidsViewModel mChooseKidsViewModel;

    ContinueBtn mContinueBtn;

    private List<ChildenModel> listChildrenModel;
    private ChildenModel childenModel;

    private NavController navController;

    public ChooseKidsFragment() {
        // Required empty public constructor
    }

    public static ChooseKidsFragment newInstance() {
        ChooseKidsFragment fragment = new ChooseKidsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentChooseKidsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_kids, container, false);
        View view = mFragmentChooseKidsBinding.getRoot();
        performDependencyInjection(getBuildComponent());
        mFragmentChooseKidsBinding.setViewModel(mChooseKidsViewModel);
        mChooseKidsViewModel.setNavigator(this);
        setUp();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //navController = Navigation.findNavController(view);
    }

    private void setUp() {
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentChooseKidsBinding.rvKids.setLayoutManager(mLinearLayoutManager);
        mFragmentChooseKidsBinding.rvKids.addItemDecoration(new EqualSpacingItemDecoration(30));
        mFragmentChooseKidsBinding.rvKids.setItemAnimator(new DefaultItemAnimator());
        mFragmentChooseKidsBinding.rvKids.setAdapter(mChooseKidAdapter);
        mChooseKidAdapter.setListener(new ChildrenCallBack() {
            @Override
            public void onClickItem() {

            }

            @Override
            public void onClickItemView(ChildenModel childenModel) {
                ChooseKidsFragment.this.childenModel = childenModel;
            }
        });
        if (listChildrenModel != null) {
            mChooseKidAdapter.addItems(listChildrenModel);
        }
    }

    public void setCallBack(ContinueBtn mCallBack) {
        this.mContinueBtn = mCallBack;
    }

    public void setData(List<ChildenModel> listChildrenModel) {
        this.listChildrenModel = listChildrenModel;
    }


    private DialogComponent getBuildComponent() {
        return DaggerDialogComponent.builder()
                .appComponent(((MvvmApp) (getContext().getApplicationContext())).appComponent)
                .dialogModule(new DialogModule(this))
                .build();
    }

    private void performDependencyInjection(DialogComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void close() {
        dismiss();
    }

    @Override
    public void onContinueBtn() {
        if (mContinueBtn != null && childenModel != null) {
            mContinueBtn.clickContinueBtn(childenModel);
            dismiss();
        } else {
            Toast.makeText(getContext(), "Select Children", Toast.LENGTH_SHORT).show();
        }
    }

    public interface ContinueBtn {
        void clickContinueBtn(ChildenModel childenModel);
    }
}