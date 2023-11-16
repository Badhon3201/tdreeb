package com.ryx.tdreeb.ui.fragments.parentfragment.videocourse;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.allvideocourse.AllVideoCourseResult;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;

import java.util.ArrayList;
import java.util.List;

public class MyVideoHomeContainItemViewModel {
    public final ObservableField<String> name;
    public final ObservableField<List<LiveCourseModel>> list;
    public final AllVideoCourseResult mAllVideoCourseResult;
    public final LiveCourseModelPass mLiveCourseModelPass;


    public MyVideoHomeContainItemViewModel(AllVideoCourseResult mAllVideoCourseResult,LiveCourseModelPass mLiveCourseModelPass) {
        this.mAllVideoCourseResult = mAllVideoCourseResult;
        this.mLiveCourseModelPass = mLiveCourseModelPass;
        this.name = new ObservableField<>(mAllVideoCourseResult.getSubject());
        this.list = new ObservableField<>(mAllVideoCourseResult.getResult());

    }
}
