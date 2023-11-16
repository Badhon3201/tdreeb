package com.ryx.tdreeb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.ItemChildListBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.children.ChildListItemViewModel;

import java.util.List;

public class ChildrenListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    ChildrenCallBack mListener;

    private List<ChildenModel> list;

    public ChildrenListAdapter(List<ChildenModel> list) {
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

        ItemChildListBinding itemChildListBinding = ItemChildListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ChildrenListHolder(itemChildListBinding);
    }


    public void addItems(List<ChildenModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(ChildrenCallBack listener) {
        this.mListener = listener;
    }


    public class ChildrenListHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemChildListBinding mBinding;

        private ChildListItemViewModel mChildListItemViewModel;

        public ChildrenListHolder(ItemChildListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mChildListItemViewModel = new ChildListItemViewModel(list.get(position), this);
            mBinding.setViewModel(mChildListItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem() {

        }

        @Override
        public void onClickItemView(ChildenModel childenModel) {
            if (mListener != null) {
                mListener.onClickItemView(childenModel);
            }
        }
    }
}