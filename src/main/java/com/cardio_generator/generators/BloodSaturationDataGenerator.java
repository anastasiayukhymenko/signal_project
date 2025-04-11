package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * BloodSaturationDataGenerator creates fake blood saturation values for patients.
 *
 * Each patient starts with a value between 95% and 100%, and their value changes slightly
 * each time the data is generated, which simulates realistic and healthy fluctuations in blood saturation.
 */

public class BloodSaturationDataGenerator implements PatientDataGenerator {

    //random number generator used for creating small changes in values
    private static final Random random = new Random();
    //stores the most recent saturation value
    private int[] lastSaturationValues;

    /**
     * {@code BloodSaturationDataGenerator} sets up the generator with a starting saturation value for each patient.
     *
     * @param patientCount the number of patients to generate data for
     */

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }


    /**
     * {@code generate} generates and sends updated blood saturation data for a specific patient.
     *
     * The value can increase or decrease by 1% (or stay the same), and stays between 90% and 100%.
     *
     * @param patientId      the ID of the patient
     * @param outputStrategy the way the generated data should be sent or displayed
     */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
