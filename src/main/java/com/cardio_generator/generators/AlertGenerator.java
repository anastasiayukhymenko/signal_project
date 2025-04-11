package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * AlertGenerator creates random alert messages for each patient.
 * It either triggers a new alert or resolves an existing one
 */

public class AlertGenerator implements PatientDataGenerator {

    //random number generator used to create alerts
    public static final Random randomGenerator = new Random();
    //array stroing current alert state of each patient
    //Changed var name to camelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Creates an {@code AlertGenerator} for a given number of patients.
     *
     * @param patientCount the total number of patients to generate alert data for
     */

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for a specific patient.
     *
     * If an alert is already active, there is a 90% chance it will be resolved.
     * If no alert is active, there is small chance a new alert will be triggered.
     *
     * @param patientId      the ID of the patient for whom to generate alert data
     * @param outputStrategy how the alert should be displayed or output
     */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
