package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface representing a patient data generator.
 * Implementations responsible for generating
 * specific types of data for a given patient and outputting
 * that data using the provided {@link OutputStrategy}.
 */
public interface PatientDataGenerator {

    /**
     * Generates data for a specific patient and sends it through the given output strategy.
     * @param patientId       id for patient
     * @param outputStrategy  strategy for output
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
