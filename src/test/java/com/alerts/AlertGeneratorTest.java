/*package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertGeneratorTest {

    private DataStorage mockDataStorage;
    private Patient mockPatient;
    private AlertGenerator alertGenerator;
    private List<Alert> triggeredAlerts;

  /*  @BeforeEach
    void setUp() {
        // Initialize mocks
        mockDataStorage = mock(DataStorage.class);
        mockPatient = mock(Patient.class);

        // Create AlertGenerator with a spy to capture alerts
        alertGenerator = new AlertGenerator(mockDataStorage) {
            //@Override
            protected void triggerAlert(Alert alert) {
                if (triggeredAlerts == null) {
                    triggeredAlerts = new ArrayList<>();
                }
                triggeredAlerts.add(alert);
                super.triggerAlert(alert); // Call original if needed
            }
        };
        triggeredAlerts = new ArrayList<>();
    }



    @Test
    void testEvaluateBloodPressureTrendsIncreasing() {
        List<PatientRecord> records = createTestRecords(
                new double[]{100, 115, 130},
                new String[]{"Systolic", "Systolic", "Systolic"},
                new long[]{1000, 2000, 3000}
        );

        when(mockPatient.getAllRecords()).thenReturn(records);
        when(mockPatient.getPatientId()).thenReturn(1);

        alertGenerator.evaluateData(mockPatient);

        assertFalse(triggeredAlerts.isEmpty());
        assertTrue(triggeredAlerts.get(0).getCondition().contains("Trend Alert"));
    }

    @Test
    void testEvaluateCriticalSystolicHigh() {
        List<PatientRecord> records = createTestRecords(
                new double[]{185},
                new String[]{"Systolic"},
                new long[]{1000}
        );

        when(mockPatient.getAllRecords()).thenReturn(records);
        when(mockPatient.getPatientId()).thenReturn(1);

        alertGenerator.evaluateData(mockPatient);

        assertFalse(triggeredAlerts.isEmpty());
        assertTrue(triggeredAlerts.get(0).getCondition().contains("Critical Threshold Alert"));
    }

    @Test
    void testEvaluateBloodSaturationLow() {
        List<PatientRecord> records = createTestRecords(
                new double[]{91},
                new String[]{"OxygenSaturation"},
                new long[]{1000}
        );

        when(mockPatient.getAllRecords()).thenReturn(records);
        when(mockPatient.getPatientId()).thenReturn(1);

        alertGenerator.evaluateData(mockPatient);

        assertFalse(triggeredAlerts.isEmpty());
        assertTrue(triggeredAlerts.get(0).getCondition().contains("Low Oxygen Saturation"));
    }

    @Test
    void testNoAlertForNormalValues() {
        List<PatientRecord> records = createTestRecords(
                new double[]{120, 80, 98},
                new String[]{"Systolic", "Diastolic", "OxygenSaturation"},
                new long[]{1000, 2000, 3000}
        );

        when(mockPatient.getAllRecords()).thenReturn(records);
        when(mockPatient.getPatientId()).thenReturn(1);

        alertGenerator.evaluateData(mockPatient);

        assertTrue(triggeredAlerts.isEmpty());
    }

    private List<PatientRecord> createTestRecords(double[] values, String[] types, long[] timestamps) {
        List<PatientRecord> records = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            records.add(new PatientRecord(1, values[i], types[i], timestamps[i]));
        }
        return records;
    }
}

 */