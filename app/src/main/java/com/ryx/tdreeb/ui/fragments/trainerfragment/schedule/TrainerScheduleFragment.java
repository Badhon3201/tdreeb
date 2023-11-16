package com.ryx.tdreeb.ui.fragments.trainerfragment.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ScheduleTimeAdapter.AddTimeSchedule;
import com.ryx.tdreeb.adapters.ScheduleWeekAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule__1;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.ScheduleSubmitModel;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.TrainerScheduleResponse;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.WeekDay;
import com.ryx.tdreeb.databinding.TrainerMyScheduleFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;


public class TrainerScheduleFragment extends BaseFragment<TrainerMyScheduleFragmentBinding,
        TrainerScheduleViewModel> implements TrainerScheduleNavigator {

    ImageView backImg,profileImg;

    List<WeekDay> weekDayList = new ArrayList<>();
    List<WeekDay> weekDayListTwo = new ArrayList<>();
    TrainerMyScheduleFragmentBinding mTrainerMyScheduleFragmentBinding;
    private DailySchedule weeakAndData, weekAndDataTwo;
    private List<ScheduleSubmitModel.DailySchedule> listDirect, listOnline;

    DatePickerDialog.OnDateSetListener date, dateTo;
    DatePickerDialog.OnDateSetListener dateOnline, dateToOnline;

    private ScheduleSubmitModel mScheduleSubmitModelDirectSchedule, mScheduleSubmitOnlineSchedule;

    public TrainerScheduleFragment() {
    }

    @Inject
    DataManager dataManager;
    @Inject
    ScheduleWeekAdapter mScheduleWeekAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    ScheduleWeekAdapter mScheduleWeekOnlineAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManagerOnline;

    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    Calendar myCalendar;

    public static TrainerScheduleFragment newInstance() {
        TrainerScheduleFragment fragment = new TrainerScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.trainer_my_schedule_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mTrainerMyScheduleFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        backImg = mTrainerMyScheduleFragmentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerMyScheduleFragmentBinding.getRoot().findViewById(R.id.profile_image);

        backImg.setOnClickListener(v -> getBaseActivity().openDrawer());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mScheduleSubmitModelDirectSchedule = new ScheduleSubmitModel();
        mScheduleSubmitOnlineSchedule = new ScheduleSubmitModel();
        myCalendar = Calendar.getInstance();
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerMyScheduleFragmentBinding.rvDirectTraining.setLayoutManager(mLinearLayoutManager);
        mTrainerMyScheduleFragmentBinding.rvDirectTraining.addItemDecoration(new EqualSpacingItemDecoration(20));
        mTrainerMyScheduleFragmentBinding.rvDirectTraining.setItemAnimator(new DefaultItemAnimator());
        mTrainerMyScheduleFragmentBinding.rvDirectTraining.setAdapter(mScheduleWeekAdapter);
        mScheduleWeekAdapter.setTimeListener(new AddTimeSchedule() {
            @Override
            public void onClickAdd(int pos, int posTwo) {
                if (weekDayList.get(pos).getDailySchedules().get(posTwo).getFromTime() != null && weekDayList.get(pos).getDailySchedules().get(posTwo).getToTime() != null) {
                    getBaseActivity().showLoading();
                    mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
                } else {
                    Toast.makeText(getContext(), "Select From and to time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickClose(int pos, int posTwo) {
                if (listDirect.get(pos).getTimeData().size() == 1) {
                    listDirect.get(pos).getTimeData().remove(0);
                } else {
                    listDirect.get(pos).getTimeData().remove(posTwo - 1);
                }
                mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
                weekDayList.get(pos).getDailySchedules().remove(posTwo);
                mScheduleWeekAdapter.addItems(weekDayList);
                getBaseActivity().showLoading();
                mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
            }

            @Override
            public void fromTimerClick(int pos, int posTwo) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                    myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    myCalendar.set(Calendar.MINUTE, selectedMinute);
                    String myFormat = "hh:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                    weekDayList.get(pos).getDailySchedules().get(posTwo).setFromTime(sdf.format(myCalendar.getTime()));
                    mScheduleWeekAdapter.addItems(weekDayList);
//                    if(posTwo != 0){
//                        getBaseActivity().showLoading();
//                        mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
//                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time for Direct Schedule");
                mTimePicker.show();
            }

            @Override
            public void toTimerClick(int pos, int posTwo) {
                if (weekDayList.get(pos).getDailySchedules().get(posTwo).getFromTime() != null) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        String myFormat = "hh:mm a";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                        if (CommonUtils.timeDiff(sdf.format(myCalendar.getTime()), weekDayList.get(pos).getDailySchedules().get(posTwo).getFromTime())) {

                            weekDayList.get(pos).getDailySchedules().get(posTwo).setToTime(sdf.format(myCalendar.getTime()));
                            mScheduleWeekAdapter.addItems(weekDayList);
                            ScheduleSubmitModel.TimeDatum timeDatum = new ScheduleSubmitModel.TimeDatum();
                            timeDatum.setTo(sdf.format(myCalendar.getTime()));
                            timeDatum.setFrom(weekDayList.get(pos).getDailySchedules().get(posTwo).getFromTime());
                            timeDatum.setFromTimeStamp("50340000");
                            timeDatum.setToTimeStamp("50340000");
                            if (posTwo != 0) {
                                listDirect.get(pos).getTimeData().set(posTwo - 1, timeDatum);
                                mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
                                getBaseActivity().showLoading();
                                mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
                            } else {
                                listDirect.get(pos).getTimeData().add(timeDatum);
                                mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
                            }
                        } else {
                            Toast.makeText(getContext(), "Select Correct Time", Toast.LENGTH_SHORT).show();
                        }
                    }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time for Direct Schedule");
                    mTimePicker.show();
                } else {
                    Toast.makeText(getContext(), "Select from Time first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mScheduleWeekAdapter.setListener(pos -> {
            listDirect.get(pos).setHoliday(!listDirect.get(pos).isHoliday());
            mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        });


//      TODO:- Online Schedule
        mLinearLayoutManagerOnline.setOrientation(LinearLayoutManager.VERTICAL);
        mTrainerMyScheduleFragmentBinding.rvOnlineTraining.setLayoutManager(mLinearLayoutManagerOnline);
        mTrainerMyScheduleFragmentBinding.rvOnlineTraining.addItemDecoration(new EqualSpacingItemDecoration(20));
        mTrainerMyScheduleFragmentBinding.rvOnlineTraining.setItemAnimator(new DefaultItemAnimator());
        mTrainerMyScheduleFragmentBinding.rvOnlineTraining.setAdapter(mScheduleWeekOnlineAdapter);
        mScheduleWeekOnlineAdapter.setTimeListener(new AddTimeSchedule() {
            @Override
            public void onClickAdd(int pos, int posTwo) {
                if (weekDayListTwo.get(pos).getDailySchedules().get(posTwo).getFromTime() != null && weekDayListTwo.get(pos).getDailySchedules().get(posTwo).getToTime() != null) {
                    getBaseActivity().showLoading();
                    mViewModel.submitData(mScheduleSubmitOnlineSchedule);
                } else {
                    Toast.makeText(getContext(), "Select From and to time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickClose(int pos, int posTwo) {
                if (listOnline.get(pos).getTimeData().size() == 1) {
                    listOnline.get(pos).getTimeData().remove(0);
                } else {
                    listOnline.get(pos).getTimeData().remove(posTwo - 1);
                }
                mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
                weekDayListTwo.get(pos).getDailySchedules().remove(posTwo);
                mScheduleWeekOnlineAdapter.addItems(weekDayListTwo);
                getBaseActivity().showLoading();
                mViewModel.submitData(mScheduleSubmitOnlineSchedule);
            }

            @Override
            public void fromTimerClick(int pos, int posTwo) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                    myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    myCalendar.set(Calendar.MINUTE, selectedMinute);
                    String myFormat = "hh:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                    weekDayListTwo.get(pos).getDailySchedules().get(posTwo).setFromTime(sdf.format(myCalendar.getTime()));
                    mScheduleWeekOnlineAdapter.addItems(weekDayListTwo);
//                    if (posTwo != 0) {
//                        getBaseActivity().showLoading();
//                        mViewModel.submitData(mScheduleSubmitOnlineSchedule);
//                    }

                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time for Online Schedule");
                mTimePicker.show();
            }

            @Override
            public void toTimerClick(int pos, int posTwo) {
                if (weekDayListTwo.get(pos).getDailySchedules().get(posTwo).getFromTime() != null) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        String myFormat = "hh:mm a";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                        if (CommonUtils.timeDiff(sdf.format(myCalendar.getTime()), weekDayListTwo.get(pos).getDailySchedules().get(posTwo).getFromTime())) {
                            weekDayListTwo.get(pos).getDailySchedules().get(posTwo).setToTime(sdf.format(myCalendar.getTime()));
                            mScheduleWeekOnlineAdapter.addItems(weekDayListTwo);
                            ScheduleSubmitModel.TimeDatum timeDatum = new ScheduleSubmitModel.TimeDatum();
                            timeDatum.setTo(sdf.format(myCalendar.getTime()));
                            timeDatum.setFrom(weekDayListTwo.get(pos).getDailySchedules().get(posTwo).getFromTime());
                            timeDatum.setFromTimeStamp("50340000");
                            timeDatum.setToTimeStamp("50340000");
                            if (posTwo != 0) {
                                listOnline.get(pos).getTimeData().set(posTwo - 1, timeDatum);
                                mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
                                getBaseActivity().showLoading();
                                mViewModel.submitData(mScheduleSubmitOnlineSchedule);
                            } else {
                                listOnline.get(pos).getTimeData().add(timeDatum);
                                mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
                            }
                        } else {
                            Toast.makeText(getContext(), "Select Correct Time", Toast.LENGTH_SHORT).show();
                        }
                    }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time for Online Schedule");
                    mTimePicker.show();
                } else {
                    Toast.makeText(getContext(), "Select from Time first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mScheduleWeekOnlineAdapter.setListener(pos -> {
            listOnline.get(pos).setHoliday(!listOnline.get(pos).isHoliday());
            mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        });

//      Date Picker
        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            mTrainerMyScheduleFragmentBinding.tvFromDateDirect.setText(sdf.format(myCalendar.getTime()));
            mScheduleSubmitModelDirectSchedule.setFromDate(sdf.format(myCalendar.getTime()));
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        };

        dateTo = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            mTrainerMyScheduleFragmentBinding.tvToDateDirect.setText(sdf.format(myCalendar.getTime()));
            mScheduleSubmitModelDirectSchedule.setToDate(sdf.format(myCalendar.getTime()));
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        };

        dateOnline = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            mTrainerMyScheduleFragmentBinding.tvFromDateOnline.setText(sdf.format(myCalendar.getTime()));
            mScheduleSubmitOnlineSchedule.setFromDate(sdf.format(myCalendar.getTime()));
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        };

        dateToOnline = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            mTrainerMyScheduleFragmentBinding.tvToDateOnline.setText(sdf.format(myCalendar.getTime()));
            mScheduleSubmitOnlineSchedule.setToDate(sdf.format(myCalendar.getTime()));
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        };

        getBaseActivity().showLoading();
        mViewModel.getResourceData(dataManager.getCurrentUserId());
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void handleError(Throwable throwable) {
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessGetTrainerSchedule(TrainerScheduleResponse trainerScheduleResponse) {
        getBaseActivity().hideLoading();
//        Toast.makeText(getContext(), "" + trainerScheduleResponse.data.dailySchedule.get(0)
//                .weekDays.size(), Toast.LENGTH_LONG).show();
        if (trainerScheduleResponse.data.dailySchedule != null && trainerScheduleResponse.data.dailySchedule.size() > 0) {
            weeakAndData = trainerScheduleResponse.data.dailySchedule.get(0);
            mTrainerMyScheduleFragmentBinding.tvFromDateDirect.setText(weeakAndData.fromDate);
            mTrainerMyScheduleFragmentBinding.tvToDateDirect.setText(weeakAndData.toDate);
            mTrainerMyScheduleFragmentBinding.checkboxDirectTraining.setChecked(weeakAndData.availableforDirectTraining);
            mTrainerMyScheduleFragmentBinding.checkboxGroupSession.setChecked(weeakAndData.availableforDirectGroupSession);
            weekDayList = weeakAndData.weekDays;

//            //TODO:- DataSet
            mScheduleSubmitModelDirectSchedule.setFromDate(weeakAndData.fromDate);
            mScheduleSubmitModelDirectSchedule.setToDate(weeakAndData.toDate);
            mScheduleSubmitModelDirectSchedule.setScheduleType("Daily Schedule");
            mScheduleSubmitModelDirectSchedule.setTrainerId(dataManager.getCurrentUserId());
            mScheduleSubmitModelDirectSchedule.setWeekdayScheduleId(0);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectGroupSession(weeakAndData.availableforDirectGroupSession);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectTraining(weeakAndData.availableforDirectTraining);

            listDirect = new ArrayList<>();
            for (int i = 0; i < weeakAndData.weekDays.size(); i++) {
                ScheduleSubmitModel.DailySchedule dailySchedule = new ScheduleSubmitModel.DailySchedule();
                dailySchedule.setDay(weeakAndData.weekDays.get(i).weekDay);
                dailySchedule.setHoliday(weeakAndData.weekDays.get(i).isHoliday);
                dailySchedule.setTimeData(new ArrayList<>());

                for (DailySchedule__1 data : weeakAndData.weekDays.get(i).dailySchedules) {
                    ScheduleSubmitModel.TimeDatum timeDatum = new ScheduleSubmitModel.TimeDatum();
                    timeDatum.setFrom(data.getFromTime());
                    timeDatum.setTo(data.getToTime());
                    timeDatum.setToTimeStamp("50340000");
                    timeDatum.setFromTimeStamp("50340000");
                    dailySchedule.getTimeData().add(timeDatum);
                }
                listDirect.add(dailySchedule);

                weeakAndData.weekDays.get(i).dailySchedules.add(0, new DailySchedule__1());
            }
            mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
            mScheduleWeekAdapter.addItems(weeakAndData.weekDays);
        }
        if (trainerScheduleResponse.data.onlineSchedule != null && trainerScheduleResponse.data.onlineSchedule.size() > 0) {
            weekAndDataTwo = trainerScheduleResponse.data.onlineSchedule.get(0);
            weekDayListTwo = weekAndDataTwo.weekDays;
            //TODO:- DataSet
            mScheduleSubmitOnlineSchedule.setFromDate(weekAndDataTwo.fromDate);
            mScheduleSubmitOnlineSchedule.setToDate(weekAndDataTwo.toDate);
            mScheduleSubmitOnlineSchedule.setScheduleType("Online Schedule");
            mScheduleSubmitOnlineSchedule.setTrainerId(dataManager.getCurrentUserId());
            mScheduleSubmitOnlineSchedule.setWeekdayScheduleId(0);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineGroupSession(weekAndDataTwo.availableforOnlineGroupSession);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineTraining(weekAndDataTwo.availableforOnlineTraining);

            mTrainerMyScheduleFragmentBinding.checkboxGroupSessionOnline.setChecked(weekAndDataTwo.availableforOnlineGroupSession);
            mTrainerMyScheduleFragmentBinding.checkboxOnlineTraining.setChecked(weekAndDataTwo.availableforOnlineTraining);

            mTrainerMyScheduleFragmentBinding.tvFromDateOnline.setText(trainerScheduleResponse.data.onlineSchedule.get(0).fromDate);
            mTrainerMyScheduleFragmentBinding.tvToDateOnline.setText(trainerScheduleResponse.data.onlineSchedule.get(0).toDate);

            listOnline = new ArrayList<>();
            for (int i = 0; i < weekAndDataTwo.weekDays.size(); i++) {

                ScheduleSubmitModel.DailySchedule dailySchedule = new ScheduleSubmitModel.DailySchedule();
                dailySchedule.setDay(weekAndDataTwo.weekDays.get(i).weekDay);
                dailySchedule.setHoliday(weekAndDataTwo.weekDays.get(i).isHoliday);
                dailySchedule.setTimeData(new ArrayList<>());

                for (DailySchedule__1 data : weekAndDataTwo.weekDays.get(i).dailySchedules) {
                    ScheduleSubmitModel.TimeDatum timeDatum = new ScheduleSubmitModel.TimeDatum();
                    timeDatum.setFrom(data.getFromTime());
                    timeDatum.setTo(data.getToTime());
                    timeDatum.setToTimeStamp("50340000");
                    timeDatum.setFromTimeStamp("50340000");
                    dailySchedule.getTimeData().add(timeDatum);
                }
                listOnline.add(dailySchedule);
                weekAndDataTwo.weekDays.get(i).dailySchedules.add(0, new DailySchedule__1());
            }
            mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
            mScheduleWeekOnlineAdapter.addItems(weekAndDataTwo.weekDays);
        }
    }

    @Override
    public void openDateForDirectFrom() {
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void openDateForDirectTo() {
        new DatePickerDialog(getContext(), dateTo, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void openDateForOnlineFrom() {
        new DatePickerDialog(getContext(), dateOnline, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void openDateForOnlineTo() {
        new DatePickerDialog(getContext(), dateToOnline, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onClickAvailableforDirectTraining() {
        if (mTrainerMyScheduleFragmentBinding.checkboxDirectTraining.isChecked()) {
            mTrainerMyScheduleFragmentBinding.checkboxDirectTraining.setChecked(false);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectTraining(false);
            for (int i = 0; i < listDirect.size(); i++) {
                listDirect.get(i).setHoliday(false);
            }
            mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
            Log.e("DATALINK", "onClickAvailableforDirectTraining: "+mScheduleSubmitModelDirectSchedule.toString() );
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        } else {
            mTrainerMyScheduleFragmentBinding.checkboxDirectTraining.setChecked(true);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectTraining(true);
            for (int i = 0; i < listDirect.size(); i++) {
                listDirect.get(i).setHoliday(true);
            }
            mScheduleSubmitModelDirectSchedule.setDailySchedule(listDirect.toString());
            Log.e("DATALINK", "onClickAvailableforDirectTraining: "+mScheduleSubmitModelDirectSchedule.toString());
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        }
    }

    @Override
    public void onClickAvailableforDirectGroup() {
        if (mTrainerMyScheduleFragmentBinding.checkboxGroupSession.isChecked()) {
            mTrainerMyScheduleFragmentBinding.checkboxGroupSession.setChecked(false);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectGroupSession(false);
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        } else {
            mTrainerMyScheduleFragmentBinding.checkboxGroupSession.setChecked(true);
            mScheduleSubmitModelDirectSchedule.setAvailableforDirectGroupSession(true);
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitModelDirectSchedule);
        }
    }

    @Override
    public void onClickAvailableforOnlineTraining() {
        if (mTrainerMyScheduleFragmentBinding.checkboxOnlineTraining.isChecked()) {
            mTrainerMyScheduleFragmentBinding.checkboxOnlineTraining.setChecked(false);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineTraining(false);
            for (int i = 0; i < listOnline.size(); i++) {
                listOnline.get(i).setHoliday(false);
            }
            mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        } else {
            mTrainerMyScheduleFragmentBinding.checkboxOnlineTraining.setChecked(true);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineTraining(true);
            for (int i = 0; i < listOnline.size(); i++) {
                listOnline.get(i).setHoliday(true);
            }
            mScheduleSubmitOnlineSchedule.setDailySchedule(listOnline.toString());
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        }
    }

    @Override
    public void onClickAvailableforOnlineGroup() {
        if (mTrainerMyScheduleFragmentBinding.checkboxGroupSessionOnline.isChecked()) {
            mTrainerMyScheduleFragmentBinding.checkboxGroupSessionOnline.setChecked(false);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineGroupSession(false);
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        } else {
            mTrainerMyScheduleFragmentBinding.checkboxGroupSessionOnline.setChecked(true);
            mScheduleSubmitOnlineSchedule.setAvailableforOnlineGroupSession(true);
            getBaseActivity().showLoading();
            mViewModel.submitData(mScheduleSubmitOnlineSchedule);
        }
    }
}
