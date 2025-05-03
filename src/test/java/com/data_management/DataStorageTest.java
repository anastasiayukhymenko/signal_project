package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class DataStorageTest {

    @BeforeEach
    void clearSingletonInstance() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();
    }

    @Test
    void testAddAndGetRecords() {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
         DataReader reader = Mockito.mock(DataReader.class);

        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testAddPatientData_NewPatient() {
        // Add new patient data
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "HeartRate", 1714376789050L);

        // Check if the data has been added correctly
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789050L);
        assertEquals(1, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals("HeartRate", records.get(0).getRecordType());
    }

    @Test
    void testAddPatientData_ExistingPatient() {
        DataStorage storage = DataStorage.getInstance();
        // Add data to an existing patient
        storage.addPatientData(1, 100.0, "HeartRate", 1714376789050L);
        storage.addPatientData(1, 120.0, "HeartRate", 1714376789051L);

        // Retrieve all records for the patient
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);

        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(120.0, records.get(1).getMeasurementValue());
    }

    @Test
    void testGetRecords_ValidTimeRange() {
        DataStorage storage = DataStorage.getInstance();
        // Add data to a patient
        storage.addPatientData(1, 100.0, "HeartRate", 1714376789050L);
        storage.addPatientData(1, 120.0, "HeartRate", 1714376789051L);

        // Retrieve records in a specific time range
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);

        assertEquals(2, records.size());
    }

    @Test
    void testGetRecords_NoRecordsInRange() {
        DataStorage storage = DataStorage.getInstance();
        // Add data to a patient
        storage.addPatientData(1, 100.0, "HeartRate", 1714376789050L);

        // Retrieve records in a time range where no records exist
        List<PatientRecord> records = storage.getRecords(1, 1714376789051L, 1714376789052L);

        assertTrue(records.isEmpty());
    }

    @Test
    void testGetAllPatients() {
        DataStorage storage = DataStorage.getInstance();
        // Add data for multiple patients
        storage.addPatientData(1, 100.0, "HeartRate", 1714376789050L);
        storage.addPatientData(2, 120.0, "BloodPressure", 1714376789051L);

        // Retrieve all patients
        List<Patient> patients = storage.getAllPatients();

        assertEquals(2, patients.size());
        assertTrue(patients.stream().anyMatch(patient -> patient.getPatientId() == 1));
        assertTrue(patients.stream().anyMatch(patient -> patient.getPatientId() == 2));
    }
    
}
