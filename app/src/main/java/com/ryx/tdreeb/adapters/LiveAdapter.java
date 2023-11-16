package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.databinding.ItemLiveCourseBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.LiveCourseCallBack;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.LiveCourseItemViewModel;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    LiveCourseCallBack mListener;


    private List<LiveCourseModel> list;


    public LiveAdapter(List<LiveCourseModel> list) {
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

        ItemLiveCourseBinding itemLiveCourseBinding = ItemLiveCourseBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveHolder(itemLiveCourseBinding);
    }


    public void addItems(List<LiveCourseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(LiveCourseCallBack listener) {
        this.mListener = listener;
    }


    public class LiveHolder extends BaseViewHolder implements LiveCourseCallBack {

        private ItemLiveCourseBinding mBinding;

        private LiveCourseItemViewModel mLiveCourseItemViewModel;

        public LiveHolder(ItemLiveCourseBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mLiveCourseItemViewModel = new LiveCourseItemViewModel(list.get(position),this);
            mBinding.setViewModel(mLiveCourseItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickEdit(LiveCourseModel mLiveCourseModel) {
            if (mListener != null) {
                mListener.onClickEdit(mLiveCourseModel);
            }
        }

        @Override
        public void onClickDelete(LiveCourseModel mLiveCourseModel) {

        }
    }
}