package com.alerts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlertTest {

    @Test
    void testAlertConstructorAndGetters() {
        String expectedPatientId = "123";
        String expectedCondition = "Low Oxygen";
        long expectedTimestamp = System.currentTimeMillis();

        Alert alert = new Alert(expectedPatientId, expectedCondition, expectedTimestamp);

        assertEquals(expectedPatientId, alert.getPatientId());
        assertEquals(expectedCondition, alert.getCondition());
        assertEquals(expectedTimestamp, alert.getTimestamp());
    }
}
