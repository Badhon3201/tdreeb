package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.databinding.ItemUploadResourceBinding;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource.ResourceUploadItemViewModel;

import java.io.File;
import java.util.List;

public class ResourceFileUploadAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    AddResourceCallBack mListener;

    private List<File> list;

    public ResourceFileUploadAdapter(List<File> list) {
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
        ItemUploadResourceBinding mBinding = ItemUploadResourceBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ResourceFileUploadHolder(mBinding);
    }


    public void addItems(List<File> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AddResourceCallBack listener) {
        this.mListener = listener;
    }

    public class ResourceFileUploadHolder extends BaseViewHolder implements AddResourceCallBack {

        private ItemUploadResourceBinding mBinding;

        private ResourceUploadItemViewModel mResourceUploadItemViewModel;

        public ResourceFileUploadHolder(ItemUploadResourceBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mResourceUploadItemViewModel = new ResourceUploadItemViewModel(list.get(position), position, this);
            mBinding.setViewModel(mResourceUploadItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickFileRemoveItem(int pos, File file) {
            if (mListener != null) {
                mListener.onClickFileRemoveItem(pos, file);
            }
        }

        @Override
        public void onClickEdit(ResourceModel mResourceModel) {

        }

        @Override
        public void onClickDelete(ResourceModel mResourceModel) {

        }
    }
}