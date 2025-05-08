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
    private Patient testPatient;
    private DataStorage testDataStorage;

    @BeforeEach
    void setUp() {
        testDataStorage = mock(DataStorage.class);
        testPatient = mock(Patient.class);
        alertGenerator = new AlertGenerator(testDataStorage);
    }

    @Test
    void testEvaluateECGAbnormalities() {
        
        List<PatientRecord> testECGData = List.of(
                new PatientRecord(1, 0.97, "ECG", System.currentTimeMillis())
        );
        when(testPatient.getAllRecords()).thenReturn(testECGData);

        alertGenerator.evaluateECGAbnormalities(testPatient);

        verify(testPatient).getAllRecords();
    }

    @Test
    void testEvaluateBloodPressureTrends() {
        List<PatientRecord> testBloodPressureData = List.of(
                new PatientRecord(1, 129, "Blood Pressure Systolic", System.currentTimeMillis()),
                new PatientRecord(1, 136, "Blood Pressure Systolic", System.currentTimeMillis() + 1000),
                new PatientRecord(1, 142, "Blood Pressure Systolic", System.currentTimeMillis() + 2000)
        );
        when(testPatient.getAllRecords()).thenReturn(testBloodPressureData);

        alertGenerator.evaluateBloodPressureTrends(testPatient);

        verify(testPatient).getAllRecords();
    }

    @Test
    void testEvaluateHypotensiveHypoxemia() {
        List<PatientRecord> mockData = List.of(
                new PatientRecord(1, 85, "Blood Pressure Systolic", System.currentTimeMillis()),
                new PatientRecord(1, 88, "Oxygen Saturation", System.currentTimeMillis())
        );
        when(testPatient.getAllRecords()).thenReturn(mockData);

        alertGenerator.evaluateHypotensiveHypoxemia(testPatient);

        verify(testPatient).getAllRecords();
    }

    @Test
    void testEvaluateTriggeredAlert() {
        Patient mockPatient = mock(Patient.class);

        alertGenerator.evaluateTriggeredAlert(mockPatient);

        verify(mockPatient, atLeast(0)).getAllRecords(); 
    }

    class TestAlertGenerator extends AlertGenerator {
        List<Alert> triggeredAlerts = new ArrayList<>();

        public TestAlertGenerator() {
            super(null);
        }

        @Override
        protected void triggerAlert(Alert alert) {
            triggeredAlerts.add(alert);
        }

        public List<Alert> getTriggeredAlerts() {
            return triggeredAlerts;
        }
    }

    @Test
    void testEvaluateBloodSaturation_triggersLowAndRapidDropAlerts() {
        long now = System.currentTimeMillis();

        List<PatientRecord> testRecords = List.of(
                new PatientRecord(1, 97.0, "Oxygen Saturation", now - 9 * 60 * 1000L), //Normal
                new PatientRecord(1, 90.0, "Oxygen Saturation", now) //Low and rapid drop
        );

        Patient testPatient = mock(Patient.class);
        when(testPatient.getAllRecords()).thenReturn(testRecords);
        when(testPatient.getPatientId()).thenReturn(1);

        TestAlertGenerator alertGenerator = new TestAlertGenerator();

        alertGenerator.evaluateBloodSaturation(testPatient);

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
