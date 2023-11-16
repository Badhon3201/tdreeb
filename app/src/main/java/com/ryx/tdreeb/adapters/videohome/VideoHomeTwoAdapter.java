package com.ryx.tdreeb.adapters.videohome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.databinding.VideoHomeItemBinding;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.MyVideoHomeTwoItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.AddCourseCallBack;

import java.util.List;

public class VideoHomeTwoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<LiveCourseModel> list;

    LiveCourseModelPass mListener;

    public VideoHomeTwoAdapter(List<LiveCourseModel> list) {
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

        VideoHomeItemBinding item = VideoHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new VideoHomeTwoHolder(item);
    }


    public void addItems(List<LiveCourseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(LiveCourseModelPass listener) {
        this.mListener = listener;
    }


    public class VideoHomeTwoHolder extends BaseViewHolder implements LiveCourseModelPass {

        private VideoHomeItemBinding mBinding;

        private MyVideoHomeTwoItemViewModel mMyVideoHomeTwoItemViewModel;

        public VideoHomeTwoHolder(VideoHomeItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mMyVideoHomeTwoItemViewModel = new MyVideoHomeTwoItemViewModel( list.get(position),itemView.getContext(),this);
            mBinding.setViewModel(mMyVideoHomeTwoItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickLiveCourses(int pos, LiveCourseModel liveCourseModel) {
            if(mListener != null){
                mListener.onClickLiveCourses(pos,liveCourseModel);
            }
        }
    }
}