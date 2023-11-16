package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatMassageModel;
import com.ryx.tdreeb.databinding.ItemReceiveMezBinding;
import com.ryx.tdreeb.databinding.ItemSendMezBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails.ChatDetailsItemViewModel;

import java.util.List;

public class ChatDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_ITS_ME = 0;

    public static final int VIEW_TYPE_ITS_YOU = 1;

    ChildrenCallBack mListener;


    private List<ChatMassageModel> list;


    public ChatDetailsAdapter(List<ChatMassageModel> list) {
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
        if (list.get(position).getIsMe()) {
            return VIEW_TYPE_ITS_ME;
        } else {
            return VIEW_TYPE_ITS_YOU;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITS_ME) {
            ItemSendMezBinding itemSendMezBinding = ItemSendMezBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new ItsMeHolder(itemSendMezBinding);
        } else {
            ItemReceiveMezBinding itemReceiveMezBinding = ItemReceiveMezBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new ItsYouHolder(itemReceiveMezBinding);
        }
    }


    public void addItems(List<ChatMassageModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(ChildrenCallBack listener) {
        this.mListener = listener;
    }


    public class ItsMeHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemSendMezBinding mBinding;

        private ChatDetailsItemViewModel mChatDetailsItemViewModel;

        public ItsMeHolder(ItemSendMezBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (position != 0) {
                if (list.get(position).getIsMe() == list.get(position - 1).getIsMe()) {
                    mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", true);
                    mBinding.setViewModel(mChatDetailsItemViewModel);
                    mBinding.executePendingBindings();
                } else {
                    mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", false);
                    mBinding.setViewModel(mChatDetailsItemViewModel);
                    mBinding.executePendingBindings();
                }
            } else {
                mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", false);
                mBinding.setViewModel(mChatDetailsItemViewModel);
                mBinding.executePendingBindings();
            }
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

    public class ItsYouHolder extends BaseViewHolder implements ChildrenCallBack {

        private ItemReceiveMezBinding mBinding;

        private ChatDetailsItemViewModel mChatDetailsItemViewModel;

        public ItsYouHolder(ItemReceiveMezBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (position != 0) {
                if (list.get(position).getIsMe() == list.get(position - 1).getIsMe()) {
                    mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", true);
                    mBinding.setViewModel(mChatDetailsItemViewModel);
                    mBinding.executePendingBindings();
                } else {
                    mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", false);
                    mBinding.setViewModel(mChatDetailsItemViewModel);
                    mBinding.executePendingBindings();
                }
            } else {
                mChatDetailsItemViewModel = new ChatDetailsItemViewModel(list.get(position), "", false);
                mBinding.setViewModel(mChatDetailsItemViewModel);
                mBinding.executePendingBindings();
            }
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