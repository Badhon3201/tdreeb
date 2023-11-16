package com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.livecoursesmodel.AddDateTimeModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.interfaces.ChildrenCallBack;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class UpcomingLiveItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> teacherName;

    public final ObservableField<String> subjectName;

    public final ObservableField<String> pice;

    public final ObservableField<String> date;

    public final ObservableField<String> time;

    public final ObservableField<String> remainingTime;

    public final ObservableField<Boolean> isShow;

    public final ObservableField<List<AddDateTimeModel>> list;

    public final LiveCourseModelPass mListener;

    public final LiveCourseModel mLiveCourseModel;

    public UpcomingLiveItemViewModel(LiveCourseModel mLiveCourseModel, LiveCourseModelPass mListener) {
        this.mLiveCourseModel = mLiveCourseModel;
        this.image = new ObservableField<>(mLiveCourseModel.getTrainerResponse().getImage());
        this.mListener = mListener;
        this.teacherName = new ObservableField<>(mLiveCourseModel.getTrainerResponse().getName());
        this.subjectName = new ObservableField<>(" - " + mLiveCourseModel.getCourseSubject());
        if(mLiveCourseModel.getLiveCourseDateRangeRequests() != null && mLiveCourseModel.getLiveCourseDateRangeRequests().size() > 0){
            this.list = new ObservableField<>(mLiveCourseModel.getLiveCourseDateRangeRequests());
            this.isShow = new ObservableField<>(true);
        }else{
            this.list = new ObservableField<>(new ArrayList<>());
            this.isShow = new ObservableField<>(false);
        }

        this.pice = new ObservableField<>("AED " + mLiveCourseModel.getPrice());
        this.date = new ObservableField<>(CommonUtils.dateFormater(mLiveCourseModel.getLiveDate(), "dd/MM/yy", "yyyy-mm-dd'T'hh:mm:ss"));
        this.time = new ObservableField<>(CommonUtils.dateFormater(mLiveCourseModel.getLiveTime(), "hh a", "hh:mm a"));
        this.remainingTime = new ObservableField<>(mLiveCourseModel.getRemainingTime());
    }

    public void onClickItem() {
        if (mListener != null) {
            mListener.onClickLiveCourses(0, mLiveCourseModel);
        }
    }
}