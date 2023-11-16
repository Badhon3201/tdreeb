package com.ryx.tdreeb.customui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ryx.tdreeb.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView extends LinearLayout {
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 35;

    // default date format
    private static final String DATE_FORMAT = "MMMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();
    private Date fromDate;
    private Date toDate;

    //event handling
    private EventHandler eventHandler = null;

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid, gridWeek;

    // seasons' rainbow
    int[] rainbow = new int[]{
            R.color.cyan_dark,
            R.color.cyan_dark,
            R.color.cyan_dark,
            R.color.cyan_dark
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
        gridWeek = (GridView) findViewById(R.id.week_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                Log.e("DATALINK", "onItemLongClick: ");
                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
                return true;
            }
        });


    }

    public void updateCalendar() {
        updateCalendar(null);
    }

    public void setFormAndToDate(Date fromDate, Date toDate) {
        if (this.fromDate == null && this.toDate == null) {
            this.fromDate = fromDate;
            this.toDate = toDate;
            synchronized (grid) {
                updateCalendar();
            }
        }
    }

    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));
        ArrayList<String> weeklist = new ArrayList<>();
        weeklist.add("Sun");
        weeklist.add("Mon");
        weeklist.add("Tue");
        weeklist.add("Wed");
        weeklist.add("Thu");
        weeklist.add("Fri");
        weeklist.add("Sat");

        gridWeek.setAdapter(new WeekAdapter(getContext(), weeklist));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];

        // header.setBackgroundColor(getResources().getColor(color));
    }

    private class WeekAdapter extends ArrayAdapter<String> {
        // days with events
        ArrayList<String> week;
        // for view inflation
        private LayoutInflater inflater;


        public WeekAdapter(Context context, ArrayList<String> week) {
            super(context, R.layout.control_calendar_day, week);
            this.week = week;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(R.layout.control_calendar_day, parent, false);

            ((TextView) convertView).setText(String.valueOf(week.get(position)));
            return convertView;
        }
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;
        private ArrayList<Integer> selectCheck;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
            selectCheck = new ArrayList<>();
            Date today = new Date();
            for (int i = 0; i < days.size(); i++) {
                Date date = days.get(i);
                int day = date.getDate();
                if (day == today.getDate()) {
                    this.selectCheck.add(1);
                } else {
                    this.selectCheck.add(0);
                }

            }
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            // day in question
            final Date date = getItem(position);
            final int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            final Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);


            // if this day has an event, specify event image
            view.setBackgroundResource(0);
            if (eventDays != null) {
                for (Date eventDate : eventDays) {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year) {
                        break;
                    }
                }
            }

            // clear styling
            ((TextView) view).setTypeface(null, Typeface.NORMAL);
            ((TextView) view).setTextColor(Color.BLACK);

            if (toDate != null && fromDate != null) {
                if ((month >= fromDate.getMonth() && month <= toDate.getMonth())) {
                    if (System.currentTimeMillis() <= date.getTime()) {
                        ((TextView) view).setTextColor(Color.BLACK);
                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.shadow));
                    }
                } else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.shadow));
                }
            }

//            if (month != today.getMonth() || year != today.getYear()) {
//                ((TextView) view).setTextColor(getResources().getColor(R.color.shadow));
//            }
            if (day == today.getDate()) {
                // if it is today, set it to blue/bold
                ((TextView) view).setTypeface(null, Typeface.NORMAL);
                ((TextView) view).setBackground(getResources().getDrawable(R.drawable.today_bg));
                ((TextView) view).setTextColor(Color.BLACK);
                //eventHandler.itemClick(date);
            }

            if (selectCheck.get(position) == 1) {
                if (toDate != null) {
                    if (today.getTime() <= toDate.getTime()) {
                        ((TextView) view).setTypeface(null, Typeface.NORMAL);
                        ((TextView) view).setTextColor(Color.WHITE);
                        ((TextView) view).setBackground(getResources().getDrawable(R.drawable.select_date));
                    }
                }
                if (today.getDate() == date.getDate()) {
                    eventHandler.itemClick(date, true);
                }
            } else {
                // clear styling
                ((TextView) view).setTypeface(null, Typeface.NORMAL);
                ((TextView) view).setTextColor(Color.BLACK);
                if (toDate != null && fromDate != null) {
                    if ((month >= fromDate.getMonth() && month <= toDate.getMonth())) {
                        if (System.currentTimeMillis() <= date.getTime()) {
                            ((TextView) view).setTextColor(Color.BLACK);
                        } else if (day == today.getDate()) {
                            ((TextView) view).setTextColor(Color.BLACK);
                        } else {
                            ((TextView) view).setTextColor(getResources().getColor(R.color.shadow));
                        }
                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.shadow));
                    }
                }
            }
//
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //eventHandler.itemClick(date, true);
                    if (((TextView) view).getCurrentTextColor() == Color.BLACK) {
                        eventHandler.itemClick(date, true);
                        for (int k = 0; k < selectCheck.size(); k++) {
                            if (k == position) {
                                selectCheck.set(k, 1);
                            } else {
                                selectCheck.set(k, 0);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            });

            // set text
            ((TextView) view).setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Date date);

        void itemClick(Date date, boolean flag);
    }
}