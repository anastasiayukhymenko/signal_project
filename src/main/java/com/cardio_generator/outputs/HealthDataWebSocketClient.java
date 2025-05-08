package com.cardio_generator.outputs;

import com.data_management.DataStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class HealthDataWebSocketClient extends WebSocketClient {

    private final DataStorage dataStorage;

    public HealthDataWebSocketClient(URI serverUri, DataStorage storage) {
        super(serverUri);
        this.dataStorage = storage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        // Expected format: patientId,timestamp,label,data
        try {
            String[] parts = message.split(",", 4);
            if (parts.length != 4) {
                System.err.println("Invalid message format: " + message);
                return;
            }

            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            double measurement = Double.parseDouble(parts[3]);

            // Store the parsed data
            dataStorage.addPatientData(patientId, measurement, label, timestamp);
            System.out.println("Stored: " + message);
        } catch (Exception e) {
            System.err.println("Failed to parse or store message: " + message);
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error:");
        ex.printStackTrace();
    }
}
