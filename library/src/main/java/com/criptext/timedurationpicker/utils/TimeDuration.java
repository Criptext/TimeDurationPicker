package com.criptext.timedurationpicker.utils;

import android.util.Log;

/**
 * Created by gesuwall on 4/26/17.
 */
public class TimeDuration {
    public static long yearInMS = 31536000000L;
    public static long monthInMS = 2629746000L;
    public static long daysInMS = 86400000L;
    public static long hoursInMS = 3600000L;
    public static long minutesInMS = 60000L;

    public long toMiliseconds(int years, int months, int days, int hours, int minutes) {
        return years * yearInMS
        + months * monthInMS
        + days * daysInMS
        + hours * hoursInMS
        + minutes * minutesInMS;
    }

    public String milisToString(long milis, String daysLabel, String hoursLabel, String minutesLabel) {
        long numberOfDays = milis / daysInMS;
        long residuo = milis % daysInMS;
        long numberOfHours = residuo / hoursInMS;
        residuo = residuo % hoursInMS;
        long numberOfMinutes = residuo / minutesInMS;

        String numberOfDaysStr = numberOfDays == 0 ? "00" : "" + numberOfDays;
        String numberOfHoursStr = numberOfHours == 0 ? "00" : "" + numberOfHours;
        String numberOfMinutesStr = numberOfMinutes == 0 ? "00" : "" + numberOfMinutes;

        return numberOfDaysStr + " " + daysLabel + " " + numberOfHoursStr + " " + hoursLabel + " "
                + numberOfMinutesStr + " " + minutesLabel;
    }
}
