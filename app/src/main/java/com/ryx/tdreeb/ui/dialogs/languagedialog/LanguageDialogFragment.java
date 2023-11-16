package com.ryx.tdreeb.ui.dialogs.languagedialog;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.LanguageAdapter;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.databinding.FragmentLanguageDialogBinding;
import com.ryx.tdreeb.di.component.DaggerDialogComponent;
import com.ryx.tdreeb.di.component.DialogComponent;
import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseDialog;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LanguageDialogFragment extends BaseDialog implements LanguageDialogNavigator {

    private static final String TAG = "LanguageDialogFragment";

    FragmentLanguageDialogBinding mFragmentLanguageDialogBinding;

    @Inject
    LanguageDialogViewModel mLanguageDialogViewModel;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    LanguageAdapter mLanguageAdapter;

    private List<LanguageModel> modelArrayList = new ArrayList<>();
    String title = "";
    LanguagClickBack mCallBack;

    private List<NationalityModel> mNationalityModelList = new ArrayList<>();

    public LanguageDialogFragment() {
        // Required empty public constructor
    }

    public static LanguageDialogFragment newInstance() {
        LanguageDialogFragment fragment = new LanguageDialogFragment();
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

        mFragmentLanguageDialogBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_language_dialog, container, false);
        View view = mFragmentLanguageDialogBinding.getRoot();
        performDependencyInjection(getBuildComponent());
        mFragmentLanguageDialogBinding.setViewModel(mLanguageDialogViewModel);
        mLanguageDialogViewModel.setNavigator(this);
        setUp();
        return view;
    }

    private void setUp() {
        if(modelArrayList.size()>0){
            mFragmentLanguageDialogBinding.tvTitle.setText(title);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mFragmentLanguageDialogBinding.rv.setLayoutManager(mLinearLayoutManager);
            mFragmentLanguageDialogBinding.rv.addItemDecoration(new EqualSpacingItemDecoration(2));
            mFragmentLanguageDialogBinding.rv.setItemAnimator(new DefaultItemAnimator());
            mFragmentLanguageDialogBinding.rv.setAdapter(mLanguageAdapter);
            mLanguageAdapter.addItems(modelArrayList);
            mLanguageAdapter.setListener(new LanguagClickBack() {
                @Override
                public void onClickItem(LanguageModel mLanguageModel) {
                    if (mCallBack != null) {
                        mCallBack.onClickItem(mLanguageModel);
                    }
                    dismiss();
                }
                @Override
                public void onClickItemNationality(NationalityModel mNationalityModel) {

                }
            });
        }else{
            mFragmentLanguageDialogBinding.tvTitle.setText(title);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mFragmentLanguageDialogBinding.rv.setLayoutManager(mLinearLayoutManager);
            mFragmentLanguageDialogBinding.rv.addItemDecoration(new EqualSpacingItemDecoration(2));
            mFragmentLanguageDialogBinding.rv.setItemAnimator(new DefaultItemAnimator());
            mFragmentLanguageDialogBinding.rv.setAdapter(mLanguageAdapter);
            mLanguageAdapter.addItemsNationality(mNationalityModelList);
            mLanguageAdapter.setListener(new LanguagClickBack() {
                @Override
                public void onClickItem(LanguageModel mLanguageModel) {

                }
                @Override
                public void onClickItemNationality(NationalityModel mNationalityModel) {
                    if (mCallBack != null) {
                        mCallBack.onClickItemNationality(mNationalityModel);
                    }
                    dismiss();
                }
            });
        }

    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void setData(List<LanguageModel> modelArrayList, String title) {
        this.title = title;
        this.modelArrayList = modelArrayList;
    }

    public void setDataNationality(List<NationalityModel> mNationalityModelList, String title) {
        this.title = title;
        this.mNationalityModelList = mNationalityModelList;
    }

    public void setCallBack(LanguagClickBack mCallBack) {
        this.mCallBack = mCallBack;
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

}