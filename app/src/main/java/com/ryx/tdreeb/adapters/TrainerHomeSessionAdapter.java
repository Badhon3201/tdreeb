package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.databinding.ItemHomeSessionBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.home.TrainerHomeSessionItemViewModel;

import java.util.List;

public class TrainerHomeSessionAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    MyResourceCallBack mListener;


    private List<SessionModel> list;


    public TrainerHomeSessionAdapter(List<SessionModel> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            if(list.size()>3){
                return 3;
            }else {
                return list.size();
            }
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

        ItemHomeSessionBinding itemMySessionBinding = ItemHomeSessionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new HomeSessionHolder(itemMySessionBinding);
    }


    public void addItems(List<SessionModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(MyResourceCallBack listener) {
        this.mListener = listener;
    }


    public class HomeSessionHolder extends BaseViewHolder implements MyResourceCallBack {

        private ItemHomeSessionBinding mBinding;

        private TrainerHomeSessionItemViewModel mTrainerHomeSessionItemViewModel;

        public HomeSessionHolder(ItemHomeSessionBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mTrainerHomeSessionItemViewModel = new TrainerHomeSessionItemViewModel(list.get(position),this,itemView.getContext());
            mBinding.setViewModel(mTrainerHomeSessionItemViewModel);
            mBinding.executePendingBindings();

        }

        @Override
        public void myResourceFvrt(SessionModel mSessionModel) {
            if (mListener != null) {
                mListener.myResourceFvrt(mSessionModel);
            }
        }
    }
}