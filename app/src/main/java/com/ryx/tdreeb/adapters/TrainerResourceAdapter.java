package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.databinding.ItemTrainerResourceBinding;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.ResourceTrainerItemViewModel;

import java.io.File;
import java.util.List;

public class TrainerResourceAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    AddResourceCallBack mListener;

    private List<ResourceModel> list;

    public TrainerResourceAdapter(List<ResourceModel> list) {
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

        ItemTrainerResourceBinding itemTrainerResourceBinding =
                ItemTrainerResourceBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new TrainerResourceHolder(itemTrainerResourceBinding);
    }


    public void addItems(List<ResourceModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AddResourceCallBack listener) {
        this.mListener = listener;
    }


    public class TrainerResourceHolder extends BaseViewHolder implements AddResourceCallBack {

        private ItemTrainerResourceBinding mBinding;

        private ResourceTrainerItemViewModel mResourceTrainerItemViewModel;

        public TrainerResourceHolder(ItemTrainerResourceBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mResourceTrainerItemViewModel = new ResourceTrainerItemViewModel(list.get(position),
                    this);
            mBinding.setViewModel(mResourceTrainerItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickFileRemoveItem(int pos, File file) {

        }

        @Override
        public void onClickEdit(ResourceModel mResourceModel) {
            if (mListener != null) {
                mListener.onClickEdit(mResourceModel);
            }
        }

        @Override
        public void onClickDelete(ResourceModel mResourceModel) {
            if (mListener != null) {
                mListener.onClickDelete(mResourceModel);
            }
        }
    }
}