package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Sends generated patient data to a connected client using a TCP connection.
 * This class starts a simple TCP server that waits for a client to connect.
 * Once a connection is established, it sends formatted data messages (e.g., alerts, heart rate) over the network.
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * {@code TcpOutputStrategy} starts a TCP server on the given port and waits for a client to connect.
     *
     * The server accepts one client and sets up an output stream to send data.
     *
     * @param port the port number to listen on
     */

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code output} sends a formatted data message to the connected client.
     *
     * @param patientId the ID of the patient
     * @param timestamp the current time in milliseconds
     * @param label     the type of data
     * @param data      the actual data value
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
