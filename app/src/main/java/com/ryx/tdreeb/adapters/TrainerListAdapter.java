package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemTrainerListBinding;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.CoursesCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist.TrainerListItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrainerListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    CoursesCallBack mListener;

    private List<CourseModel> list;
    private List<CourseModel> filteredData;

    public TrainerListAdapter(List<CourseModel> list) {
        this.list = list;
        this.filteredData = list;
    }


    @Override
    public int getItemCount() {
        if (filteredData != null && filteredData.size() > 0) {
            return filteredData.size();
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
        ItemTrainerListBinding mBinding = ItemTrainerListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new TrainerListHolder(mBinding);
    }


    public void addItems(List<CourseModel> list) {
        this.list = list;
        this.filteredData = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(CoursesCallBack listener) {
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
                    List<CourseModel> filteredList = new ArrayList<>();
                    for (CourseModel row : list) {
                        if(row.getSubjectName() != null) {
                            if (row.getSubjectName().toLowerCase().contains(charString.toLowerCase()) || row.getPrice().toString().contains(charString)) {
                                filteredList.add(row);
                            }
                        }
                        if(row.getTrainer() != null){
                            if (row.getTrainer().getName().toLowerCase().contains(charString.toLowerCase()) || row.getPrice().toString().contains(charString)) {
                                filteredList.add(row);
                            }
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
                filteredData = (ArrayList<CourseModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class TrainerListHolder extends BaseViewHolder implements CoursesCallBack {

        private ItemTrainerListBinding mBinding;

        private TrainerListItemViewModel mTrainerListItemViewModel;

        public TrainerListHolder(ItemTrainerListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mTrainerListItemViewModel = new TrainerListItemViewModel(filteredData.get(position), this);
            mBinding.setViewModel(mTrainerListItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickCourses(CourseModel mCourseModel) {
            if (mListener != null) {
                mListener.onClickCourses(mCourseModel);
            }
        }
    }
}