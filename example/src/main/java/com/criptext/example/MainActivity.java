package com.criptext.example;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.criptext.timedurationpicker.TimeDurationPicker;
import com.criptext.timedurationpicker.data.Type;
import com.criptext.timedurationpicker.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {
    TimeDurationPicker mDialogAll;
    TimeDurationPicker mDialogYearMonth;
    TimeDurationPicker mDialogYearMonthDay;
    TimeDurationPicker mDialogMonthDayHourMinute;
    TimeDurationPicker mDialogDayHourMinute;
    TimeDurationPicker mDialogHourMinute;
    TextView mTvTime;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimeDurationPicker.Builder()
                .setCallBack(this)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setYearText("Year")
                .setMonthText("Month")
                .setDayText("Day")
                .setHourText("Hour")
                .setMinuteText("Minute")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(60000)
                .setThemeColor(ContextCompat.getColor(this, R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();

//        mDialogAll = new TimeDurationPicker.Builder()
//                .setMinMillseconds(System.currentTimeMillis())
//                .setThemeColor(R.color.colorPrimary)
//                .setWheelItemTextSize(16)
//                .setCallBack(this)
//                .build();
        mDialogYearMonth = new TimeDurationPicker.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mDialogYearMonthDay = new TimeDurationPicker.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();
        mDialogMonthDayHourMinute = new TimeDurationPicker.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(this)
                .build();
        mDialogHourMinute = new TimeDurationPicker.Builder()
                .setType(Type.HOURS_MINS)
                .setCallBack(this)
                .build();
        mDialogDayHourMinute = new TimeDurationPicker.Builder()
                .setType(Type.DAY_HOUR_MIN)
                .setHourText(" Hours")
                .setMinuteText(" Minutes")
                .setDayText(" Days")
                .setCancelStringId("Cancel")
                .setSureStringId("Set")
                .setTitleStringId("Expiration Timer")
                .setExpirationOptionLabels("Timer begins once the email is:", "Opened", "Sent")
                .setThemeColor(Color.WHITE)
                .setToolBarTextColor(Color.BLACK)
                .setCallBack(this)
                .setMaxDay(24)
                .setWheelItemSelectorTextSize(16)
                .setCurrentMillseconds(60000)
                .build();
    }

    void initView() {
        findViewById(R.id.btn_all).setOnClickListener(this);
        findViewById(R.id.btn_year_month_day).setOnClickListener(this);
        findViewById(R.id.btn_year_month).setOnClickListener(this);
        findViewById(R.id.btn_month_day_hour_minute).setOnClickListener(this);
        findViewById(R.id.btn_hour_minute).setOnClickListener(this);
        findViewById(R.id.btn_day_hour_minute).setOnClickListener(this);

        mTvTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all:
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.btn_year_month:
                mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
                break;
            case R.id.btn_year_month_day:
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.btn_month_day_hour_minute:
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.btn_hour_minute:
                mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");
                break;
            case R.id.btn_day_hour_minute:
                mDialogDayHourMinute.show(getSupportFragmentManager(), "day_hour_minute");
                break;
        }
    }


    @Override
    public void onDateSet(TimeDurationPicker timePickerDialog, long miliseconds, boolean choseRightOption) {
        final String opt = choseRightOption ? "Sent" : "Opened";
        final String text = "Duration: " + miliseconds + "ms. " + opt;
        mTvTime.setText(text);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

}
