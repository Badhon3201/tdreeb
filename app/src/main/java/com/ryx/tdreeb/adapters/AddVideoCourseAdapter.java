package com.ryx.tdreeb.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.VideoFile;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.databinding.ItemVideoAddBinding;
import com.ryx.tdreeb.databinding.ItemVideoFileAddBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.VideoResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.AdditionVideoItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.VideoAddItemViewModel;

import java.util.List;

public class AddVideoCourseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    VideoResourceCallBack mListener;
    VideoAddItemViewModel.VideoFile mListenerTwo;

    private List<VideoFile> list;


    public AddVideoCourseAdapter(List<VideoFile> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
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
            ItemVideoFileAddBinding itemVideoFileAddBinding = ItemVideoFileAddBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new AddVideoCourseHolder(itemVideoFileAddBinding);
        } else {
            ItemVideoAddBinding itemVideoAddBinding = ItemVideoAddBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new AdditionVideoHolder(itemVideoAddBinding);
        }
    }


    public void addItems(List<VideoFile> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(VideoResourceCallBack listener) {
        this.mListener = listener;
    }

    public void setListenerTwo(VideoAddItemViewModel.VideoFile listener) {
        this.mListenerTwo = listener;
    }


    public class AddVideoCourseHolder extends BaseViewHolder implements VideoResourceCallBack, TextWatcher, VideoAddItemViewModel.VideoFile {

        private ItemVideoFileAddBinding mBinding;

        private VideoAddItemViewModel mVideoAddItemViewModel;

        public AddVideoCourseHolder(ItemVideoFileAddBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (list.get(position).getTitle() != null && list.get(position).getFile().getName() != null) {
                mVideoAddItemViewModel = new VideoAddItemViewModel(list.get(position).getTitle(), list.get(position).getFile().getName(), this, position, this);
            } else {
                mVideoAddItemViewModel = new VideoAddItemViewModel("", "", this, position, this);
            }

            mBinding.setViewModel(mVideoAddItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void addVideo() {
            if (mListener != null) {
                mListener.addVideo();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            list.get(getAdapterPosition()).setTitle(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onClickVideoFile(int pos) {
            if (mListenerTwo != null) {
                mListenerTwo.onClickVideoFile(pos);
            }
        }
    }

    public class AdditionVideoHolder extends BaseViewHolder implements VideoResourceCallBack {

        private ItemVideoAddBinding mBinding;

        private AdditionVideoItemViewModel mAdditionVideoItemViewModel;

        public AdditionVideoHolder(ItemVideoAddBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mAdditionVideoItemViewModel = new AdditionVideoItemViewModel("", "", this);
            mBinding.setViewModel(mAdditionVideoItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void addVideo() {
            if (mListener != null) {
                mListener.addVideo();
            }
        }
    }
}