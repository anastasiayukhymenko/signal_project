package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    //starts WebSocket server on a specific port
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    /**
     * Output the formatted data message to all connected clients.
     * This method ensures that each message contains patientId, timestamp, label, and measurement data.
     *
     * @param patientId ID of the patient
     * @param timestamp Timestamp of the data
     * @param label Type of the data (e.g., "HeartRate", "BloodPressure")
     * @param data The actual measurement data (e.g., heart rate value)
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {

        //format the message correctly
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);

        //broadcast the message to all connected clients
        for (WebSocket conn : server.getConnections()) {
            conn.send(message);  // Sends the message to each connected client
        }

        //optionally log the message being sent for debugging
        System.out.println("Broadcasting message: " + message);
    }

    //WebSocket server implementation for handling connections.
    private static class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context 
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
