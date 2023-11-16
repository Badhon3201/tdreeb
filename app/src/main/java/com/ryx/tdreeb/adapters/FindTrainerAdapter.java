package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.TrainingModel.TrainingModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemFindTrainerBinding;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.OnClickFindCourse;
import com.ryx.tdreeb.ui.fragments.parentfragment.findteacher.FindTrainerItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class FindTrainerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    OnClickFindCourse mListener;

    private List<TrainingModel> list;
    private List<TrainingModel>filteredData;


    public FindTrainerAdapter(List<TrainingModel> list) {
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
        ItemFindTrainerBinding itemFindTrainerBinding = ItemFindTrainerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new FindTrainerHolder(itemFindTrainerBinding);
    }


    public void addItems(List<TrainingModel> list) {
        this.list = list;
        this.filteredData = list;
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
                    List<TrainingModel> filteredList = new ArrayList<>();
                    for (TrainingModel row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCourseName().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredData = (ArrayList<TrainingModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FindTrainerHolder extends BaseViewHolder implements OnClickFindCourse {

        private ItemFindTrainerBinding mBinding;

        private FindTrainerItemViewModel mFindTrainerItemViewModel;

        public FindTrainerHolder(ItemFindTrainerBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mFindTrainerItemViewModel = new FindTrainerItemViewModel(filteredData.get(position).getImage(), filteredData.get(position).getCourseName(),this);
            mBinding.setViewModel(mFindTrainerItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickItem(String subjectName) {
            if (mListener != null) {
                mListener.onClickItem(subjectName);
            }
        }

        @Override
        public void onClickItemModel(SubjectModel subjectName) {

        }
    }
}