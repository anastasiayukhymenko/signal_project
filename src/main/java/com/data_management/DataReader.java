package com.data_management;

import java.io.IOException;
import java.net.URI;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;

    /**
     * Connects to a WebSocket server and streams real-time data into the given data storage.
     *
     * @param dataStorage the storage to stream incoming data into
     * @param serverUri the URI of the WebSocket server (e.g., ws://localhost:8080)
     * @throws IOException if connection fails or encounters IO errors
     */
    void startStreaming(DataStorage dataStorage, URI serverUri) throws IOException;

    /**
     * Stops the streaming and closes the WebSocket connection.
     *
     * @throws IOException if an error occurs during disconnection
     */
    void stopStreaming() throws IOException;
}
