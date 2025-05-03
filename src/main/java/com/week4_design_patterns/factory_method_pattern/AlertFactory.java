package com.week4_design_patterns.factory_method_pattern;

import com.alerts.Alert;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}
