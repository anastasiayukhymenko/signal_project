package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientRecordTest {

    private PatientRecord patientRecord;

    @BeforeEach
    void setUp() {
        // Create a new PatientRecord instance before each test
        patientRecord = new PatientRecord(1, 98.6, "HeartRate", 1714376789050L);
    }

    @Test
    void testConstructor() {
        // Ensure that the PatientRecord was constructed correctly
        assertEquals(1, patientRecord.getPatientId());
        assertEquals(98.6, patientRecord.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", patientRecord.getRecordType());
        assertEquals(1714376789050L, patientRecord.getTimestamp());
    }

    @Test
    void testGetPatientId() {
        // Verify that the patient ID is retrieved correctly
        assertEquals(1, patientRecord.getPatientId());
    }

    @Test
    void testGetMeasurementValue() {
        // Verify that the measurement value is retrieved correctly
        assertEquals(98.6, patientRecord.getMeasurementValue(), 0.01);
    }

    @Test
    void testGetRecordType() {
        // Verify that the record type is retrieved correctly
        assertEquals("HeartRate", patientRecord.getRecordType());
    }

    @Test
    void testGetTimestamp() {
        // Verify that the timestamp is retrieved correctly
        assertEquals(1714376789050L, patientRecord.getTimestamp());
    }

    @Test
    void testToString() {
        // Verify the string representation of the PatientRecord
        String expectedString = "Patient ID: 1, Record Type: HeartRate, Measurement Value: 98.6, Timestamp: 1714376789050";
        assertEquals(expectedString, patientRecord.toString());
    }

    @Test
    void testConstructor_InvalidMeasurementValue() {
        // Test invalid measurement value (e.g., negative)
        PatientRecord invalidRecord = new PatientRecord(2, -10.0, "BloodPressure", 1714376789051L);
        assertEquals(-10.0, invalidRecord.getMeasurementValue(), 0.01);
    }

    @Test
    void testConstructor_InvalidTimestamp() {
        // Test with invalid timestamp (e.g., negative)
        PatientRecord invalidRecord = new PatientRecord(3, 120.0, "ECG", -1714376789050L);
        assertEquals(-1714376789050L, invalidRecord.getTimestamp());
    }
}
