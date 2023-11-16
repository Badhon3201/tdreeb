package com.ryx.tdreeb.adapters.videohome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.allvideocourse.AllVideoCourseResult;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.databinding.VideoContainItemBinding;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.MyVideoHomeContainItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.AddCourseCallBack;

import java.util.List;

public class VideoHomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<AllVideoCourseResult> list;

    LiveCourseModelPass mListener;

    public VideoHomeAdapter(List<AllVideoCourseResult> list) {
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

        VideoContainItemBinding item = VideoContainItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new VideoHomeContainHolder(item);
    }


    public void addItems(List<AllVideoCourseResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(LiveCourseModelPass listener) {
        this.mListener = listener;
    }


    public class VideoHomeContainHolder extends BaseViewHolder implements LiveCourseModelPass {

        private VideoContainItemBinding mBinding;

        private MyVideoHomeContainItemViewModel mMyVideoHomeContainItemViewModel;

        public VideoHomeContainHolder(VideoContainItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mMyVideoHomeContainItemViewModel = new MyVideoHomeContainItemViewModel(list.get(position),this);
            mBinding.setViewModel(mMyVideoHomeContainItemViewModel);
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