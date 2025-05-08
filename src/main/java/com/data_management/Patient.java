package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an empty list of patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since UNIX epoch
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
     * @param startTime the start of the time range, in milliseconds since UNIX
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     *         range
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
        return new ArrayList<>(patientRecords); // Return a copy to prevent external modification
    }
   /* public List<PatientRecord> getRecordsByType(String type) {
        return patientRecords.stream()
                .filter(record -> type.equals(record.getRecordType()))
                .collect(Collectors.toList());
    }

    */

    /*
     * Checks if this patient has a record of a specific type.
     * This method iterates through the patient's records to determine if any match the given type.
     *
     * @param recordType the type of record to check for, e.g., "HeartRate"
     * @return true if the patient has a record of the specified type, false otherwise
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
