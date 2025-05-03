package com.data_management;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileDataReaderTest {

    private Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory and sample .csv file
        tempDir = Files.createTempDirectory("test_data_dir");

        testFile = tempDir.resolve("sample.csv");
        Files.writeString(testFile,
                "101, 98.6, Temperature, 1714720000000\n" +
                        "102, 120.0, BloodPressure, 1714720001000\n" +
                        "101, 99.1, Temperature, 1714720002000\n");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testReadDataAddsRecordsCorrectly() throws IOException {
        DataStorage dataStorage = new DataStorage();
        FileDataReader reader = new FileDataReader(tempDir.toString());

        reader.readData(dataStorage);

        // Validate records for patient 101
        List<PatientRecord> records101 = dataStorage.getRecords(101, 0L, Long.MAX_VALUE);
        assertEquals(2, records101.size());

        PatientRecord firstRecord = records101.get(0);
        assertEquals(101, firstRecord.getPatientId());
        assertEquals("Temperature", firstRecord.getRecordType());
        assertEquals(98.6, firstRecord.getMeasurementValue());
        assertEquals(1714720000000L, firstRecord.getTimestamp());

        // Validate records for patient 102
        List<PatientRecord> records102 = dataStorage.getRecords(102, 0L, Long.MAX_VALUE);
        assertEquals(1, records102.size());

        PatientRecord secondRecord = records102.get(0);
        assertEquals("BloodPressure", secondRecord.getRecordType());
    }

    @Test
    void testHandlesMalformedLinesGracefully() throws IOException {
        Files.writeString(testFile, "\nBAD_LINE\n123,abc,BadType,XYZ\n", StandardOpenOption.APPEND);

        DataStorage dataStorage = new DataStorage();
        FileDataReader reader = new FileDataReader(tempDir.toString());

        assertDoesNotThrow(() -> reader.readData(dataStorage));

        // Only the original 3 lines should result in records
        List<PatientRecord> allRecords = dataStorage.getAllPatients()
                .stream()
                .flatMap(p -> p.getAllRecords().stream())
                .collect(Collectors.toList());

        assertEquals(3, allRecords.size());
    }

    @Test
    void testInvalidDirectoryThrowsException() {
        FileDataReader reader = new FileDataReader("invalid/path");
        DataStorage dataStorage = new DataStorage();

        assertThrows(IOException.class, () -> reader.readData(dataStorage));
    }
}
