package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.adapters.ScheduleTimeAdapter;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.WeekDay;
import com.ryx.tdreeb.interfaces.ScheduleWeek;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.Model.SheduleModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDateItemViewModel {

    public final ObservableField<Integer> pos;

    public final ObservableField<WeekDay> listMutableOptionLiveData;

    public final ScheduleTimeAdapter.AddTimeSchedule addTimeSchedule;

    public final ScheduleWeek mScheduleWeek;


    public ScheduleDateItemViewModel(int pos, WeekDay listMutableOptionLiveData, ScheduleTimeAdapter.AddTimeSchedule addTimeSchedule, ScheduleWeek mScheduleWeek) {
        this.pos = new ObservableField<>(pos);
        this.addTimeSchedule = addTimeSchedule;
        this.mScheduleWeek = mScheduleWeek;
        this.listMutableOptionLiveData = new ObservableField<>(listMutableOptionLiveData);
    }

    public void onClickCheck() {
        if (mScheduleWeek != null) {
            mScheduleWeek.onClickCheck(pos.get());
        }
    }
}