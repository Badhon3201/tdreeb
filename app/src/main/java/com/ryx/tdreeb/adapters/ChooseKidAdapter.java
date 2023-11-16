package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.ItemChooseKidsBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidItemViewModel;

import java.util.List;

public class ChooseKidAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    ChildrenCallBack mListener;


    private List<ChildenModel> list;


    public ChooseKidAdapter(List<ChildenModel> list) {
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

        ItemChooseKidsBinding itemChooseKidsBinding = ItemChooseKidsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ChooseKidHolder(itemChooseKidsBinding);
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


    public class ChooseKidHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemChooseKidsBinding mBinding;

        private ChooseKidItemViewModel mChooseKidItemViewModel;

        public ChooseKidHolder(ItemChooseKidsBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mChooseKidItemViewModel = new ChooseKidItemViewModel(list.get(position), this);
            mBinding.setViewModel(mChooseKidItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem() {

        }

        @Override
        public void onClickItemView(ChildenModel childenModel) {
            if (mListener != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == getAdapterPosition()) {
                        list.get(i).setSelect(true);
                        list.set(i, list.get(i));
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                mListener.onClickItemView(childenModel);
                notifyDataSetChanged();
            }
        }
    }
}