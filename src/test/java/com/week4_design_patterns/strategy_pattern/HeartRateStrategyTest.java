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
    public void testNoAlertWhenHeartRateIsWithinNormalRange() {
        double[] normalHeartRates = {75, 85, 90, 78, 88};
        assertFalse(strategy.checkAlert(normalHeartRates));
    }

    @Test
    public void testAlertForLowHeartRate() {
        double[] lowHeartRates = {40, 45, 50, 60};
        assertTrue(strategy.checkAlert(lowHeartRates));
    }

    @Test
    public void testAlertForHighHeartRate() {
        double[] highHeartRates = {125, 130, 140, 110};
        assertTrue(strategy.checkAlert(highHeartRates));
    }

    @Test
    public void testNoAlertForHeartRatesWithinNormalRange() {
        double[] normalReadings = {60, 72, 80, 90};
        assertFalse(strategy.checkAlert(normalReadings));
    }

    @Test
    public void testAlertForMixedHeartRatesWithOneCriticalLow() {
        double[] mixedHeartRates = {60, 72, 45, 90};
        assertTrue(strategy.checkAlert(mixedHeartRates));
    }

    @Test
    public void testAlertForMixedHeartRatesWithOneCriticalHigh() {
        double[] mixedHeartRates = {60, 72, 125, 90};
        assertTrue(strategy.checkAlert(mixedHeartRates));
    }

    @Test
    public void testNoAlertForEmptyReadings() {
        double[] emptyReadings = {};
        assertFalse(strategy.checkAlert(emptyReadings));
    }
}
