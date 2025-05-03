package com.week4_design_patterns.decorator_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PriorityAlertDecoratorTest {

    private Alert baseAlert;
    private PriorityAlertDecorator priorityAlert;

    @BeforeEach
    public void setUp() {
        baseAlert = mock(Alert.class);
        when(baseAlert.getPatientId()).thenReturn("P456");
        when(baseAlert.getCondition()).thenReturn("Low Oxygen");
        when(baseAlert.getTimestamp()).thenReturn(1714709999L);

        priorityAlert = new PriorityAlertDecorator(baseAlert, "HIGH");
    }

    @Test
    public void testGetPatientId() {
        assertEquals("P456", priorityAlert.getPatientId());
    }

    @Test
    public void testGetCondition() {
        assertEquals("Low Oxygen - Priority: HIGH", priorityAlert.getCondition());
    }

    @Test
    public void testGetTimestamp() {
        assertEquals(1714709999L, priorityAlert.getTimestamp());
    }

    @Test
    public void testToString() {
        String expected = "Alert{patientId='P456', condition='Low Oxygen - Priority: HIGH', timestamp=1714709999}, Priority Level: HIGH";
        assertEquals(expected, priorityAlert.toString());
    }
}
