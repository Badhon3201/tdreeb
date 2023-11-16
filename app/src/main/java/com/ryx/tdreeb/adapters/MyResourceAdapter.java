package com.ryx.tdreeb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavouriteModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.databinding.ItemMyResourceBinding;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.parentfragment.myresource.MyResourceItemViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyResourceAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    AddResourceCallBack mListener;
    MyResourceCallBack mListenerTwo;
    private List<ResourceModel> list;
    private List<ResourceModel> filteredData;
    private List<SessionModel> listTwo;
    private List<FavouriteModel> listThree;

    public MyResourceAdapter(List<ResourceModel> list, List<SessionModel> listTwo) {
        this.list = list;
        this.filteredData = list;
        this.listTwo = listTwo;
        this.listThree = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        if (filteredData != null && filteredData.size() > 0) {
            return filteredData.size();
        } else if (listTwo != null && listTwo.size() > 0) {
            return listTwo.size();
        } else if (listThree != null && listThree.size() > 0) {
            return listThree.size();
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
        ItemMyResourceBinding itemMyResourceBinding = ItemMyResourceBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new MyResourcerHolder(itemMyResourceBinding);
    }


    public void addItems(List<ResourceModel> list) {
        this.list = list;
        this.filteredData = list;
        notifyDataSetChanged();
    }

    public void addItemsTwo(List<SessionModel> listTwo) {
        this.listTwo = listTwo;
        notifyDataSetChanged();
    }

    public void addItemsThree(List<FavouriteModel> listThree) {
        this.listThree = listThree;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(AddResourceCallBack listener) {
        this.mListener = listener;
    }

    public void setListenerTwo(MyResourceCallBack mListenerTwo) {
        this.mListenerTwo = mListenerTwo;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredData = list;
                    Log.e("performFiltering", "performFiltering: "+filteredData.size());
                } else {
                    List<ResourceModel> filteredList = new ArrayList<>();
                    for (ResourceModel row : list) {
                        if(row.getResourceName() != null){
                            if (row.getResourceName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }if(row.getSubject() != null){
                            if(row.getSubject().getSubjectName().toLowerCase().contains(charString.toLowerCase())){
                                filteredList.add(row);
                            }
                        }if(row.getTrainer().getName() != null){
                            if(row.getTrainer().getName().toLowerCase().contains(charString.toLowerCase())){
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
                filteredData = (ArrayList<ResourceModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyResourcerHolder extends BaseViewHolder implements AddResourceCallBack, MyResourceCallBack {

        private ItemMyResourceBinding mBinding;

        private MyResourceItemViewModel mMyResourceItemViewModel;

        public MyResourcerHolder(ItemMyResourceBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (list.size() > 0) {
                mMyResourceItemViewModel = new MyResourceItemViewModel(filteredData.get(position), itemView.getContext(), this, true);
                mBinding.setViewModel(mMyResourceItemViewModel);
                mBinding.executePendingBindings();
            } else if (listTwo.size() > 0) {
                mMyResourceItemViewModel = new MyResourceItemViewModel(listTwo.get(position), itemView.getContext(), this, false);
                mBinding.setViewModel(mMyResourceItemViewModel);
                mBinding.executePendingBindings();
            } else {
                mMyResourceItemViewModel = new MyResourceItemViewModel(listThree.get(position).getTrainerResourcesRequest(), itemView.getContext(), this, true);
                mBinding.setViewModel(mMyResourceItemViewModel);
                mBinding.executePendingBindings();
            }
        }


        @Override
        public void onClickFileRemoveItem(int pos, File file) {

        }

        @Override
        public void onClickEdit(ResourceModel mResourceModel) {
            if (mListener != null) {
                mListener.onClickEdit(mResourceModel);
            }
        }

        @Override
        public void onClickDelete(ResourceModel mResourceModel) {
            if (mListener != null) {
                mResourceModel.setFavorite(!mResourceModel.isFavorite());
                if (list.size() > 0) {
                    list.set(getAdapterPosition(), mResourceModel);
                } else {
                    listThree.get(getAdapterPosition()).setTrainerResourcesRequest(mResourceModel);
                }
                notifyDataSetChanged();
                mListener.onClickDelete(mResourceModel);
            }
        }

        @Override
        public void myResourceFvrt(SessionModel mSessionModel) {
            if (mListenerTwo != null) {
//                mSessionModel.getTrainerResourcesRequest().setFavorite(!mSessionModel.getTrainerResourcesRequest().isFavorite());
//                list.set(getAdapterPosition(),mResourceModel);
//                notifyDataSetChanged();
//                mListenerTwo.myResourceFvrt(mSessionModel);
            }
        }
    }
}