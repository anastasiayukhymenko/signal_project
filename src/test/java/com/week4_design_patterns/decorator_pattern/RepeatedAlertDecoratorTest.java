package com.week4_design_patterns.decorator_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepeatedAlertDecoratorTest {

    private Alert baseAlert;
    private RepeatedAlertDecorator repeatedAlert;

    @BeforeEach
    public void setUp() {
        baseAlert = mock(Alert.class);
        when(baseAlert.getPatientId()).thenReturn("P222");
        when(baseAlert.getCondition()).thenReturn("Irregular Heart Rate");
        when(baseAlert.getTimestamp()).thenReturn(1714711111L);

        repeatedAlert = new RepeatedAlertDecorator(baseAlert, 3);
    }

    @Test
    public void testGetPatientId() {
        assertEquals("P222", repeatedAlert.getPatientId(), "Patient ID should match base alert"); 
    }

    @Test
    public void testGetCondition() {
        assertEquals("Irregular Heart Rate - Repeated 3 times", repeatedAlert.getCondition(), "Condition should include repeat information");
    }

    @Test
    public void testGetTimestamp() {
        assertEquals(1714711111L, repeatedAlert.getTimestamp(), "Timestamp should match base alert");
    }

    @Test
    public void testToString() {
        String expected = "Alert{patientId='P222', condition='Irregular Heart Rate - Repeated 3 times', timestamp=1714711111}, Repeat Count: 3";
        assertEquals(expected, repeatedAlert.toString(), "toString should include all formatted values");
    }
}
