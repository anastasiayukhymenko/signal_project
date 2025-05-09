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
    private final int patientId = 11;

    @BeforeEach
    void setUp() {
        generator = new BloodLevelsDataGenerator(22);
        mockOutput = mock(OutputStrategy.class);
    }

    @Test
    void testGenerateOutputsThreeBloodMetrics() {
        generator.generate(patientId, mockOutput);

        verify(mockOutput, times(3)).output(eq(patientId), anyLong(), anyString(), anyString());

        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockOutput, times(3)).output(eq(patientId), anyLong(), labelCaptor.capture(), anyString());

        assertTrue(labelCaptor.getAllValues().contains("Cholesterol"));
        assertTrue(labelCaptor.getAllValues().contains("WhiteBloodCells"));
        assertTrue(labelCaptor.getAllValues().contains("RedBloodCells"));
    }
}
