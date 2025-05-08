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
        ConsoleOutputStrategy strategy = new ConsoleOutputStrategy();
        int patientId = 119;
        long timestamp = 1630000000000L;
        String label = "Heart Rate";
        String data = "78";

        strategy.output(patientId, timestamp, label, data);

        String output = outContent.toString();
        assertTrue(output.contains("Patient ID: 119"));
        assertTrue(output.contains("Timestamp: 1630000000000"));
        assertTrue(output.contains("Label: Heart Rate"));
        assertTrue(output.contains("Data: 78"));
    }
}
