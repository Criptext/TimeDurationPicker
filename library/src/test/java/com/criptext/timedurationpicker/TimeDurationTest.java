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
}