package com.week4_design_patterns.factory_method_pattern;

import com.alerts.Alert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ECGAlertFactoryTest {

    @Test
    public void testCreateAlert() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("P696", "Abnormal ECG results", 1714725000L);

        assertNotNull(alert, "Alert should not be empty");
        assertEquals("P696", alert.getPatientId(), "Patient ID should match input");
        assertEquals("ECG Alert", alert.getCondition(), "Condition should be set to 'ECG Alert'");
        assertEquals(1714725000L, alert.getTimestamp(), "Timestamp should match input");
    }
}
