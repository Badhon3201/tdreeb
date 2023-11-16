package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.ParentAllVideoList.Result;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.VedioCourseFilesModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemFindVideoCourseBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseItemViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.SelectPostionAdapterDataInterface;

import java.util.ArrayList;
import java.util.List;

public class FindVideoCourseAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;
    OnClickFindCourse mListener;
    private List<SubjectModel> list;
    private List<VedioCourseFilesModel> listTwo;
    private List<SubjectModel>filteredData;


    public FindVideoCourseAdapter(List<SubjectModel> list) {
        this.list = list;
        this.filteredData = list;
        this.listTwo = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        if (filteredData != null && filteredData.size() > 0) {
            return filteredData.size();
        } else if (listTwo != null && listTwo.size() > 0) {
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
        ItemFindVideoCourseBinding itemFindVideoCourseBinding = ItemFindVideoCourseBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new FindVideoCourseHolder(itemFindVideoCourseBinding);
    }


    public void addItems(List<SubjectModel> list) {
        this.list = list;
        this.filteredData = list;
        notifyDataSetChanged();
    }

    public void addItemsTwo(List<VedioCourseFilesModel> listTwo) {
        this.listTwo = listTwo;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(OnClickFindCourse listener) {
        this.mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredData = list;
                } else {
                    List<SubjectModel> filteredList = new ArrayList<>();
                    for (SubjectModel row : list) {
                        if (row.getSubjectName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (ArrayList<SubjectModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FindVideoCourseHolder extends BaseViewHolder implements OnClickFindCourse {

        private ItemFindVideoCourseBinding mBinding;

        private FindVideoCourseItemViewModel mFindVideoCourseItemViewModel;

        public FindVideoCourseHolder(ItemFindVideoCourseBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (list.size() > 0) {
                mFindVideoCourseItemViewModel = new FindVideoCourseItemViewModel(filteredData.get(position).getImage()
                        , filteredData.get(position).getSubjectName(),"", this);
                mBinding.setViewModel(mFindVideoCourseItemViewModel);
                mBinding.executePendingBindings();
            } else {
                mFindVideoCourseItemViewModel = new FindVideoCourseItemViewModel(listTwo.get(position).getTitle()
                        , listTwo.get(position).getTitle(),"", this);
                mBinding.setViewModel(mFindVideoCourseItemViewModel);
                mBinding.executePendingBindings();
            }
        }

        @Override
        public void onClickItem(String subjectName) {

        }

        @Override
        public void onClickItemModel(SubjectModel subjectName) {
            if (mListener != null) {
                if (list.size() > 0) {
                    mListener.onClickItemModel(filteredData.get(getAdapterPosition()));
                }
            }
        }
    }
}