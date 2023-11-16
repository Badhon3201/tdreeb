package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.report.ReportCardModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemReportCardBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.reportcard.ReportCardItemViewModel;

import java.util.List;

public class ReportCardAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    OnClickFindCourse mListener;

    private List<ReportCardModel> list;

    public ReportCardAdapter(List<ReportCardModel> list) {
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
        ItemReportCardBinding mBinding = ItemReportCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ReportCardHolder(mBinding);
    }


    public void addItems(List<ReportCardModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(OnClickFindCourse listener) {
        this.mListener = listener;
    }

    public class ReportCardHolder extends BaseViewHolder implements OnClickFindCourse {

        private ItemReportCardBinding mBinding;

        private ReportCardItemViewModel mReportCardItemViewModel;

        public ReportCardHolder(ItemReportCardBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mReportCardItemViewModel = new ReportCardItemViewModel(list.get(position), itemView.getContext());
            mBinding.setViewModel(mReportCardItemViewModel);
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