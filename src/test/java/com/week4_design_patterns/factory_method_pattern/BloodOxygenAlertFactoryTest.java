package com.week4_design_patterns.factory_method_pattern;

import com.alerts.Alert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BloodOxygenAlertFactoryTest {

    @Test
    public void testCreateAlert() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("P002", "Low SpO2", 1714723000L);

        assertNotNull(alert, "Alert should not be null");
        assertEquals("P002", alert.getPatientId(), "Patient ID should match");
        assertEquals("Blood Oxygen Alert", alert.getCondition(), "Condition should be fixed as 'Blood Oxygen Alert'");
        assertEquals(1714723000L, alert.getTimestamp(), "Timestamp should match input");
    }
}
