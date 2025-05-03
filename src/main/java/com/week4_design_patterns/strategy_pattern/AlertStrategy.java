package com.week4_design_patterns.strategy_pattern;

public interface AlertStrategy {
    boolean checkAlert(double[] readings);
}
