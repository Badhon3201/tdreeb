package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.databinding.ItemTrainerNotificationBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.notification.NotificationTrainerItemViewModel;

import java.util.List;

public class TrainerNotificationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;


    private List<String> activeRequestModelList;


    public TrainerNotificationAdapter(List<String> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
    }


    @Override
    public int getItemCount() {
        if (activeRequestModelList != null && activeRequestModelList.size() > 0) {
            return activeRequestModelList.size();
        } else {
            return 3;
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

        ItemTrainerNotificationBinding itemTrainerNotificationBinding = ItemTrainerNotificationBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new TrainerNotificationHolder(itemTrainerNotificationBinding);
    }


    public void addItems(List<String> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
        notifyDataSetChanged();
    }

    public void clearItems() {
        activeRequestModelList.clear();
    }


    public class TrainerNotificationHolder extends BaseViewHolder {

        private ItemTrainerNotificationBinding mBinding;

        private NotificationTrainerItemViewModel mNotificationTrainerItemViewModel;

        public TrainerNotificationHolder(ItemTrainerNotificationBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mNotificationTrainerItemViewModel = new NotificationTrainerItemViewModel("", "");
            mBinding.setViewModel(mNotificationTrainerItemViewModel);
            mBinding.executePendingBindings();
        }
    }
}