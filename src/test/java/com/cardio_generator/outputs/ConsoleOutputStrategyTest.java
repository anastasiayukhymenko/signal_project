package com.cardio_generator.outputs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleOutputStrategyTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testOutputPrintsCorrectly() {
        // Arrange
        ConsoleOutputStrategy strategy = new ConsoleOutputStrategy();
        int patientId = 123;
        long timestamp = 1650000000000L;
        String label = "HeartRate";
        String data = "75";

        // Act
        strategy.output(patientId, timestamp, label, data);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Patient ID: 123"));
        assertTrue(output.contains("Timestamp: 1650000000000"));
        assertTrue(output.contains("Label: HeartRate"));
        assertTrue(output.contains("Data: 75"));
    }
}
