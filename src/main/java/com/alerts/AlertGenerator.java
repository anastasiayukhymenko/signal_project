package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.DataStorage;
import com.data_management.Patient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private static class BloodPressurePair {
        long timestamp;
        double systolic;
        double diastolic;

        BloodPressurePair(long timestamp, double systolic, double diastolic) {
            this.timestamp = timestamp;
            this.systolic = systolic;
            this.diastolic = diastolic;
        }
    }


    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        //evaluateBloodSaturation(patient);
        // You can add other evaluations here as needed
        evaluateBloodPressureTrends(patient);
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }

    public void evaluateBloodPressureTrends(Patient patient) {
        List<PatientRecord> records = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equals("Systolic") || r.getRecordType().equals("Diastolic"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp).reversed())
                .collect(Collectors.toList());

        // Group systolic and diastolic readings by timestamp
        Map<Long, Double> systolicMap = new TreeMap<>(Collections.reverseOrder());
        Map<Long, Double> diastolicMap = new TreeMap<>(Collections.reverseOrder());

        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Systolic")) {
                systolicMap.put(r.getTimestamp(), r.getMeasurementValue());
            } else if (r.getRecordType().equals("Diastolic")) {
                diastolicMap.put(r.getTimestamp(), r.getMeasurementValue());
            }
        }

        // Match and pair up to 3 BP readings
        List<BloodPressurePair> bpList = new ArrayList<>();
        for (Long ts : systolicMap.keySet()) {
            if (diastolicMap.containsKey(ts)) {
                bpList.add(new BloodPressurePair(ts, systolicMap.get(ts), diastolicMap.get(ts)));
            }
            if (bpList.size() == 3) break;
        }

        if (bpList.size() < 3) return;

        // Check for trend alerts
        boolean increasingSys = bpList.get(2).systolic - bpList.get(1).systolic > 10 &&
                bpList.get(1).systolic - bpList.get(0).systolic > 10;

        boolean decreasingSys = bpList.get(0).systolic - bpList.get(1).systolic > 10 &&
                bpList.get(1).systolic - bpList.get(2).systolic > 10;

        boolean increasingDia = bpList.get(2).diastolic - bpList.get(1).diastolic > 10 &&
                bpList.get(1).diastolic - bpList.get(0).diastolic > 10;

        boolean decreasingDia = bpList.get(0).diastolic - bpList.get(1).diastolic > 10 &&
                bpList.get(1).diastolic - bpList.get(2).diastolic > 10;

        if (increasingSys || decreasingSys || increasingDia || decreasingDia) {
            triggerAlert(new Alert(String.valueOf(patient.getPatientId()),
                    "Trend Alert: Significant BP change over 3 readings",
                    System.currentTimeMillis()));
        }

        // Check for critical thresholds
        for (BloodPressurePair bp : bpList) {
            if (bp.systolic > 180 || bp.systolic < 90 || bp.diastolic > 120 || bp.diastolic < 60) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()),
                        "Critical Threshold Alert: Abnormal BP reading",
                        System.currentTimeMillis()));
                break;
            }
        }
    }
}
