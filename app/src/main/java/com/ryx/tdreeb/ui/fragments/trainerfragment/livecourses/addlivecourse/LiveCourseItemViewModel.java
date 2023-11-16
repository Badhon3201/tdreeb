package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.adapters.AddLiveDateTimeAdapter;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.VideoAddItemViewModel;
import com.ryx.tdreeb.utils.CommonUtils;

public class LiveCourseItemViewModel {

    public final ObservableField<String> courseTime;
    public final ObservableField<String> courseDate;
    public final ObservableField<Boolean> isShow;
    public final VideoAddItemViewModel.VideoFile mOnclikc;
    public final AddLiveDateTimeAdapter.DateTimeClick mDateTimeClick;

    public LiveCourseItemViewModel(String courseTime, String courseDate, VideoAddItemViewModel.VideoFile mOnclikc,boolean isShow,AddLiveDateTimeAdapter.DateTimeClick mDateTimeClick) {
        this.mOnclikc = mOnclikc;
        this.mDateTimeClick = mDateTimeClick;
        this.courseTime = new ObservableField<>(courseTime);
        this.isShow = new ObservableField<>(isShow);
        this.courseDate = new ObservableField<>(CommonUtils.dateFormater(courseDate,"dd MMM, yyyy","yyyy-MM-dd'T'HH:mm:ss"));
    }

    public void onClickClose() {
        if (mOnclikc != null)
            mOnclikc.onClickVideoFile(0);
    }

    public void onClickDate() {
        if (mDateTimeClick != null)
            mDateTimeClick.onClickDate(0);
    }

    public void onClickTime() {
        if (mDateTimeClick != null)
            mDateTimeClick.onClickTime(0);
    }

}
