package com.criptext.timedurationpicker;

/**
* Created by gesuwall on 4/25/17.
*/
import android.content.Context;
import android.view.View;

import com.criptext.timedurationpicker.adapters.NumericWheelAdapter;
import com.criptext.timedurationpicker.config.DefaultConfig;
import com.criptext.timedurationpicker.config.PickerConfig;
import com.criptext.timedurationpicker.data.PickerConstants;
import com.criptext.timedurationpicker.data.source.TimeRepository;
import com.criptext.timedurationpicker.utils.Utils;
import com.criptext.timedurationpicker.wheel.OnWheelChangedListener;
import com.criptext.timedurationpicker.wheel.WheelView;

import java.util.Calendar;

/**
 * Created by jzxiang on 16/4/20.
 */
public class TimeWheel {
    Context mContext;

    WheelView year, month, day, hour, minute;
    NumericWheelAdapter mYearAdapter, mMonthAdapter, mDayAdapter, mHourAdapter, mMinuteAdapter;

    PickerConfig mPickerConfig;
    TimeRepository mRepository;
    OnWheelChangedListener yearListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateMonths();
        }
    };
    OnWheelChangedListener monthListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateDays();
        }
    };
    OnWheelChangedListener dayListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateHours();
        }
    };
    OnWheelChangedListener minuteListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateMinutes();
        }
    };

    public TimeWheel(View view, PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;

        mRepository = new TimeRepository(pickerConfig);
        mContext = view.getContext();
        initialize(view);
    }

    public void initialize(View view) {
        initView(view);
        initYear();
        initMonth();
        initDay();
        initHour();
        initMinute();
    }


    void initView(View view) {
        year = (WheelView) view.findViewById(R.id.year);
        month = (WheelView) view.findViewById(R.id.month);
        day = (WheelView) view.findViewById(R.id.day);
        hour = (WheelView) view.findViewById(R.id.hour);
        minute = (WheelView) view.findViewById(R.id.minute);

        switch (mPickerConfig.mType) {
            case ALL:

                break;
            case YEAR_MONTH_DAY:
                Utils.hideViews(hour, minute);
                break;
            case YEAR_MONTH:
                Utils.hideViews(day, hour, minute);
                break;
            case DAY_HOUR_MIN:
                Utils.hideViews(year, month);
                break;
            case MONTH_DAY_HOUR_MIN:
                Utils.hideViews(year);
                break;
            case HOURS_MINS:
                Utils.hideViews(year, month, day);
                break;
            case YEAR:
                Utils.hideViews(month, day, hour, minute);
                break;
        }

        year.addChangingListener(yearListener);
        year.addChangingListener(monthListener);
        year.addChangingListener(dayListener);
        year.addChangingListener(minuteListener);
        month.addChangingListener(monthListener);
        month.addChangingListener(dayListener);
        month.addChangingListener(minuteListener);
        day.addChangingListener(dayListener);
        day.addChangingListener(minuteListener);
        hour.addChangingListener(minuteListener);
    }

    void initYear() {
        int minYear = mRepository.getMinYear();
        int maxYear = mRepository.getMaxYear();

        mYearAdapter = new NumericWheelAdapter(mContext, minYear, maxYear, PickerConstants.FORMAT, mPickerConfig.mYear);
        mYearAdapter.setConfig(mPickerConfig);
        year.setViewAdapter(mYearAdapter);
    }

    void initMonth() {
        updateMonths();
        month.setCyclic(mPickerConfig.cyclic);
    }

    void initDay() {
        updateDays();
        day.setCurrentItem(mPickerConfig.mCurrentDay);
        day.setCyclic(mPickerConfig.cyclic);
    }

    void initHour() {
        updateHours();
        hour.setCurrentItem(mPickerConfig.mCurrentHour);
        hour.setCyclic(mPickerConfig.cyclic);
    }

    void initMinute() {
        updateMinutes();
        minute.setCurrentItem(mPickerConfig.mCurrentMinute);
        minute.setCyclic(mPickerConfig.cyclic);
    }

    void updateMonths() {
        if (month.getVisibility() == View.GONE)
            return;

        int minMonth = PickerConstants.MIN_MONTH;
        int maxMonth = PickerConstants.MAX_MONTH;
        mMonthAdapter = new NumericWheelAdapter(mContext, minMonth, maxMonth, PickerConstants.FORMAT, mPickerConfig.mMonth);
        mMonthAdapter.setConfig(mPickerConfig);
        month.setViewAdapter(mMonthAdapter);
    }

    void updateDays() {
        if (day.getVisibility() == View.GONE)
            return;


        int maxDay = mPickerConfig.maxDay;
        int minDay = mPickerConfig.minDay;
        mDayAdapter = new NumericWheelAdapter(mContext, minDay, maxDay, mPickerConfig.dayStep,
                PickerConstants.FORMAT, mPickerConfig.mDay);
        mDayAdapter.setConfig(mPickerConfig);
        day.setViewAdapter(mDayAdapter);
    }

    void updateHours() {
        if (hour.getVisibility() == View.GONE)
            return;

        int minHour = PickerConstants.MIN_HOUR;
        int maxHour = PickerConstants.MAX_HOUR;

        mHourAdapter = new NumericWheelAdapter(mContext, minHour, maxHour, mPickerConfig.hourStep,
                PickerConstants.FORMAT, mPickerConfig.mHour);
        mHourAdapter.setConfig(mPickerConfig);
        hour.setViewAdapter(mHourAdapter);
    }

    void updateMinutes() {
        if (minute.getVisibility() == View.GONE)
            return;

        int minMinute = PickerConstants.MIN_MINUTE;
        int maxMinute = PickerConstants.MAX_MINUTE;

        mMinuteAdapter = new NumericWheelAdapter(mContext, minMinute, maxMinute, mPickerConfig.minuteStep,
                PickerConstants.FORMAT, mPickerConfig.mMinute);
        mMinuteAdapter.setConfig(mPickerConfig);
        minute.setViewAdapter(mMinuteAdapter);
    }

    public int getCurrentYear() {
        return year.getCurrentItem();
    }

    public int getCurrentMonth() {
        return month.getCurrentItem();
    }

    public int getCurrentDay() {
        return day.getCurrentItem();
    }

    public int getCurrentHour() {
        return hour.getCurrentItem();
    }

    public int getCurrentMinute() {
        return minute.getCurrentItem();
    }


}
