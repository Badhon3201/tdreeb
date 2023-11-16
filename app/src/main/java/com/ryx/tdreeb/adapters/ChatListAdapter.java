package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatHistoryModel;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatReceiverModel;
import com.ryx.tdreeb.databinding.ItemChildrenBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.MyChildItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.ChatCallBack;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    ChatCallBack mListener;

    private List<ChatHistoryModel> list;

    public ChatListAdapter(List<ChatHistoryModel> list) {
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
        return VIEW_TYPE_EMPTY;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChildrenBinding itemChildrenBinding = ItemChildrenBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ChatListViewHolder(itemChildrenBinding);
    }


    public void addItems(List<ChatHistoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(ChatCallBack listener) {
        this.mListener = listener;
    }


    public class ChatListViewHolder extends BaseViewHolder implements ChatCallBack {

        private ItemChildrenBinding mBinding;

        private MyChildItemViewModel mMyChildItemViewModel;

        public ChatListViewHolder(ItemChildrenBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mMyChildItemViewModel = new MyChildItemViewModel(list.get(position).getReceiver(), this);
            mBinding.setViewModel(mMyChildItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickChat(ChatReceiverModel mChatReceiverModel) {
            if(mListener!=null){
                mListener.onClickChat(mChatReceiverModel);
            }
        }
    }
}