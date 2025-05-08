package com.week4_design_patterns.strategy_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OxygenSaturationStrategyTest {

    private OxygenSaturationStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new OxygenSaturationStrategy();
    }

    @Test
    public void testNoAlertWhenOxygenSaturationIsAboveThreshold() { //normal 
        double[] normalOxygenLevels = {92, 95, 98, 93};
        assertFalse(strategy.checkAlert(normalOxygenLevels));
    }

    @Test
    public void testAlertForLowOxygenSaturation() { // low 
        double[] lowOxygenLevels = {89, 92, 85, 91};
        assertTrue(strategy.checkAlert(lowOxygenLevels));
    }

    @Test
    public void testAlertForSingleLowOxygenSaturation() { // single low
        double[] singleLowOxygen = {91, 89, 95, 94};
        assertTrue(strategy.checkAlert(singleLowOxygen));
    }

    @Test
    public void testNoAlertForAllNormalOxygenSaturation() { // all normal- no alert
        double[] allNormalLevels = {92, 96, 93, 95};
        assertFalse(strategy.checkAlert(allNormalLevels));
    }

    @Test
    public void testAlertForMixedOxygenLevelsWithOneCriticalLow() { // mixed with one critical low
        double[] mixedLevels = {92, 95, 89, 98}; 
        assertTrue(strategy.checkAlert(mixedLevels));
    }

    @Test
    public void testNoAlertForEmptyReadings() { // empty readings
        double[] emptyReadings = {};
        assertFalse(strategy.checkAlert(emptyReadings));
    }
}
