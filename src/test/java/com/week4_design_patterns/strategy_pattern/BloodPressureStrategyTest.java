package com.week4_design_patterns.strategy_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloodPressureStrategyTest {

    private BloodPressureStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new BloodPressureStrategy();
    }

    @Test
    public void testNoAlertWhenReadingsAreNormalAndStable() { // normal systolic and diastolic readings
        double[] normalReadings = {120, 80, 121, 81, 122, 82};
        assertFalse(strategy.checkAlert(normalReadings));
    }

    @Test
    public void testAlertForCriticalHighSystolic() { // systolic reading above 180
        double[] readings = {120, 80, 190, 85, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalLowSystolic() { // systolic reading below 90
        double[] readings = {120, 80, 88, 85, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalHighDiastolic() { // diastolic reading above 120
        double[] readings = {120, 80, 122, 125, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalLowDiastolic() { // diastolic reading below 60
        double[] readings = {120, 80, 122, 58, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForIncreasingSystolicTrend() { // systolic increasing > 10 twice
        double[] readings = {110, 70, 125, 70, 140, 70}; 
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForDecreasingDiastolicTrend() { // diastolic decreasing > 10 twice
        double[] readings = {120, 100, 120, 85, 120, 70}; 
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testNoAlertForTooFewReadings() { // fewer than 3 readings
        double[] shortReadings = {120, 80}; 
        assertFalse(strategy.checkAlert(shortReadings));
    }

    @Test
    public void testNoAlertForSmallChanges() { // all readings within normal range
        double[] slightFluctuations = {120, 80, 122, 81, 124, 83}; 
        assertFalse(strategy.checkAlert(slightFluctuations));
    }
}
