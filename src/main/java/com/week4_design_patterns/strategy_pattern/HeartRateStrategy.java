package com.week4_design_patterns.strategy_pattern;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(double[] readings) {
        for (double r : readings) {
            if (r < 50 || r > 120) return true;
        }
        return false;
    }
}
