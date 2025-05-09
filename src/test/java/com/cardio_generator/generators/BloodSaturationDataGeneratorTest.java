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
        generator = new BloodSaturationDataGenerator(175);
        mockOutputStrategy = mock(OutputStrategy.class);
    }

    @Test
    void testGenerate_outputsValueWithinRange() {
        for (int i = 1; i <= 175; i++) {
            final int patientId = i;
            assertDoesNotThrow(() -> generator.generate(patientId, mockOutputStrategy));
        }
        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockOutputStrategy, times(175)).output(
                patientIdCaptor.capture(),
                timestampCaptor.capture(),
                labelCaptor.capture(),
                dataCaptor.capture()
        );

        for (String data : dataCaptor.getAllValues()) {
            assertTrue(data.endsWith("%"), "Data should end with % symbol");
            String numericPart = data.replace("%", "");
            double value = Double.parseDouble(numericPart);
            assertTrue(value >= 90 && value <= 100, "Saturation should be in range 90–100%");
        }
    }

    @Test
    void testGenerate_usesCorrectLabel() {
        generator.generate(14, mockOutputStrategy);

        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockOutputStrategy).output(eq(14), anyLong(), labelCaptor.capture(), anyString());

        assertEquals("Saturation", labelCaptor.getValue(), "Name should be 'Saturation'");
    }

    @Test
    void testGenerate_multipleCallsProduceDifferentValues() {
        generator.generate(14, mockOutputStrategy);
        generator.generate(14, mockOutputStrategy);
        verify(mockOutputStrategy, times(2)).output(eq(14), anyLong(), eq("Saturation"), anyString());
    }
}
