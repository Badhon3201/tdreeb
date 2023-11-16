package com.ryx.tdreeb.adapters.videohome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.VedioCourseFilesModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemVideoBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseItemViewModel;

import java.util.List;

public class VideoCourseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;
    OnClickFindCourse mListener;
    private List<VedioCourseFilesModel> listTwo;


    public VideoCourseAdapter(List<VedioCourseFilesModel> list) {
        this.listTwo = list;
    }


    @Override
    public int getItemCount() {
       if (listTwo != null && listTwo.size() > 0) {
            return listTwo.size();
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
        ItemVideoBinding item = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new VideoCourseHolder(item);
    }


    public void addItems(List<VedioCourseFilesModel> listTwo) {
        this.listTwo = listTwo;
        notifyDataSetChanged();
    }

    public void clearItems() {
        listTwo.clear();
    }

    public void setListener(OnClickFindCourse listener) {
        this.mListener = listener;
    }

    public class VideoCourseHolder extends BaseViewHolder implements OnClickFindCourse {

        private ItemVideoBinding mBinding;

        private FindVideoCourseItemViewModel mFindVideoCourseItemViewModel;

        public VideoCourseHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mFindVideoCourseItemViewModel = new FindVideoCourseItemViewModel(listTwo.get(position).getTitle()
                    , listTwo.get(position).getTitle(),listTwo.get(position).getFilePath() ,this);
            mBinding.setViewModel(mFindVideoCourseItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem(String subjectName) {
            if (mListener != null) {
                if (listTwo.size() > 0) {
                    mListener.onClickItem(subjectName);
                }
            }
        }

        @Override
        public void onClickItemModel(SubjectModel subjectName) {

        }
    }
}