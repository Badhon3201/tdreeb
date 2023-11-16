package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionDataModel;
import com.ryx.tdreeb.databinding.ItemRequestBinding;
import com.ryx.tdreeb.interfaces.AllRequestCallBack;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept.RequestItemViewModel;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    AllRequestCallBack mListener;

    private List<SessionModel> list;

    private boolean isViewShow = false;

    public RequestAdapter(List<SessionModel> list) {
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

        ItemRequestBinding itemRequestBinding = ItemRequestBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RequestHolder(itemRequestBinding);
    }

    public void addItems(List<SessionModel> list,boolean isViewShow) {
        this.list = list;
        this.isViewShow = isViewShow;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AllRequestCallBack listener) {
        this.mListener = listener;
    }


    public class RequestHolder extends BaseViewHolder implements AllRequestCallBack {

        private ItemRequestBinding mBinding;
        private RequestItemViewModel mRequestItemViewModel;

        public RequestHolder(ItemRequestBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mRequestItemViewModel = new RequestItemViewModel(list.get(position),this, isViewShow);
            mBinding.setViewModel(mRequestItemViewModel);
            mBinding.executePendingBindings();
        }


        @Override
        public void onClickView(SessionModel mSessionModel) {
            if (mListener != null) {
                mListener.onClickView(mSessionModel);
            }
        }

        @Override
        public void onClickAccept(SessionModel mSessionModel) {
            if (mListener != null) {
                mListener.onClickAccept(mSessionModel);
            }
        }

        @Override
        public void onClickCancel(SessionModel mSessionModel) {
            if (mListener != null) {
                mListener.onClickCancel(mSessionModel);
            }
        }
    }
}