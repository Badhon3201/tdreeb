package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.ItemAddChildBinding;
import com.ryx.tdreeb.databinding.ItemChildrenBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.AddChildItemViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.MyChildItemViewModel;

import java.util.List;

public class MyChildHomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    ChildrenCallBack mListener;

    private List<ChildenModel> activeRequestModelList;

    public MyChildHomeAdapter(List<ChildenModel> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
    }


    @Override
    public int getItemCount() {
        if (activeRequestModelList != null && activeRequestModelList.size() > 0) {
            return activeRequestModelList.size() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == activeRequestModelList.size()) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            ItemChildrenBinding itemChildrenBinding = ItemChildrenBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new MyChildHomeViewHolder(itemChildrenBinding);
        } else {
            ItemAddChildBinding itemAddChildBinding = ItemAddChildBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new MyChildAddHomeHolder(itemAddChildBinding);
        }
    }


    public void addItems(List<ChildenModel> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
        notifyDataSetChanged();
    }

    public void clearItems() {
        activeRequestModelList.clear();
    }

    public void setListener(ChildrenCallBack listener) {
        this.mListener = listener;
    }


    public class MyChildHomeViewHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemChildrenBinding mBinding;

        private MyChildItemViewModel mMyChildItemViewModel;

        public MyChildHomeViewHolder(ItemChildrenBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mMyChildItemViewModel = new MyChildItemViewModel(activeRequestModelList.get(position), this);
            mBinding.setViewModel(mMyChildItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem() {
//            if (mListener != null) {
//                mListener.onClickItem();
//            }
        }

        @Override
        public void onClickItemView(ChildenModel childenModel) {
            if (mListener != null) {
                mListener.onClickItemView(childenModel);
            }
        }
    }

    public class MyChildAddHomeHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemAddChildBinding mBinding;

        private AddChildItemViewModel mAddChildItemViewModel;

        public MyChildAddHomeHolder(ItemAddChildBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mAddChildItemViewModel = new AddChildItemViewModel("", "", this);
            mBinding.setViewModel(mAddChildItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem() {
            if (mListener != null) {
                mListener.onClickItem();
            }
        }

        @Override
        public void onClickItemView(ChildenModel childenModel) {

        }
    }
}