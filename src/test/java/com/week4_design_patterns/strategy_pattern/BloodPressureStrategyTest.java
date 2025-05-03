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
    public void testNoAlertWhenReadingsAreNormalAndStable() {
        double[] normalReadings = {120, 80, 121, 81, 122, 82};
        assertFalse(strategy.checkAlert(normalReadings));
    }

    @Test
    public void testAlertForCriticalHighSystolic() {
        double[] readings = {120, 80, 190, 85, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalLowSystolic() {
        double[] readings = {120, 80, 88, 85, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalHighDiastolic() {
        double[] readings = {120, 80, 122, 125, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForCriticalLowDiastolic() {
        double[] readings = {120, 80, 122, 58, 122, 82};
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForIncreasingSystolicTrend() {
        double[] readings = {110, 70, 125, 70, 140, 70}; // systolic increasing > 10 twice
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testAlertForDecreasingDiastolicTrend() {
        double[] readings = {120, 100, 120, 85, 120, 70}; // diastolic decreasing > 10 twice
        assertTrue(strategy.checkAlert(readings));
    }

    @Test
    public void testNoAlertForTooFewReadings() {
        double[] shortReadings = {120, 80}; // Only one reading
        assertFalse(strategy.checkAlert(shortReadings));
    }

    @Test
    public void testNoAlertForSmallChanges() {
        double[] slightFluctuations = {120, 80, 122, 81, 124, 83}; // all within normal range
        assertFalse(strategy.checkAlert(slightFluctuations));
    }
}
