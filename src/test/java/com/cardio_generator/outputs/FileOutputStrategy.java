package com.cardio_generator.outputs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputStrategyTest {

    private Path tempDir;
    private FileOutputStrategy fileOutputStrategy;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("output_test");
        fileOutputStrategy = new FileOutputStrategy(tempDir.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete all files in the temp directory
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a)) // delete children before parent
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        // Ignored for cleanup
                    }
                });
    }

    @Test
    void testOutputCreatesFileAndWritesData() throws IOException {
        int patientId = 101;
        long timestamp = System.currentTimeMillis();
        String label = "HeartRate";
        String data = "75";

        fileOutputStrategy.output(patientId, timestamp, label, data);

        Path outputFile = tempDir.resolve(label + ".txt");
        assertTrue(Files.exists(outputFile), "Output file should exist");

        List<String> lines = Files.readAllLines(outputFile);
        assertEquals(1, lines.size(), "File should contain one line of output");
        assertTrue(lines.get(0).contains("Patient ID: 101"), "Output should contain correct patient ID");
        assertTrue(lines.get(0).contains("Label: HeartRate"), "Output should contain correct label");
        assertTrue(lines.get(0).contains("Data: 75"), "Output should contain correct data");
    }

    @Test
    void testOutputAppendsData() throws IOException {
        int patientId = 202;
        long timestamp = System.currentTimeMillis();
        String label = "BloodPressure";
        String data1 = "120/80";
        String data2 = "125/85";

        fileOutputStrategy.output(patientId, timestamp, label, data1);
        fileOutputStrategy.output(patientId, timestamp + 1000, label, data2);

        Path outputFile = tempDir.resolve(label + ".txt");
        List<String> lines = Files.readAllLines(outputFile);
        assertEquals(2, lines.size(), "File should contain two lines of output");
        assertTrue(lines.get(1).contains("Data: 125/85"), "Second line should contain updated data");
    }
}
