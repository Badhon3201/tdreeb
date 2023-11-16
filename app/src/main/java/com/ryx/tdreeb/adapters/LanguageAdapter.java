package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemLanguageBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.dialogs.languagedialog.LaguageItemViewModel;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<LanguageModel> list;

    private List<NationalityModel> nationalityList;

    private List<SubjectModel> subjectModelList;

    private List<CurriculumModel> curriculumModelList;

    private List<GradeModel> gradeModelList;

    LanguagClickBack mListener;
    private ListCallBack mListCallBack;

    public LanguageAdapter(List<LanguageModel> list) {
        this.list = list;
        this.nationalityList = new ArrayList<>();
        this.subjectModelList = new ArrayList<>();
        this.curriculumModelList = new ArrayList<>();
        this.gradeModelList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else if (nationalityList != null && nationalityList.size() > 0) {
            return nationalityList.size();
        } else if (subjectModelList != null && subjectModelList.size() > 0) {
            return subjectModelList.size();
        } else if (curriculumModelList != null && curriculumModelList.size() > 0) {
            return curriculumModelList.size();
        }else if (gradeModelList != null && gradeModelList.size() > 0) {
            return gradeModelList.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (mSectionResponseList != null && !mSectionResponseList.isEmpty()) {
//            return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLanguageBinding itemLanguageBinding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LanguageHolder(itemLanguageBinding);
    }

    public void addItems(List<LanguageModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItemsNationality(List<NationalityModel> nationalityList) {
        this.nationalityList = nationalityList;
        notifyDataSetChanged();
    }

    public void addItemsSubject(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
        notifyDataSetChanged();
    }

    public void addItemsCurriculum(List<CurriculumModel> curriculumModelList) {
        this.curriculumModelList = curriculumModelList;
        notifyDataSetChanged();
    }

    public void addItemsGrade(List<GradeModel> gradeModelList) {
        this.gradeModelList = gradeModelList;
        notifyDataSetChanged();
    }


    public void setListener(LanguagClickBack mListener) {
        this.mListener = mListener;
    }

    public void setListener(ListCallBack mListCallBack) {
        this.mListCallBack = mListCallBack;
    }

    public void clearItems() {
        list.clear();
    }


    public class LanguageHolder extends BaseViewHolder implements LanguagClickBack,ListCallBack {

        private ItemLanguageBinding mBinding;

        private LaguageItemViewModel mLaguageItemViewModel;

        public LanguageHolder(ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (list.size() > 0) {
                final LanguageModel mLanguageModel = list.get(position);
                mLaguageItemViewModel = new LaguageItemViewModel(mLanguageModel, this);
                mBinding.setViewModel(mLaguageItemViewModel);
                mBinding.executePendingBindings();
            } else if(nationalityList.size()>0) {
                final NationalityModel mNationalityModel = nationalityList.get(position);
                mLaguageItemViewModel = new LaguageItemViewModel(mNationalityModel, this);
                mBinding.setViewModel(mLaguageItemViewModel);
                mBinding.executePendingBindings();
            } else if(subjectModelList.size()>0){
                final SubjectModel mSubjectModel = subjectModelList.get(position);
                mLaguageItemViewModel = new LaguageItemViewModel(mSubjectModel, this);
                mBinding.setViewModel(mLaguageItemViewModel);
                mBinding.executePendingBindings();
            }
            else if(curriculumModelList.size()>0){
                final CurriculumModel mCurriculumModel = curriculumModelList.get(position);
                mLaguageItemViewModel = new LaguageItemViewModel(mCurriculumModel, this);
                mBinding.setViewModel(mLaguageItemViewModel);
                mBinding.executePendingBindings();
            }
            else if(gradeModelList.size()>0){
                final GradeModel mGradeModel = gradeModelList.get(position);
                mLaguageItemViewModel = new LaguageItemViewModel(mGradeModel, this);
                mBinding.setViewModel(mLaguageItemViewModel);
                mBinding.executePendingBindings();
            }

        }


        @Override
        public void onClickItem(LanguageModel mLanguageModel) {
            if (mListener != null) {
                if (mLanguageModel != null) {
                    mListener.onClickItem(mLanguageModel);
                }
            }
        }

        @Override
        public void onClickItemNationality(NationalityModel mNationalityModel) {
            if (mListener != null) {
                if (mNationalityModel != null) {
                    mListener.onClickItemNationality(mNationalityModel);
                }
            }
        }

        @Override
        public void callBackSubject(SubjectModel mSubjectModel) {
            if (mListCallBack != null) {
                if (mSubjectModel != null) {
                    mListCallBack.callBackSubject(mSubjectModel);
                }
            }
        }

        @Override
        public void callCurriculum(CurriculumModel mCurriculumModel) {
            if (mListCallBack != null) {
                if (mCurriculumModel != null) {
                    mListCallBack.callCurriculum(mCurriculumModel);
                }
            }
        }

        @Override
        public void callGrade(GradeModel mGradeModel) {
            if (mListCallBack != null) {
                if (mGradeModel != null) {
                    mListCallBack.callGrade(mGradeModel);
                }
            }
        }
    }
}