package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ECGDataGeneratorTest {

    private ECGDataGenerator generator;
    private OutputStrategy mockOutputStrategy;

    @BeforeEach
    void setUp() {
        generator = new ECGDataGenerator(10); // Test with 10 patients
        mockOutputStrategy = mock(OutputStrategy.class);
    }

    @Test
    void testGenerateOutputsECGData() {
        int patientId = 3;

        generator.generate(patientId, mockOutputStrategy);

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockOutputStrategy, times(1))
                .output(idCaptor.capture(), timestampCaptor.capture(), labelCaptor.capture(), dataCaptor.capture());

        assertEquals(patientId, idCaptor.getValue());
        assertEquals("ECG", labelCaptor.getValue());

        String dataStr = dataCaptor.getValue();
        assertNotNull(dataStr);
        assertDoesNotThrow(() -> Double.parseDouble(dataStr));
    }

    @Test
    void testGenerateDoesNotCrashForMultiplePatients() {
        for (int i = 1; i <= 10; i++) {
            final int patientId = i;
            assertDoesNotThrow(() -> generator.generate(patientId, mockOutputStrategy));
        }
    }
}
