package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient data allowing for the addition and retrieval
 * of medical records based on specified criteria
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;



    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp
     * @param measurementValue  measurement value to store in the record
     * @param recordType        type of record, e.g., "HeartRate", "BloodPressure"
     * @param timestamp         time at which the measurement was taken, in
     *                         milliseconds
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }
    public int getPatientId() {
        return patientId;
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @param startTime  start of the time range in milliseconds
     * @param endTime    end of the time range in milliseconds
     * @return a list of PatientRecord objects that fall within the specified timeline
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> filteredRecords = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }
    public List<PatientRecord> getAllRecords() {
        return new ArrayList<>(patientRecords);
    }

    /*
     * Checks if this patient has a record of a specific type.
     * This method iterates through the patient's records to determine if any match the given type.
     *
     * @param recordType  type of record to check
     * @return true if  patient has a record of the specified type
     */
    public boolean hasRecord(String recordType) {
        for (PatientRecord record : patientRecords) {
            if (record.getRecordType().equals(recordType)) {
                return true;
            }
        }
        return false;
    }
}
