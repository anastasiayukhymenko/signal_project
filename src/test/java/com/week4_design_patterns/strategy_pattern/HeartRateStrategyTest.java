package com.week4_design_patterns.strategy_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeartRateStrategyTest {

    private HeartRateStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new HeartRateStrategy();
    }

    @Test
    public void testNoAlertWhenHeartRateIsWithinNormalRange() { // Test for normal heart rate
        double[] normalHeartRates = {75, 85, 90, 78, 88};
        assertFalse(strategy.checkAlert(normalHeartRates));
    }

    @Test
    public void testAlertForLowHeartRate() { // Test for low heart rate
        double[] lowHeartRates = {40, 45, 50, 60};
        assertTrue(strategy.checkAlert(lowHeartRates));
    }

    @Test
    public void testAlertForHighHeartRate() { // Test for high heart rate
        double[] highHeartRates = {125, 130, 140, 110};
        assertTrue(strategy.checkAlert(highHeartRates));
    }

    @Test
    public void testNoAlertForHeartRatesWithinNormalRange() { // Test for normal heart rate- no alert
        double[] normalReadings = {60, 72, 80, 90};
        assertFalse(strategy.checkAlert(normalReadings));
    }

    @Test
    public void testAlertForMixedHeartRatesWithOneCriticalLow() { // Test for mixed heart rates with one critical low
        double[] mixedHeartRates = {60, 72, 45, 90};
        assertTrue(strategy.checkAlert(mixedHeartRates));
    }

    @Test
    public void testAlertForMixedHeartRatesWithOneCriticalHigh() { // Test for mixed heart rates with one critical high
        double[] mixedHeartRates = {60, 72, 125, 90};
        assertTrue(strategy.checkAlert(mixedHeartRates));
    }

    @Test
    public void testNoAlertForEmptyReadings() { // Test for empty readings
        double[] emptyReadings = {};
        assertFalse(strategy.checkAlert(emptyReadings));
    }
}
