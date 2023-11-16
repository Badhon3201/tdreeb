package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.databinding.ItemSubjectListBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.AddCourseCallBack;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.SubjectCourseItemViewModel;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<CourseModel> list;

    AddCourseCallBack mListener;

    public CourseListAdapter(List<CourseModel> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemSubjectListBinding itemSubjectListBinding = ItemSubjectListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CourseListHolder(itemSubjectListBinding);
    }


    public void addItems(List<CourseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AddCourseCallBack listener) {
        this.mListener = listener;
    }


    public class CourseListHolder extends BaseViewHolder implements AddCourseCallBack {

        private ItemSubjectListBinding mBinding;

        private SubjectCourseItemViewModel mSubjectCourseItemViewModel;

        public CourseListHolder(ItemSubjectListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mSubjectCourseItemViewModel = new SubjectCourseItemViewModel(list.get(position),this);
            mBinding.setViewModel(mSubjectCourseItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onCallBack(AddCourseModel addCourseModel, boolean isUpdate) {

        }

        @Override
        public void onCallBackUpdate(CourseModel mCourseModell) {
            if (mListener != null) {
                mListener.onCallBackUpdate(mCourseModell);
            }
        }

        @Override
        public void onCallDelete(CourseModel mCourseModel) {
            if (mListener != null) {
                mListener.onCallDelete(mCourseModel);
            }
        }
    }
}