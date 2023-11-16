package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.databinding.ItemUpcomingLiveCourseBinding;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive.UpcomingLiveItemViewModel;

import java.util.List;

public class UpcomingLiveCourseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    LiveCourseModelPass mListener;

    private List<LiveCourseModel> list;

    public UpcomingLiveCourseAdapter(List<LiveCourseModel> list) {
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

        ItemUpcomingLiveCourseBinding mBinding = ItemUpcomingLiveCourseBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new UpcomingLiveCourseHolder(mBinding);
    }


    public void addItems(List<LiveCourseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(LiveCourseModelPass listener) {
        this.mListener = listener;
    }


    public class UpcomingLiveCourseHolder extends BaseViewHolder implements LiveCourseModelPass {

        private ItemUpcomingLiveCourseBinding mBinding;

        private UpcomingLiveItemViewModel mUpcomingLiveItemViewModel;

        public UpcomingLiveCourseHolder(ItemUpcomingLiveCourseBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mUpcomingLiveItemViewModel = new UpcomingLiveItemViewModel(list.get(position), this);
            mBinding.setViewModel(mUpcomingLiveItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickLiveCourses(int pos, LiveCourseModel liveCourseModel) {
            if (mListener != null) {
                mListener.onClickLiveCourses(getAdapterPosition(),liveCourseModel);
            }
        }
    }
}