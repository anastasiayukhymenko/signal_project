package com.data_management;

import com.cardio_generator.outputs.HealthDataWebSocketClient;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.List;

import static org.mockito.Mockito.*;

class FileDataReaderTest {
    private Path tempPath;
    private DataStorage mockStorage;

    @BeforeEach
    void setUp() throws IOException {
        tempPath = Files.createTempDirectory("testData");
        mockStorage = mock(DataStorage.class);
    }

    @AfterEach
    void tearDown() throws IOException {
        try (var files = Files.list(tempPath)) {
            files.forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                }
            });
        }
        Files.deleteIfExists(tempPath);
    }

    @Test
    void testReadData_validCsvFile() throws IOException {
        Path file = Files.createFile(tempPath.resolve("test.csv"));
        Files.write(file, List.of(
                "1,98.6,heart_rate,1672531200",
                "2,75.5,blood_pressure,1672531260"
        ));

        FileDataReader reader = new FileDataReader(tempPath.toString());
        reader.readData(mockStorage);

        verify(mockStorage).addPatientData(1, 98.6, "heart_rate", 1672531200L);
        verify(mockStorage).addPatientData(2, 75.5, "blood_pressure", 1672531260L);
    }

    @Test
    void testReadData_ignoresNonCsvFiles() throws IOException {
        Path txtFile = Files.createFile(tempPath.resolve("notes.txt"));
        Files.write(txtFile, List.of("This should be ignored"));

        FileDataReader reader = new FileDataReader(tempPath.toString());
        reader.readData(mockStorage);

        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testReadData_skipsMalformedLines() throws IOException {
        Path file = Files.createFile(tempPath.resolve("test.csv"));
        Files.write(file, List.of(
                "invalid,line,missing,values",
                "3,100.0,temp,1672531300"
        ));

        FileDataReader reader = new FileDataReader(tempPath.toString());
        reader.readData(mockStorage);

        verify(mockStorage).addPatientData(3, 100.0, "temp", 1672531300L);
    }

    @Test
    void testStartStreaming_callsConnect() throws IOException {
        HealthDataWebSocketClient mockClient = mock(HealthDataWebSocketClient.class);
        URI serverUri = URI.create("ws://localhost:8080");
        FileDataReader reader = new FileDataReader(tempPath.toString(), mockClient);

        reader.startStreaming(mockStorage, serverUri);

        verify(mockClient).connect();
    }

    @Test
    void testStopStreaming_callsClose() throws IOException {
        HealthDataWebSocketClient mockClient = mock(HealthDataWebSocketClient.class);
        FileDataReader reader = new FileDataReader(tempPath.toString(), mockClient);

        reader.stopStreaming();

        verify(mockClient).close();
    }
}
