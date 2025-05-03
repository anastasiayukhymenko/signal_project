package com.week4_design_patterns.factory_method_pattern;

import com.alerts.Alert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BloodPressureAlertFactoryTest {

    @Test
    public void testCreateAlert() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("P003", "High Systolic", 1714724000L);

        assertNotNull(alert, "Alert should not be null");
        assertEquals("P003", alert.getPatientId(), "Patient ID should match input");
        assertEquals("Blood Pressure Alert", alert.getCondition(), "Condition should be set to 'Blood Pressure Alert'");
        assertEquals(1714724000L, alert.getTimestamp(), "Timestamp should match input");
    }
}
