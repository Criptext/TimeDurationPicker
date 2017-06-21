package com.criptext.timedurationpicker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.criptext.timedurationpicker.config.PickerConfig;
import com.criptext.timedurationpicker.data.Type;
import com.criptext.timedurationpicker.data.WheelCalendar;
import com.criptext.timedurationpicker.listener.OnDateSetListener;
import com.criptext.timedurationpicker.utils.TimeDuration;

/**
 * Created by gesuwall on 4/25/17.
 */

public class TimeDurationPicker extends SlideDialogFragment implements View.OnClickListener {
    PickerConfig mPickerConfig;
    private TimeWheel mTimeWheel;
    private long mCurrentMillSeconds;
    private boolean choseRight = false;

    private static TimeDurationPicker newInstance(PickerConfig pickerConfig) {
        TimeDurationPicker timePickerDialog = new TimeDurationPicker();
        timePickerDialog.initialize(pickerConfig);
        return timePickerDialog;
    }

    private void initialize(PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
    }

    void initExpirationViews(View view) {
        TextView expirationMessage = (TextView) view.findViewById(R.id.expiration_options_text);
        expirationMessage.setText(mPickerConfig.mExpirationMsg);
        RadioButton op1 = (RadioButton) view.findViewById(R.id.expiration_option_left);
        op1.setText(mPickerConfig.mExpirationOp1Label);
        RadioButton op2 = (RadioButton) view.findViewById(R.id.expiration_option_right);
        op2.setText(mPickerConfig.mExpirationOp2Label);

        if(choseRight){
            op2.toggle();
        }
        else {
            op1.toggle();
        }
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { choseRight = false; }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { choseRight = true; }
        });
    }

    @Override
    public View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.timepicker_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(this);
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);
        sure.setOnClickListener(this);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        View toolbar = view.findViewById(R.id.toolbar);

        title.setText(mPickerConfig.mTitleString);
        title.setTextColor(mPickerConfig.mToolBarTVColor);
        cancel.setText(mPickerConfig.mCancelString);
        cancel.setTextColor(mPickerConfig.mToolBarTVColor);
        sure.setText(mPickerConfig.mSureString);
        sure.setTextColor(mPickerConfig.mToolBarTVColor);
        toolbar.setBackgroundColor(mPickerConfig.mThemeColor);

        mTimeWheel = new TimeWheel(view, mPickerConfig);
        initExpirationViews(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        sureClicked(i == R.id.tv_cancel);
    }

    public long getCurrentMillSeconds() {
        return mCurrentMillSeconds;
    }

    void sureClicked(boolean cancelled) {
        final TimeDuration timeDuration = new TimeDuration();
        mCurrentMillSeconds = cancelled ? 0L : timeDuration.toMiliseconds(mTimeWheel.getCurrentYear(),
                mTimeWheel.getCurrentMonth(), mTimeWheel.getCurrentDay(), mTimeWheel.getCurrentHour(),
                mTimeWheel.getCurrentMinute());
        if (mPickerConfig.mCallBack != null) {
            mPickerConfig.mCallBack.onDateSet(this, mCurrentMillSeconds, choseRight);
        }
        dismiss();
    }

    public void setCallBack(OnDateSetListener listener) {
        mPickerConfig.mCallBack = listener;
    }

    public void setExpirationOnSent() {
        this.choseRight = true;
    }

    public static class Builder {
        PickerConfig mPickerConfig;

        public Builder() {
            mPickerConfig = new PickerConfig();
        }

        public Builder setType(Type type) {
            mPickerConfig.mType = type;
            return this;
        }

        public Builder setThemeColor(int color) {
            mPickerConfig.mThemeColor = color;
            return this;
        }

        public Builder setExpirationOptionLabels(String msg, String op1, String op2) {
            mPickerConfig.mExpirationMsg = msg;
            mPickerConfig.mExpirationOp1Label = op1;
            mPickerConfig.mExpirationOp2Label = op2;
            return this;
        }

        public Builder setCancelStringId(String left) {
            mPickerConfig.mCancelString = left;
            return this;
        }

        public Builder setSureStringId(String right) {
            mPickerConfig.mSureString = right;
            return this;
        }

        public Builder setTitleStringId(String title) {
            mPickerConfig.mTitleString = title;
            return this;
        }

        public Builder setToolBarTextColor(int color) {
            mPickerConfig.mToolBarTVColor = color;
            return this;
        }

        public Builder setWheelItemTextNormalColor(int color) {
            mPickerConfig.mWheelTVNormalColor = color;
            return this;
        }

        public Builder setWheelItemTextSelectorColor(int color) {
            mPickerConfig.mWheelTVSelectorColor = color;
            return this;
        }

        public Builder setWheelItemTextSize(int size) {
            mPickerConfig.mWheelTVSize = size;
            return this;
        }

        public Builder setWheelItemSelectorTextSize(int size) {
            mPickerConfig.mWheelTVSelectorSize = size;
            return this;
        }

        public Builder setCyclic(boolean cyclic) {
            mPickerConfig.cyclic = cyclic;
            return this;
        }

        public Builder setMinMillseconds(long millseconds) {
            mPickerConfig.mMinCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setMaxMillseconds(long millseconds) {
            mPickerConfig.mMaxCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setCurrentDay(int day) {
            mPickerConfig.mCurrentDay = day;
            return this;
        }

        public Builder setCurrentHour(int hour) {
            mPickerConfig.mCurrentHour = hour;
            return this;
        }

        public Builder setCurrentMinute(int minute) {
            mPickerConfig.mCurrentMinute = minute;
            return this;
        }

        public Builder setCurrentMillseconds(long millseconds) {
            mPickerConfig.mCurrentDay = (int)(millseconds / TimeDuration.daysInMS);
            mPickerConfig.mCurrentHour = (int)(millseconds / TimeDuration.hoursInMS);
            mPickerConfig.mCurrentMinute = (int)(millseconds / TimeDuration.minutesInMS);
            mPickerConfig.mCurrentCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setYearText(String year){
            mPickerConfig.mYear = year;
            return this;
        }

        public Builder setMonthText(String month){
            mPickerConfig.mMonth = month;
            return this;
        }

        public Builder setDayText(String day){
            mPickerConfig.mDay = day;
            return this;
        }

        public Builder setHourText(String hour){
            mPickerConfig.mHour = hour;
            return this;
        }

        public Builder setMinuteText(String minute){
            mPickerConfig.mMinute = minute;
            return this;
        }

        public Builder setDayStep(int dayStep){
            mPickerConfig.dayStep = dayStep;
            return this;
        }

        public Builder setHourStep(int hourStep){
            mPickerConfig.hourStep = hourStep;
            return this;
        }

        public Builder setMinuteStep(int minuteStep){
            mPickerConfig.minuteStep = minuteStep;
            return this;
        }

        public Builder setMinDay(int minDay) {
            mPickerConfig.minDay = minDay;
            return this;
        }

        public Builder setMaxDay(int maxDay) {
            mPickerConfig.maxDay = maxDay;
            return this;
        }

        public Builder setCallBack(OnDateSetListener listener) {
            mPickerConfig.mCallBack = listener;
            return this;
        }

        public void modify(TimeDurationPicker existingPicker) {
            existingPicker.initialize(mPickerConfig);
        }

        public TimeDurationPicker build() {
            return newInstance(mPickerConfig);
        }
    }
}
