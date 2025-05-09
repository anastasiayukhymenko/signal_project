package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BloodPressureDataGeneratorTest {

    private BloodPressureDataGenerator generator;
    private OutputStrategy mockOutput;

    @BeforeEach
    void setUp() {
        generator = new BloodPressureDataGenerator(333);
        mockOutput = mock(OutputStrategy.class);
    }

    @Test
    void testGenerate_outputsSystolicAndDiastolicValuesWithinRange() {
        int patientId = 1;

        generator.generate(patientId, mockOutput);

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timeCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockOutput, times(2)).output(
                idCaptor.capture(), timeCaptor.capture(), labelCaptor.capture(), valueCaptor.capture()
        );

        for (int i = 0; i < 2; i++) {
            String label = labelCaptor.getAllValues().get(i);
            double value = Double.parseDouble(valueCaptor.getAllValues().get(i));

            if (label.equals("SystolicPressure")) {
                assertTrue(value >= 90 && value <= 180, "Systolic Pressure should be between 90 and 180");
            } else if (label.equals("DiastolicPressure")) {
                assertTrue(value >= 60 && value <= 120, "Diastolic Pressure should be between 60 and 120");
            } else {
                fail("Unexpected name: " + label);
            }
        }
    }

    @Test
    void testGenerate_usesCorrectLabels() {
        generator.generate(23, mockOutput);

        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockOutput, times(2)).output(anyInt(), anyLong(), labelCaptor.capture(), anyString());

        assertTrue(labelCaptor.getAllValues().contains("SystolicPressure"));
        assertTrue(labelCaptor.getAllValues().contains("DiastolicPressure"));
    }

    @Test
    void testGenerate_multiplePatients() {
        generator.generate(13, mockOutput);
        generator.generate(23, mockOutput);

        verify(mockOutput, times(4)).output(anyInt(), anyLong(), anyString(), anyString());
    }
}
