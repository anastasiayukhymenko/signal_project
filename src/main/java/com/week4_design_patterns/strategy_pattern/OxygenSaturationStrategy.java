package com.week4_design_patterns.strategy_pattern;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(double[] readings) {
        for (double rate : readings) {
            if (rate < 90) return true;
        }
        return false;
    }
}
