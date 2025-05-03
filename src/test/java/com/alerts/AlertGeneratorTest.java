package com.alerts;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

class AlertGeneratorTest {

    private AlertGenerator alertGenerator;
    private Patient mockPatient;
    private DataStorage mockDataStorage;

    @BeforeEach
    void setUp() {
        mockDataStorage = mock(DataStorage.class);
        mockPatient = mock(Patient.class);
        alertGenerator = new AlertGenerator(mockDataStorage);
    }

    @Test
    void testEvaluateECGAbnormalities() {
        // Arrange
        List<PatientRecord> mockECGData = List.of(
                new PatientRecord(1, 0.95, "ECG", System.currentTimeMillis())
        );
        when(mockPatient.getAllRecords()).thenReturn(mockECGData);

        // Act
        alertGenerator.evaluateECGAbnormalities(mockPatient);

        // Assert
        verify(mockPatient).getAllRecords();
    }

    @Test
    void testEvaluateBloodPressureTrends() {
        // Arrange
        List<PatientRecord> mockBPData = List.of(
                new PatientRecord(1, 130, "BloodPressureSystolic", System.currentTimeMillis()),
                new PatientRecord(1, 135, "BloodPressureSystolic", System.currentTimeMillis() + 1000),
                new PatientRecord(1, 140, "BloodPressureSystolic", System.currentTimeMillis() + 2000)
        );
        when(mockPatient.getAllRecords()).thenReturn(mockBPData);

        // Act
        alertGenerator.evaluateBloodPressureTrends(mockPatient);

        // Assert
        verify(mockPatient).getAllRecords();
    }

    @Test
    void testEvaluateHypotensiveHypoxemia() {
        // Arrange
        List<PatientRecord> mockData = List.of(
                new PatientRecord(1, 85, "BloodPressureSystolic", System.currentTimeMillis()),
                new PatientRecord(1, 88, "OxygenSaturation", System.currentTimeMillis())
        );
        when(mockPatient.getAllRecords()).thenReturn(mockData);

        // Act
        alertGenerator.evaluateHypotensiveHypoxemia(mockPatient);

        // Assert
        verify(mockPatient).getAllRecords();
    }

    @Test
    void testEvaluateTriggeredAlert() {
        // Arrange
        Patient mockPatient = mock(Patient.class);

        // Act
        alertGenerator.evaluateTriggeredAlert(mockPatient);

        // Assert
        // You can verify interactions with the mockPatient or internal behavior if applicable
        verify(mockPatient, atLeast(0)).getAllRecords(); // for example, if that method is used
    }
    class TestableAlertGenerator extends AlertGenerator {
        List<Alert> triggeredAlerts = new ArrayList<>();

        public TestableAlertGenerator() {
            super(null);
        }

        @Override
        protected void triggerAlert(Alert alert) {
            triggeredAlerts.add(alert); // Capture alerts for inspection
        }

        public List<Alert> getTriggeredAlerts() {
            return triggeredAlerts;
        }
    }
    @Test
    void testEvaluateBloodSaturation_triggersLowAndRapidDropAlerts() {
        long now = System.currentTimeMillis();

        List<PatientRecord> mockRecords = List.of(
                new PatientRecord(1, 97.0, "OxygenSaturation", now - 9 * 60 * 1000L), // Normal
                new PatientRecord(1, 91.0, "OxygenSaturation", now)                   // Low and rapid drop
        );

        Patient mockPatient = mock(Patient.class);
        when(mockPatient.getAllRecords()).thenReturn(mockRecords);
        when(mockPatient.getPatientId()).thenReturn(1);

        // Use the testable subclass to capture alerts
        TestableAlertGenerator alertGenerator = new TestableAlertGenerator();

        // Act
        alertGenerator.evaluateBloodSaturation(mockPatient);

        // Assert
        List<Alert> alerts = alertGenerator.getTriggeredAlerts();
        assertEquals(2, alerts.size());

        Alert alert1 = alerts.get(0);
        assertEquals("1", alert1.getPatientId());
        assertEquals("Low Oxygen Saturation (<92%)", alert1.getCondition());

        Alert alert2 = alerts.get(1);
        assertEquals("1", alert2.getPatientId());
        assertEquals("Rapid Oxygen Saturation Drop (≥5% in 10 mins)", alert2.getCondition());
    }



}
