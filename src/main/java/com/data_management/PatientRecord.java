package com.data_management;

/**
 * Represents a single record of patient data at a specific time.
 * This class stores all necessary details for a single observation or measurement
 * including type of record, the measurement value, and the exact timestamp when the measurement was
 * taken.
 */
public class PatientRecord {
    private int patientId;
    private String recordType;
    private double measurementValue;
    private long timestamp;


    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    /**
     * Returns the patient ID associated with this record.
     * @return the patient ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Returns the measurement value of this record.
     * @return the measurement value
     */
    public double getMeasurementValue() {
        return measurementValue;
    }

    /**
     * Returns the timestamp when this record was taken.
     * @return the timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the type of record
     * @return the record type
     */
    public String getRecordType() {
        return recordType;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Record Type: " + recordType + ", Measurement Value: "
                + measurementValue + ", Timestamp: " + timestamp;
    }
}
