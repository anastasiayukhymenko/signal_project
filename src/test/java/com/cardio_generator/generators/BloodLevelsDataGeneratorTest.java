package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BloodLevelsDataGeneratorTest {

    private BloodLevelsDataGenerator generator;
    private OutputStrategy mockOutput;
    private final int patientId = 1;

    @BeforeEach
    void setUp() {
        generator = new BloodLevelsDataGenerator(2); // 2 patients (index 1 and 2)
        mockOutput = mock(OutputStrategy.class);
    }

    @Test
    void testGenerateOutputsThreeBloodMetrics() {
        generator.generate(patientId, mockOutput);

        // Verify output was called 3 times (Cholesterol, WhiteBloodCells, RedBloodCells)
        verify(mockOutput, times(3)).output(eq(patientId), anyLong(), anyString(), anyString());

        // Optional: capture the labels used in output
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockOutput, times(3)).output(eq(patientId), anyLong(), labelCaptor.capture(), anyString());

        assertTrue(labelCaptor.getAllValues().contains("Cholesterol"));
        assertTrue(labelCaptor.getAllValues().contains("WhiteBloodCells"));
        assertTrue(labelCaptor.getAllValues().contains("RedBloodCells"));
    }
}
