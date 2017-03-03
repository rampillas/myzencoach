package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;
import com.unir.grupo2.myzeancoach.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */


public class PlanFragment extends Fragment {

    @BindView(R.id.calendar_view)
    CalendarPickerView calendar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_layout, container, false);
        ButterKnife.bind(this, view);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        takePlan(nextYear, lastYear);

        return view;
    }
    private void takePlan(final Calendar nextYear, final Calendar lastYear) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        calendar.setCustomDayView(new DefaultDayViewAdapter());
        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.init(getDateWithYear(2016), getDateWithYear(2020))
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(c.getTime());

        calendar.highlightDates(getHighlightedDaysForMonth( // Adds some highlighted days
                c.get(Calendar.MONTH) - 1,
                c.get(Calendar.MONTH),
                c.get(Calendar.MONTH) + 1));
    }

    private List<Date> getHighlightedDaysForMonth(int... month) {
        List<Date> dateList = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < 35; j++) {
                dateList.add(getDateWithYearAndMonthForDay(c.get(Calendar.YEAR), i, j));
            }
        }

        return dateList;
    }

    private Date getDateWithYearAndMonthForDay(int year, int month, int day) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    private Date getDateWithYear(int year) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
}

