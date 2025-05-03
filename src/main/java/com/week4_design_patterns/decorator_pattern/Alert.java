package com.week4_design_patterns.decorator_pattern;

public interface Alert {
    String getPatientId();
    String getCondition();
    long getTimestamp();
    String toString();  
}
