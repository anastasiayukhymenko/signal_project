package com.week4_design_patterns.strategy_pattern;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(double[] readings) {
        for (double rate : readings) {
            if (rate < 50 || rate > 120) return true;
        }
        return false;
    }
}
