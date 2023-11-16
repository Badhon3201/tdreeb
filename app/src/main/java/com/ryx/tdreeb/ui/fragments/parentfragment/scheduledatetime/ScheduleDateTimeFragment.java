package com.ryx.tdreeb.ui.fragments.parentfragment.scheduledatetime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ScheduleTimeTwoAdapter;
import com.ryx.tdreeb.customui.CalendarView;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.TrainerScheduleResponse;
import com.ryx.tdreeb.databinding.FragmentScheduleDateTimeBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ScheduleDateTimeFragment extends BaseFragment<FragmentScheduleDateTimeBinding, ScheduleDateTimeViewModel> implements ScheduleDateTimeNavigator, ScheduleTimeTwoAdapter.ItemClick {

    FragmentScheduleDateTimeBinding mFragmentScheduleDateTimeBinding;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    ScheduleTimeTwoAdapter mScheduleTimeAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;

    List<String> timeStr;
    private String dateStr = "";
    private String time = "";
    private String dayOfTheWeek = "";
    private Date selectedData;

    private String scheduleType = "";
    private int trainerID = 0;
    private TrainerScheduleResponse mTrainerScheduleResponse;

    public ScheduleDateTimeFragment() {
        // Required empty public constructor
    }

    public static ScheduleDateTimeFragment newInstance() {
        ScheduleDateTimeFragment fragment = new ScheduleDateTimeFragment();
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
        return R.layout.fragment_schedule_date_time;
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
        mFragmentScheduleDateTimeBinding = getViewDataBinding();
        if (getArguments() != null) {
            ScheduleDateTimeFragmentArgs args = ScheduleDateTimeFragmentArgs.fromBundle(getArguments());
            scheduleType = args.getScheduleType();
            trainerID = args.getTrainerId();
        }
        setUp();
    }

    private void setUp() {

        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentScheduleDateTimeBinding.getRoot());

        tvToolBarTitle = mFragmentScheduleDateTimeBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentScheduleDateTimeBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentScheduleDateTimeBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.date_calender));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        timeStr = new ArrayList<>();
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        mFragmentScheduleDateTimeBinding.calendarView.updateCalendar(events);

        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentScheduleDateTimeBinding.rvTime.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentScheduleDateTimeBinding.rvTime.setLayoutManager(mLayoutManager);
        mFragmentScheduleDateTimeBinding.rvTime.setAdapter(mScheduleTimeAdapter);
        mScheduleTimeAdapter.setEvent(this);

        getBaseActivity().showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("trainerId", trainerID + "");
        map.put("ScheduleType", scheduleType);
        mViewModel.getSchedule(map);

        mFragmentScheduleDateTimeBinding.calendarView.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                DateFormat df = SimpleDateFormat.getDateInstance();
            }

            @Override
            public void itemClick(Date date, boolean flag) {
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                dayOfTheWeek = sdf.format(date);
                //Log.e("DATALWEEK", "itemClick: " + dayOfTheWeek);
                selectedData = date;
                dateStr = outputFormat.format(date);
                timeDataSet();
            }
        });

        mFragmentScheduleDateTimeBinding.btnContinue.setOnClickListener(v -> {
            if (!time.isEmpty() && !dateStr.isEmpty()) {
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("time", time);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("dateStr", dateStr);
                navController.popBackStack();
            } else {
                Toast.makeText(getContext(), "Please Select Date and Time", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void clickOn(String time) {
        this.time = time;
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
    public void onSuccessSchedule(TrainerScheduleResponse mTrainerScheduleResponse) {
        getBaseActivity().hideLoading();
        if (mTrainerScheduleResponse.isSuccess) {
            this.mTrainerScheduleResponse = mTrainerScheduleResponse;
            timeDataSet();
        }
    }

    private void timeDataSet(){
        timeStr.clear();
        if(mTrainerScheduleResponse!=null){
            if (scheduleType.equals("Daily Schedule")) {
                for (int i = 0; i < mTrainerScheduleResponse.data.dailySchedule.get(0).weekDays.size(); i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    Date toDate = null;
                    Date fromDate = null;
                    try {
                        toDate = sdf.parse(mTrainerScheduleResponse.data.dailySchedule.get(0).toDate);
                        fromDate = sdf.parse(mTrainerScheduleResponse.data.dailySchedule.get(0).fromDate);
                        mFragmentScheduleDateTimeBinding.calendarView.setFormAndToDate(fromDate, toDate);
                        if (toDate.getMonth() >= selectedData.getMonth()) {
                            if (mTrainerScheduleResponse.data.dailySchedule.get(0).weekDays.get(i).weekDay.toLowerCase().equals(dayOfTheWeek.toLowerCase())) {
                                for (int j = 0; j < mTrainerScheduleResponse.data.dailySchedule.get(0).weekDays.get(i).dailySchedules.size(); j++) {
                                    timeStr.add(mTrainerScheduleResponse.data.dailySchedule.get(0).weekDays.get(i).dailySchedules.get(j).fromTime + " - " + mTrainerScheduleResponse.data.dailySchedule.get(0).weekDays.get(i).dailySchedules.get(j).toTime);
                                }
                                mScheduleTimeAdapter.setData(timeStr);
                                break;
                            }
                        } else {
                            Toast.makeText(getContext(), "Over Schedule From Date (From Date: " + mTrainerScheduleResponse.data.dailySchedule.get(0).fromDate + ", To Date: " + mTrainerScheduleResponse.data.dailySchedule.get(0).toDate + " )", Toast.LENGTH_SHORT).show();
                            timeStr.clear();
                            mScheduleTimeAdapter.setData(timeStr);
                            break;
                        }
//
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                {
                    for (int i = 0; i < mTrainerScheduleResponse.data.onlineSchedule.get(0).weekDays.size(); i++) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                        Date toDate = null;
                        Date fromDate = null;
                        try {
                            toDate = sdf.parse(mTrainerScheduleResponse.data.onlineSchedule.get(0).toDate);
                            fromDate = sdf.parse(mTrainerScheduleResponse.data.onlineSchedule.get(0).fromDate);
                            mFragmentScheduleDateTimeBinding.calendarView.setFormAndToDate(fromDate, toDate);
                            if (toDate.getMonth() >= selectedData.getMonth()) {
                                if (mTrainerScheduleResponse.data.onlineSchedule.get(0).weekDays.get(i).weekDay.toLowerCase().equals(dayOfTheWeek.toLowerCase())) {
                                    for (int j = 0; j < mTrainerScheduleResponse.data.onlineSchedule.get(0).weekDays.get(i).dailySchedules.size(); j++) {
                                        timeStr.add(mTrainerScheduleResponse.data.onlineSchedule.get(0).weekDays.get(i).dailySchedules.get(j).fromTime + " - " + mTrainerScheduleResponse.data.onlineSchedule.get(0).weekDays.get(i).dailySchedules.get(j).toTime);
                                    }
                                    mScheduleTimeAdapter.setData(timeStr);
                                    break;
                                }
                            } else {
                                Toast.makeText(getContext(), "Over Schedule From Date (From Date: " + mTrainerScheduleResponse.data.onlineSchedule.get(0).fromDate + ", To Date: " + mTrainerScheduleResponse.data.onlineSchedule.get(0).toDate + " )", Toast.LENGTH_SHORT).show();
                                timeStr.clear();
                                mScheduleTimeAdapter.setData(timeStr);
                                break;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}