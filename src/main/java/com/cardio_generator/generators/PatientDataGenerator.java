package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface representing a patient data generator.
 * Implementations of this interface are responsible for generating
 * specific types of health-related data for a given patient and outputting
 * that data using the provided {@link OutputStrategy}.
 */
public interface PatientDataGenerator {

    /**
     * Generates data for a specific patient and sends it through the given output strategy.
     * @param patientId       unique id for patient
     * @param outputStrategy  strategy used to output the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
