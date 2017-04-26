package com.criptext.timedurationpicker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.criptext.timedurationpicker.config.PickerConfig;
import com.criptext.timedurationpicker.data.Type;
import com.criptext.timedurationpicker.data.WheelCalendar;
import com.criptext.timedurationpicker.listener.OnDateSetListener;
import com.criptext.timedurationpicker.utils.TimeDuration;

/**
 * Created by gesuwall on 4/25/17.
 */

public class TimeDurationPicker extends DialogFragment implements View.OnClickListener {
    PickerConfig mPickerConfig;
    private TimeWheel mTimeWheel;
    private long mCurrentMillSeconds;

    private static TimeDurationPicker newInstance(PickerConfig pickerConfig) {
        TimeDurationPicker timePickerDialog = new TimeDurationPicker();
        timePickerDialog.initialize(pickerConfig);
        return timePickerDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.picker_height);

        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);//Here!
        window.setGravity(Gravity.BOTTOM);
    }

    private void initialize(PickerConfig pickerConfig) {
                                                    mPickerConfig = pickerConfig;
                                                                                 }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    View initView() {
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
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel) {
            dismiss();
        } else if (i == R.id.tv_sure) {
            sureClicked();
        }
    }

    public long getCurrentMillSeconds() {
        return mCurrentMillSeconds;
        }

    /*
    * @desc This method is called when onClick method is invoked by sure button.
    * @param none
    * @return none
    */
    void sureClicked() {
        final TimeDuration timeDuration = new TimeDuration();
        mCurrentMillSeconds = timeDuration.toMiliseconds(mTimeWheel.getCurrentYear(),
                mTimeWheel.getCurrentMonth(), mTimeWheel.getCurrentDay(), mTimeWheel.getCurrentHour(),
                mTimeWheel.getCurrentMinute());
        if (mPickerConfig.mCallBack != null) {
            mPickerConfig.mCallBack.onDateSet(this, mCurrentMillSeconds);
        }
        dismiss();
    }

    public void setCallBack(OnDateSetListener listener) {
        mPickerConfig.mCallBack = listener;
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

        public Builder setCurrentMillseconds(long millseconds) {
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
