package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientRecordTest {

    private PatientRecord patientRecord;

    @BeforeEach
    void setUp() {
        // Create a new PatientRecord instance before each test
        patientRecord = new PatientRecord(123, 99.6, "HeartRate", 1714376789050L);
    }

    @Test
    void testConstructor() {
        assertEquals(123, patientRecord.getPatientId());
        assertEquals(99.6, patientRecord.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", patientRecord.getRecordType());
        assertEquals(1714376789050L, patientRecord.getTimestamp());
    }

    @Test
    void testGetPatientId() {
        assertEquals(123, patientRecord.getPatientId());
    }

    @Test
    void testGetMeasurementValue() {
        assertEquals(99.6, patientRecord.getMeasurementValue(), 0.01);
    }

    @Test
    void testGetRecordType() {
        assertEquals("HeartRate", patientRecord.getRecordType());
    }

    @Test
    void testGetTimestamp() {
        assertEquals(1714376789050L, patientRecord.getTimestamp());
    }

    @Test
    void testToString() {
        String expectedString = "Patient ID: 123, Record Type: HeartRate, Measurement Value: 99.6, Timestamp: 1714376789050";
        assertEquals(expectedString, patientRecord.toString());
    }

    @Test
    void testConstructor_InvalidMeasurementValue() {
        PatientRecord invalidRecord = new PatientRecord(2, -10.0, "BloodPressure", 1714376789051L);
        assertEquals(-10.0, invalidRecord.getMeasurementValue(), 0.01);
    }

    @Test
    void testConstructor_InvalidTimestamp() {
        PatientRecord invalidRecord = new PatientRecord(3, 120.0, "ECG", -1714376789050L);
        assertEquals(-1714376789050L, invalidRecord.getTimestamp());
    }
}
