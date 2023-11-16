package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.WeekDay;
import com.ryx.tdreeb.databinding.ItemScheduleBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.ScheduleDateItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleWeekAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    ScheduleWeek mListener;

    ScheduleTimeAdapter.AddTimeSchedule mListenerTwo;

    private List<WeekDay> list;


    public ScheduleWeekAdapter(List<WeekDay> list) {
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

        ItemScheduleBinding itemScheduleBinding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ScheduleWeekHolder(itemScheduleBinding);
    }


    public void addItems(List<WeekDay> activeRequestModelList) {
        this.list = activeRequestModelList;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(ScheduleWeek listener) {
        this.mListener = listener;
    }

    public void setTimeListener(ScheduleTimeAdapter.AddTimeSchedule mListenerTwo) {
        this.mListenerTwo = mListenerTwo;
    }

    public class ScheduleWeekHolder extends BaseViewHolder implements ScheduleWeek {

        private ItemScheduleBinding mBinding;

        private ScheduleDateItemViewModel mScheduleDateItemViewModel;

        public ScheduleWeekHolder(ItemScheduleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mScheduleDateItemViewModel = new ScheduleDateItemViewModel(position, list.get(position), mListenerTwo, this);
            mBinding.setViewModel(mScheduleDateItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickCheck(int pos) {
            if (mListener != null) {
                mListener.onClickCheck(getAdapterPosition());
            }
        }
    }
}