package com.ryx.tdreeb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.databinding.ItemVideoCourseBinding;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo.MyVideoItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyVideoCourseAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    LiveCourseModelPass mListener;

    private List<LiveCourseModel> list;
    private List<LiveCourseModel> filteredData;
    private List<SessionModel> listTwo;


    public MyVideoCourseAdapter(List<LiveCourseModel> list) {
        this.list = list;
        this.filteredData = list;
        this.listTwo = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        if (filteredData != null && filteredData.size() > 0) {
            return filteredData.size();
        }else if (listTwo != null && listTwo.size() > 0) {
            return listTwo.size();
        }
        else {
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
        ItemVideoCourseBinding itemVideoCourseBinding = ItemVideoCourseBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new MyVideoCourseHolder(itemVideoCourseBinding);
    }


    public void addItems(List<LiveCourseModel> list) {
        this.list = list;
        this.filteredData = list;
        notifyDataSetChanged();
    }

    public void addItemsTwo(List<SessionModel> listTwo) {
        this.listTwo = listTwo;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(LiveCourseModelPass listener) {
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
                    List<LiveCourseModel> filteredList = new ArrayList<>();
                    for (LiveCourseModel row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCourseSubject().toLowerCase().contains(charString.toLowerCase()) || row.getCourseTitle().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredData = (ArrayList<LiveCourseModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyVideoCourseHolder extends BaseViewHolder implements LiveCourseModelPass {

        private ItemVideoCourseBinding mBinding;

        private MyVideoItemViewModel mMyVideoItemViewModel;

        public MyVideoCourseHolder(ItemVideoCourseBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if(list.size()>0){
                mMyVideoItemViewModel = new MyVideoItemViewModel(filteredData.get(position), this);
                mBinding.setViewModel(mMyVideoItemViewModel);
                mBinding.executePendingBindings();
            }else {
                mMyVideoItemViewModel = new MyVideoItemViewModel(listTwo.get(position),this);
                mBinding.setViewModel(mMyVideoItemViewModel);
                mBinding.executePendingBindings();
            }
        }

        @Override
        public void onClickLiveCourses(int pos, LiveCourseModel liveCourseModel) {
            if (mListener != null) {
                if(list.size()>0) {
                    mListener.onClickLiveCourses(getAdapterPosition(), liveCourseModel);
                }
                else {
                    mListener.onClickLiveCourses(getAdapterPosition(), listTwo.get(getAdapterPosition()).getLiveorVideoCourse());
                }
            }
        }
    }
}