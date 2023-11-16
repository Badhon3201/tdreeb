package com.ryx.tdreeb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule__1;
import com.ryx.tdreeb.databinding.ItemScheduleTimeBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.TimeScheduleItemViewModel;

import java.util.List;

public class ScheduleTimeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    AddTimeSchedule mListener;

    private List<DailySchedule__1> list;

    int pos;

    public interface AddTimeSchedule {
        void onClickAdd(int pos, int posTwo);

        void onClickClose(int pos, int posTwo);

        void fromTimerClick(int pos, int posTwo);

        void toTimerClick(int pos, int posTwo);
    }

    public ScheduleTimeAdapter(List<DailySchedule__1> list, int pos) {
        this.list = list;
        this.pos = pos;
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

        ItemScheduleTimeBinding itemScheduleTimeBinding = ItemScheduleTimeBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ScheduleTimeHolder(itemScheduleTimeBinding);
    }


    public void addItems(List<DailySchedule__1> activeRequestModelList) {
        this.list = activeRequestModelList;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AddTimeSchedule listener) {
        this.mListener = listener;
    }

    public class ScheduleTimeHolder extends BaseViewHolder implements AddTimeSchedule {

        private ItemScheduleTimeBinding mBinding;

        private TimeScheduleItemViewModel mTimeScheduleItemViewModel;

        public ScheduleTimeHolder(ItemScheduleTimeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mTimeScheduleItemViewModel = new TimeScheduleItemViewModel(pos, position, list.get(position), this);
            mBinding.setViewModel(mTimeScheduleItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickAdd(int pos, int posTwo) {
            if (mListener != null) {
                mListener.onClickAdd(pos, posTwo);
            }
        }

        @Override
        public void onClickClose(int pos, int posTwo) {
            if (mListener != null) {
                mListener.onClickClose(pos, posTwo);
            }
        }

        @Override
        public void fromTimerClick(int pos, int posTwo) {
            if (mListener != null) {
                mListener.fromTimerClick(pos, posTwo);
            }
        }

        @Override
        public void toTimerClick(int pos, int posTwo) {
            mListener.toTimerClick(pos, posTwo);
        }
    }
}