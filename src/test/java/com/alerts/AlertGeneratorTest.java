package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AlertGeneratorTest {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        // Create a mock DataStorage
        dataStorage = mock(DataStorage.class);
        alertGenerator = new AlertGenerator(dataStorage);

        // Create a sample patient
        patient = new Patient(1);
        patient.addRecord(91.5, "BloodSaturation", System.currentTimeMillis());
        patient.addRecord(87.0, "BloodSaturation", System.currentTimeMillis() + 1000);
        patient.addRecord(89.0, "BloodSaturation", System.currentTimeMillis() + 2000);
    }

    @Test
    public void testLowSaturationAlert() {
        // Mock the data retrieval to return our patient records
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(patient.getAllRecords());

        // Call the method to evaluate alerts
       // alertGenerator.evaluateBloodSaturation(patient);

        // Verify if the alert was triggered
        // Note: You would normally verify the actual alert system, but here we simply assert for testing
        verify(dataStorage, times(1)).getRecords(anyInt(), anyLong(), anyLong());
    }

    @Test
    public void testRapidDropAlert() {
        // Mock the data retrieval to return our patient records
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(patient.getAllRecords());

        // Call the method to evaluate alerts
       // alertGenerator.evaluateBloodSaturation(patient);

        // Verify if the alert was triggered
        verify(dataStorage, times(1)).getRecords(anyInt(), anyLong(), anyLong());
    }
}
