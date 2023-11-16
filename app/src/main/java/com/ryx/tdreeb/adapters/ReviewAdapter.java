package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemReviewBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.review.ReviewItemViewModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    OnClickFindCourse mListener;


    private List<String> activeRequestModelList;


    public ReviewAdapter(List<String> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
    }


    @Override
    public int getItemCount() {
        if (activeRequestModelList != null && activeRequestModelList.size() > 0) {
            return activeRequestModelList.size();
        } else {
            return 3;
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
        ItemReviewBinding mBinding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RevieHolder(mBinding);
    }


    public void addItems(List<String> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
        notifyDataSetChanged();
    }

    public void clearItems() {
        activeRequestModelList.clear();
    }

    public void setListener(OnClickFindCourse listener) {
        this.mListener = listener;
    }

    public class RevieHolder extends BaseViewHolder implements OnClickFindCourse {

        private ItemReviewBinding mBinding;

        private ReviewItemViewModel mReviewItemViewModel;

        public RevieHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mReviewItemViewModel = new ReviewItemViewModel("", "");
            mBinding.setViewModel(mReviewItemViewModel);
            mBinding.executePendingBindings();
        }


        @Override
        public void onClickItem(String subjectName) {
            if (mListener != null) {
                mListener.onClickItem("");
            }
        }

        @Override
        public void onClickItemModel(SubjectModel subjectName) {

        }
    }
}