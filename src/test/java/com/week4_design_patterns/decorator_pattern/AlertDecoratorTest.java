package com.week4_design_patterns.decorator_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertDecoratorTest {

    private Alert baseAlert;
    private AlertDecorator decoratedAlert;

    @BeforeEach
    public void setUp() {
        baseAlert = mock(Alert.class);
        when(baseAlert.getPatientId()).thenReturn("P648");
        when(baseAlert.getCondition()).thenReturn("High Blood Pressure");
        when(baseAlert.getTimestamp()).thenReturn(1714700000L);

        decoratedAlert = new AlertDecorator(baseAlert, "Urgent Attention Required");
    }

    // Test the constructor of AlertDecorator
    @Test
    public void testGetPatientId() {
        assertEquals("P648", decoratedAlert.getPatientId());
    }

    // Test the getCondition method
    @Test
    public void testGetCondition() {
        String expected = "High Blood Pressure - Urgent Attention Required";
        assertEquals(expected, decoratedAlert.getCondition());
    }

    // Test the getTimestamp method
    @Test
    public void testGetTimestamp() {
        assertEquals(1714700000L, decoratedAlert.getTimestamp());
    }

    // Test the toString method
    @Test
    public void testToString() {
        String expected = "Alert{patientId='P648', condition='High Blood Pressure - Urgent Attention Required', timestamp=1714700000}";
        assertEquals(expected, decoratedAlert.toString());
    }
}
