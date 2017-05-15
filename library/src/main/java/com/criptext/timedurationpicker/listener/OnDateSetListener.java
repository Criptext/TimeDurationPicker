package com.criptext.timedurationpicker.listener;

import com.criptext.timedurationpicker.TimeDurationPicker;

/**
 * Created by jzxiang on 16/4/20.
 */
public interface OnDateSetListener {

    void onDateSet(TimeDurationPicker timePickerView, long millseconds, boolean choseRightOption);
}
