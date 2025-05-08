package com.cardio_generator.outputs;

import com.data_management.DataStorage;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;
import java.net.http.HttpClient;

class HealthDataWebSocketClientTest {

    private DataStorage dataStorage;
    private HealthDataWebSocketClient client;

    @BeforeEach
    void setUp() {
        // Create a mock DataStorage
        dataStorage = mock(DataStorage.class);
    }

    @Test
    void testValidMessageParsing() {
        URI serverUri = URI.create("ws://localhost:8080");  // Providing a valid URI
        client = new HealthDataWebSocketClient(serverUri, dataStorage);

        String message = "101,1714720000000,Temperature,98.6";
        client.onMessage(message);

        // Verify the correct data was added to DataStorage
        verify(dataStorage).addPatientData(101, 98.6, "Temperature", 1714720000000L);
    }

    @Test
    void testInvalidMessageFormat() {
        URI serverUri = URI.create("ws://localhost:8080");  // Providing a valid URI
        client = new HealthDataWebSocketClient(serverUri, dataStorage);

        String message = "101,1714720000000,Temperature";  // Missing data value
        client.onMessage(message);

        // Verify no data is added to DataStorage
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testInvalidDataType() {
        URI serverUri = URI.create("ws://localhost:8080");  // Providing a valid URI
        client = new HealthDataWebSocketClient(serverUri, dataStorage);

        String message = "101,invalidTimestamp,Temperature,98.6";  // Invalid timestamp
        client.onMessage(message);

        // Verify that the data is not stored due to parsing failure
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testInvalidDataParsing() {
        URI serverUri = URI.create("ws://localhost:8080");  // Providing a valid URI
        client = new HealthDataWebSocketClient(serverUri, dataStorage);

        String message = "101,1714720000000,Temperature,abc";  // Invalid measurement value
        client.onMessage(message);

        // Verify that the data is not stored due to parsing failure
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }
}
