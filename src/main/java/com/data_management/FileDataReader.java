package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileDataReader implements DataReader {
    private final String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }
    /**
     * Reads data from CSV files in the specified directory and stores it in the
     * provided DataStorage.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IOException("Provided path is not a valid directory: " + directoryPath);
        }

        try (Stream<Path> files = Files.list(path)) {
            files.filter(file -> file.toString().endsWith(".csv"))
                    .forEach(file -> {
                        try {
                            parseFile(file, dataStorage);
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file + " - " + e.getMessage());
                        }
                    });
        }
    }

    private void parseFile(Path file, DataStorage dataStorage) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(",", 4);
                if (parts.length != 4) continue; // Skip malformed lines

                try {
                    int patientId = Integer.parseInt(parts[0].trim());
                    double value = Double.parseDouble(parts[1].trim());
                    String type = parts[2].trim();
                    long timestamp = Long.parseLong(parts[3].trim());

                    //PatientRecord record = new PatientRecord(patientId, value, type, timestamp);
                    dataStorage.addPatientData(patientId, value, type, timestamp);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data format in file " + file + ": " + line);
                }
            }
        }
    }

    @Override
    public void startStreaming(DataStorage dataStorage, URI serverUri) throws IOException {}
        

    @Override
    public void stopStreaming() throws IOException {}
}
