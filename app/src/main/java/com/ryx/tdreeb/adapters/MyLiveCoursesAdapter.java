package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.databinding.ItemMyLiveCoursesBinding;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive.MyLiveCourseItemViewModel;

import java.util.List;

public class MyLiveCoursesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private MyResourceCallBack mListener;

    private List<SessionModel> list;


    public MyLiveCoursesAdapter(List<SessionModel> list) {
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

        ItemMyLiveCoursesBinding mBinding = ItemMyLiveCoursesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new MyLiveCoursesHolder(mBinding);
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


    public class MyLiveCoursesHolder extends BaseViewHolder implements MyResourceCallBack {

        private ItemMyLiveCoursesBinding mBinding;

        private MyLiveCourseItemViewModel mMyLiveCourseItemViewModel;

        public MyLiveCoursesHolder(ItemMyLiveCoursesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mMyLiveCourseItemViewModel = new MyLiveCourseItemViewModel(list.get(position),this);
            mBinding.setViewModel(mMyLiveCourseItemViewModel);
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