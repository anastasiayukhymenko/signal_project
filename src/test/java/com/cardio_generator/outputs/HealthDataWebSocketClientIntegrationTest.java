package com.cardio_generator.outputs;

import com.data_management.DataStorage;
import org.junit.jupiter.api.*;
import java.net.URI;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HealthDataWebSocketClientIntegrationTest {

    private DataStorage dataStorage;
    private HealthDataWebSocketClient client;

    @BeforeEach
    void setUp() {
        // Create mock DataStorage
        dataStorage = mock(DataStorage.class);
    }

    @Test
    void testRealTimeDataProcessingAndStorage() throws InterruptedException {
        URI serverUri = URI.create("ws://localhost:8080");
        client = new HealthDataWebSocketClient(serverUri, dataStorage);

        // Simulate WebSocket message reception
        String message1 = "101,1714720000000,Temperature,98.6";
        String message2 = "102,1714720001000,BloodPressure,120.0";

        client.onMessage(message1);
        client.onMessage(message2);

        // Verify that data was correctly stored in the DataStorage
        verify(dataStorage).addPatientData(101, 98.6, "Temperature", 1714720000000L);
        verify(dataStorage).addPatientData(102, 120.0, "BloodPressure", 1714720001000L);
    }
}
