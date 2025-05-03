package com.week4_design_patterns.strategy_pattern;


public class BloodPressureStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(double[] readings) {
        if (readings.length < 6) return false;

        // Interpret readings: [sys1, dia1, sys2, dia2, sys3, dia3]
        for (int i = 0; i < readings.length; i += 2) {
            double sys = readings[i];
            double dia = readings[i + 1];

            // Critical threshold check
            if (sys > 180 || sys < 90 || dia > 120 || dia < 60) return true;
        }

        // Trend detection (3 consecutive systolic or diastolic increasing or decreasing by >10)
        for (int i = 0; i <= readings.length - 6; i += 2) {
            double s1 = readings[i], d1 = readings[i + 1];
            double s2 = readings[i + 2], d2 = readings[i + 3];
            double s3 = readings[i + 4], d3 = readings[i + 5];

            boolean incSys = s2 - s1 > 10 && s3 - s2 > 10;
            boolean decSys = s1 - s2 > 10 && s2 - s3 > 10;
            boolean incDia = d2 - d1 > 10 && d3 - d2 > 10;
            boolean decDia = d1 - d2 > 10 && d2 - d3 > 10;

            if (incSys || decSys || incDia || decDia) return true;
        }

        return false;
    }
}
