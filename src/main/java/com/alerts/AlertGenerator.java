package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @code AlertGenerator class is responsible for monitoring patient data
 * and generating alerts when certain given conditions are met. 
 * This class relies on a DataStorage instance to access patient data and evaluate
 * it against specific criteria.
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
     * @code AlertGenerator with a specified
     * @code DataStorage is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates patient's data to determine if any alert conditions are met. 
     * If a condition is met, an alert is triggered via the {@link #triggerAlert} method. 
     * This method should define the specific conditions under which an alert will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */

    public void evaluateData(Patient patient) {
        evaluateBloodPressureTrends(patient);
        evaluateBloodSaturation(patient);
        evaluateHypotensiveHypoxemia(patient);
        evaluateECGAbnormalities(patient);
        evaluateTriggeredAlert(patient);
    }

    /**
     * @code triggerAlert triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */

    // made class from private (originally) to protected (because can not test )
    protected void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }

    public void evaluateBloodPressureTrends(Patient patient) {
        List<PatientRecord> records = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equals("Systolic") || r.getRecordType().equals("Diastolic"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp).reversed())
                .collect(Collectors.toList());

        Map<Long, Double> systolicMap = new TreeMap<>(Collections.reverseOrder());
        Map<Long, Double> diastolicMap = new TreeMap<>(Collections.reverseOrder());

        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Systolic")) {
                systolicMap.put(r.getTimestamp(), r.getMeasurementValue());
            } else if (r.getRecordType().equals("Diastolic")) {
                diastolicMap.put(r.getTimestamp(), r.getMeasurementValue());
            }
        }

        List<BloodPressurePair> bpList = new ArrayList<>();
        for (Long ts : systolicMap.keySet()) {
            if (diastolicMap.containsKey(ts)) {
                bpList.add(new BloodPressurePair(ts, systolicMap.get(ts), diastolicMap.get(ts)));
            }
            if (bpList.size() == 3) break;
        }

        if (bpList.size() < 3) return;

        boolean increasingSystolic = bpList.get(2).systolic - bpList.get(1).systolic > 10 &&
                bpList.get(1).systolic - bpList.get(0).systolic > 10;

        boolean decreasingSystolic = bpList.get(0).systolic - bpList.get(1).systolic > 10 &&
                bpList.get(1).systolic - bpList.get(2).systolic > 10;

        boolean increasingDiastolic = bpList.get(2).diastolic - bpList.get(1).diastolic > 10 &&
                bpList.get(1).diastolic - bpList.get(0).diastolic > 10;

        boolean decreasingDiastolic = bpList.get(0).diastolic - bpList.get(1).diastolic > 10 &&
                bpList.get(1).diastolic - bpList.get(2).diastolic > 10;

        if (increasingSystolic || decreasingSystolic || increasingDiastolic || decreasingDiastolic) {
            triggerAlert(new Alert(String.valueOf(patient.getPatientId()),
                    "Blood Pressure Trend Alert: Significant change detected",
                    System.currentTimeMillis()));
        }

        for (BloodPressurePair bp : bpList) {
            if (bp.systolic > 180 || bp.systolic < 90 || bp.diastolic > 120 || bp.diastolic < 60) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()),
                        "Critical Threshold Alert: Abnormal blood pressure reading",
                        System.currentTimeMillis()));
                break;
            }
        }

    }

    public void evaluateBloodSaturation(Patient patient) {
        List<PatientRecord> records = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equalsIgnoreCase("Oxygen Saturation"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        for (int i = 0; i < records.size(); i++) {
            PatientRecord current = records.get(i);

            // Low Saturation Alert
            if (current.getMeasurementValue() < 92.0) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Low Oxygen Saturation (<92%)",
                        current.getTimestamp()
                ));
            }

            // Rapid Drop Alert (checks if a previous record within 10 minutes exists if yes, checks if the drop is >= 5%)
            for (int j = i - 1; j >= 0; j--) {
                PatientRecord previous = records.get(j);
                long timeDiff = current.getTimestamp() - previous.getTimestamp();

                if (timeDiff > 10 * 60 * 1000L) {
                    break; 
                }

                double drop = previous.getMeasurementValue() - current.getMeasurementValue();
                if (drop >= 5.0) {
                    triggerAlert(new Alert(
                            String.valueOf(patient.getPatientId()),
                            "Rapid Oxygen Saturation Drop (≥5% in 10 mins)",
                            current.getTimestamp()
                    ));
                    break; 
                }
            }
        }
    }

    public void evaluateHypotensiveHypoxemia(Patient patient) {
        List<PatientRecord> records = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equals("Systolic") || r.getRecordType().equals("Blood Oxygen Saturation"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp).reversed())
                .collect(Collectors.toList());
    
        Double latestSystolic = null;
        Double latestOxygen = null;
    
        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Systolic") && latestSystolic == null) {
                latestSystolic = r.getMeasurementValue();
            } else if (r.getRecordType().equals("Blood Oxygen Saturation") && latestOxygen == null) {
                latestOxygen = r.getMeasurementValue();
            }
    
            if (latestSystolic != null && latestOxygen != null) break;
        }
    
        if (latestSystolic == null || latestOxygen == null) return;
    
        if (latestSystolic < 90 && latestOxygen < 92) {
            triggerAlert(new Alert(
                    String.valueOf(patient.getPatientId()),
                    "Hypotensive Hypoxemia Alert: Blood Pressure = " + latestSystolic + " mmHg, SpO₂ = " + latestOxygen + "%",
                    System.currentTimeMillis()
            ));
        }
    }
    
    public void evaluateECGAbnormalities(Patient patient) {
        List<PatientRecord> ecgRecords = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equals("ECG"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp))
                .collect(Collectors.toList());
    
        int windowSize = 10;
        double thresholdMultiplier = 1.5; 
    
        if (ecgRecords.size() < windowSize) return;
    
        Deque<Double> window = new LinkedList<>();
        double sum = 0;
    
        for (PatientRecord record : ecgRecords) {
            double value = record.getMeasurementValue();
    
            if (window.size() == windowSize) {
                sum -= window.removeFirst();
            }
    
            window.addLast(value);
            sum += value;
    
            if (window.size() == windowSize) {
                double avg = sum / windowSize;
    
                if (value > avg * thresholdMultiplier) {
                    triggerAlert(new Alert(
                            String.valueOf(patient.getPatientId()),
                            "ECG Alert: Peak value " + value + " exceeds average " + avg,
                            record.getTimestamp()
                    ));
                    break; 
                }
            }
        }
    }

    public void evaluateTriggeredAlert(Patient patient) {
        Optional<PatientRecord> latestTriggeredRecord = patient.getAllRecords().stream()
                .filter(r -> r.getRecordType().equals("Triggered Alert"))
                .max(Comparator.comparingLong(PatientRecord::getTimestamp));
    
        if (latestTriggeredRecord.isPresent()) {
            PatientRecord record = latestTriggeredRecord.get();
            if (record.getMeasurementValue() == 1.0) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Manual Alert: Bedside alert button was triggered",
                        record.getTimestamp()
                ));
            }
        }
    }

}
