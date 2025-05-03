package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertGeneratorTest {

    private AlertGenerator alertGenerator;

    @Mock
    private OutputStrategy mockOutput;

    @BeforeEach
    public void setUp() {
        alertGenerator = new AlertGenerator(10); // handles up to patientId 10
    }

    @Test
    public void testAlertTriggered() {
        AlertGenerator.randomGenerator.setSeed(42); // deterministic seed

        for (int i = 0; i < 100; i++) {
            alertGenerator.generate(1, mockOutput);
        }

        verify(mockOutput, atLeastOnce()).output(eq(1), anyLong(), eq("Alert"), eq("triggered"));
    }

    @Test
    public void testAlertResolved() {
        AlertGenerator.randomGenerator.setSeed(123);

        for (int i = 0; i < 100; i++) {
            alertGenerator.generate(2, mockOutput); // should trigger
        }

        AlertGenerator.randomGenerator.setSeed(123);

        for (int i = 0; i < 100; i++) {
            alertGenerator.generate(2, mockOutput); // should resolve
        }

        verify(mockOutput, atLeastOnce()).output(eq(2), anyLong(), eq("Alert"), eq("triggered"));
        verify(mockOutput, atLeastOnce()).output(eq(2), anyLong(), eq("Alert"), eq("resolved"));
    }
}
