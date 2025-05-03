package com.week4_design_patterns.factory_method_pattern;

import com.alerts.Alert;

public class BloodPressureAlertFactory extends AlertFactory {
    
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Blood Pressure Alert", timestamp);
    }
}
