package com.criptext.timedurationpicker;

import com.criptext.timedurationpicker.utils.TimeDuration;

import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TimeDurationTest {
    private final TimeDuration timeDuration = new TimeDuration();
    @Test
    public void toMilisecondsTest() throws Exception {
        long ms = timeDuration.toMiliseconds(0, 0, 0, 0, 6);
        assertEquals(360000L, ms);

        ms = timeDuration.toMiliseconds(0, 0, 0, 2, 16);
        assertEquals(8160000, ms);

        ms = timeDuration.toMiliseconds(0, 0, 7, 0, 32);
        assertEquals(606720000, ms);
    }

    @Test
    public void milisToStringTest() throws Exception {
        long ms = timeDuration.toMiliseconds(0, 0, 3, 10, 6);
        String message = timeDuration.milisToString(ms, "Days", "Hours", "Mins.");
        assertEquals("3 Days 10 Hours 6 Mins.", message);
    }

    @Test
    public void milisToStringWithZeroDaysTest() throws Exception {
        long ms = timeDuration.toMiliseconds(0, 0, 0, 8, 30);
        String message = timeDuration.milisToString(ms, "Days", "Hours", "Mins.");
        assertEquals("00 Days 8 Hours 30 Mins.", message);
    }

    @Test
    public void milisToStringWithZeroHoursTest() throws Exception {
        long ms = timeDuration.toMiliseconds(0, 0, 5, 0, 45);
        String message = timeDuration.milisToString(ms, "Days", "Hours", "Mins.");
        assertEquals("5 Days 00 Hours 45 Mins.", message);
    }

    @Test
    public void milisToStringWithZeroMinsTest() throws Exception {
        long ms = timeDuration.toMiliseconds(0, 0, 7, 12, 0);
        String message = timeDuration.milisToString(ms, "Days", "Hours", "Mins.");
        assertEquals("7 Days 12 Hours 00 Mins.", message);
    }
}