package com.ryx.tdreeb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.AddDateTimeModel;
import com.ryx.tdreeb.databinding.ItemAddTimeDateLiveCourseBinding;
import com.ryx.tdreeb.databinding.ItemLiveCourseTimeTwoBinding;
import com.ryx.tdreeb.databinding.LiveCourseDateTimeBinding;
import com.ryx.tdreeb.interfaces.SimpleDialogClick;
import com.ryx.tdreeb.ui.base.BaseViewHolder;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse.LiveCourseItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse.LiveDateAddItemViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.VideoAddItemViewModel;

import java.util.List;

public class AddLiveDateTimeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    public interface DateTimeClick {
        void onClickDate(int pos);

        void onClickTime(int pos);
    }

    SimpleDialogClick mListener;
    VideoAddItemViewModel.VideoFile mListenerTwo;
    DateTimeClick mDateTimeClick;

    private List<AddDateTimeModel> list;
    private boolean isShow = true;

    public AddLiveDateTimeAdapter(List<AddDateTimeModel> list, boolean isShow) {
        this.list = list;
        this.isShow = isShow;
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            if (isShow) {
                return list.size() + 1;
            } else {
                return list.size();
            }
        } else {
            if (isShow) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShow) {
            if (position == list.size()) {
                return VIEW_TYPE_EMPTY;
            } else {
                return VIEW_TYPE_NORMAL;
            }
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            if (isShow) {
                LiveCourseDateTimeBinding inflate = LiveCourseDateTimeBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new AddDateTimeHolder(inflate);
            } else {
                ItemLiveCourseTimeTwoBinding inflate = ItemLiveCourseTimeTwoBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new AddDateTimeHolderTwo(inflate);
            }

        } else {
            ItemAddTimeDateLiveCourseBinding inflate = ItemAddTimeDateLiveCourseBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new AdditionDateTimeHolder(inflate);
        }
    }


    public void addItems(List<AddDateTimeModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setListener(SimpleDialogClick listener) {
        this.mListener = listener;
    }

    public void setListenerTwo(VideoAddItemViewModel.VideoFile listener) {
        this.mListenerTwo = listener;
    }

    public void setListenerDateTime(DateTimeClick mDateTimeClick) {
        this.mDateTimeClick = mDateTimeClick;
    }


    public class AddDateTimeHolder extends BaseViewHolder implements VideoAddItemViewModel.VideoFile, DateTimeClick {

        private LiveCourseDateTimeBinding mBinding;

        private LiveCourseItemViewModel mLiveCourseItemViewModel;

        public AddDateTimeHolder(LiveCourseDateTimeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mLiveCourseItemViewModel = new LiveCourseItemViewModel(list.get(position).getLiveTime(), list.get(position).getLiveDate(), this, isShow,this);
            mBinding.setViewModel(mLiveCourseItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickVideoFile(int pos) {
            if (mListenerTwo != null) {
                mListenerTwo.onClickVideoFile(getAdapterPosition());
            }
        }

        @Override
        public void onClickDate(int pos) {
            if (mDateTimeClick != null) {
                mDateTimeClick.onClickDate(getAdapterPosition());
            }
        }

        @Override
        public void onClickTime(int pos) {
            if (mDateTimeClick != null) {
                mDateTimeClick.onClickTime(getAdapterPosition());
            }
        }
    }

    public class AddDateTimeHolderTwo extends BaseViewHolder implements VideoAddItemViewModel.VideoFile {

        private ItemLiveCourseTimeTwoBinding mBinding;

        private LiveCourseItemViewModel mLiveCourseItemViewModel;

        public AddDateTimeHolderTwo(ItemLiveCourseTimeTwoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mLiveCourseItemViewModel = new LiveCourseItemViewModel(list.get(position).getLiveTime(), list.get(position).getLiveDate(), this, isShow,null);
            mBinding.setViewModel(mLiveCourseItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickVideoFile(int pos) {
            if (mListenerTwo != null) {
                mListenerTwo.onClickVideoFile(getAdapterPosition());
            }
        }
    }

    public class AdditionDateTimeHolder extends BaseViewHolder implements SimpleDialogClick {

        private ItemAddTimeDateLiveCourseBinding mBinding;

        private LiveDateAddItemViewModel mLiveDateAddItemViewModel;

        public AdditionDateTimeHolder(ItemAddTimeDateLiveCourseBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mLiveDateAddItemViewModel = new LiveDateAddItemViewModel("", this);
            mBinding.setViewModel(mLiveDateAddItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClose() {
            if (mListener != null) {
                mListener.onClose();
            }
        }
    }
}