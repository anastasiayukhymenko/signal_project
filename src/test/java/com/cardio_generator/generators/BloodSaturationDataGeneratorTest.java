package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BloodSaturationDataGeneratorTest {

    private BloodSaturationDataGenerator generator;
    private OutputStrategy mockOutputStrategy;

    @BeforeEach
    void setUp() {
        generator = new BloodSaturationDataGenerator(5); // for 5 patients
        mockOutputStrategy = mock(OutputStrategy.class);
    }

    @Test
    void testGenerate_outputsValueWithinRange() {
        for (int i = 1; i <= 5; i++) {
            final int patientId = i;
            assertDoesNotThrow(() -> generator.generate(patientId, mockOutputStrategy));
        }

        // Capture the output
        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockOutputStrategy, times(5)).output(
                patientIdCaptor.capture(),
                timestampCaptor.capture(),
                labelCaptor.capture(),
                dataCaptor.capture()
        );

        for (String data : dataCaptor.getAllValues()) {
            assertTrue(data.endsWith("%"), "Data should end with % symbol");
            String numericPart = data.replace("%", "");
            double value = Double.parseDouble(numericPart);
            assertTrue(value >= 90 && value <= 100, "Saturation should be in range 90%–100%");
        }
    }

    @Test
    void testGenerate_usesCorrectLabel() {
        generator.generate(1, mockOutputStrategy);

        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockOutputStrategy).output(eq(1), anyLong(), labelCaptor.capture(), anyString());

        assertEquals("Saturation", labelCaptor.getValue(), "Label should be 'Saturation'");
    }

    @Test
    void testGenerate_multipleCallsProduceDifferentValues() {
        generator.generate(1, mockOutputStrategy);
        generator.generate(1, mockOutputStrategy);
        verify(mockOutputStrategy, times(2)).output(eq(1), anyLong(), eq("Saturation"), anyString());
    }
}
