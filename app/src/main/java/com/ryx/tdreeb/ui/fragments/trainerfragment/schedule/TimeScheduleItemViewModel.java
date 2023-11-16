package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.adapters.ScheduleTimeAdapter;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule__1;


public class TimeScheduleItemViewModel {
    public final ObservableField<Integer> pos;

    public final ObservableField<Integer> position;
    public final ScheduleTimeAdapter.AddTimeSchedule mAddTimeSchedule;
    public final ObservableField<DailySchedule__1> dailySchedule;

    public TimeScheduleItemViewModel(int adapterWeekPos, int pos,
                                     DailySchedule__1 dailySchedule__1,
                                     ScheduleTimeAdapter.AddTimeSchedule mAddTimeSchedule) {
        this.pos = new ObservableField<>(adapterWeekPos);
        this.mAddTimeSchedule = mAddTimeSchedule;
        this.position = new ObservableField<>(pos);
        this.dailySchedule = new ObservableField<>(dailySchedule__1);
    }

    public TimeScheduleItemViewModel(int adapterWeekPos, int pos,
                                     ScheduleTimeAdapter.AddTimeSchedule mAddTimeSchedule) {
        this.pos = new ObservableField<>(adapterWeekPos);
        this.mAddTimeSchedule = mAddTimeSchedule;
        this.position = new ObservableField<>(pos);
        this.dailySchedule = new ObservableField<>(null);
    }

    public void onAdd() {
        if (mAddTimeSchedule != null) {
            mAddTimeSchedule.onClickAdd(pos.get(),position.get());
        }
    }

    public void onClose() {
        if (mAddTimeSchedule != null) {
            mAddTimeSchedule.onClickClose(pos.get(), position.get());
        }
    }

    public void fromTimeClick() {
        if (mAddTimeSchedule != null) {
            mAddTimeSchedule.fromTimerClick(pos.get(), position.get());
        }
    }

    public void toTimerClick() {
        if (mAddTimeSchedule != null) {
            mAddTimeSchedule.toTimerClick(pos.get(),position.get());
        }
    }
}