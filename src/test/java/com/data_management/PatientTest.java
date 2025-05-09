package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(17);
    }

    @Test
    void testGetPatientId() {
        assertEquals(17, patient.getPatientId());
    }

    @Test
    void testAddAndGetAllRecords() {
        patient.addRecord(125.5, "BloodPressure", 1714670000000L);
        patient.addRecord(76.0, "HeartRate", 1714670100000L);

        List<PatientRecord> records = patient.getAllRecords();
        assertEquals(2, records.size());

        PatientRecord first = records.get(0);
        assertEquals(125.5, first.getMeasurementValue());
        assertEquals("BloodPressure", first.getRecordType());
        assertEquals(1714670000000L, first.getTimestamp());
    }

    @Test
    void testGetRecordsWithinRange() {
        patient.addRecord(98.0, "OxygenSaturation", 1714670000000L);
        patient.addRecord(76.0, "HeartRate", 1714671000000L);
        patient.addRecord(130.0, "BloodPressure", 1714672000000L);

        List<PatientRecord> filtered = patient.getRecords(1714670000000L, 1714671000000L);
        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(r -> r.getRecordType().equals("OxygenSaturation")));
        assertTrue(filtered.stream().anyMatch(r -> r.getRecordType().equals("HeartRate")));
    }

    @Test
    void testGetRecordsReturnsEmptyWhenOutOfRange() {
        patient.addRecord(70.0, "HeartRate", 1714670000000L);

        List<PatientRecord> filtered = patient.getRecords(1714680000000L, 1714690000000L);
        assertTrue(filtered.isEmpty());
    }
}
