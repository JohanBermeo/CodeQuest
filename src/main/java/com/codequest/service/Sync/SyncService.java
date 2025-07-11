package com.codequest.service.Sync;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SyncService {

    private final String saveDir; // Directory to save files
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    public SyncService(String baseSaveDir) {
        this.saveDir = baseSaveDir;
        // Create the save directory if it doesn't exist
        try {
            Path path = Paths.get(this.saveDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + path.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error creating save directory '" + this.saveDir + "': " + e.getMessage());
        }
    }

    public SyncService() {
        this("uploads"); // Default constructor uses "uploads" directory
    }

    public boolean saveUploadData(FileUploadData data) {
        if (data == null) {
            System.err.println("Data to save is null.");
            return false;
        }

        String timestamp = LocalDateTime.now().format(formatter);
        String filename = String.format("%s_%s_%s.txt", data.getType(), sanitizeFilename(data.getTitle()), timestamp);
        Path filePath = Paths.get(this.saveDir, filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
            writer.println("Title: " + data.getTitle());
            writer.println("Type: " + data.getType());
            if (data.getChallengeTitle() != null && !data.getChallengeTitle().isEmpty()) {
                writer.println("Challenge Title: " + data.getChallengeTitle());
            }
            writer.println("--- Content ---");
            writer.println(data.getContent());

            System.out.println("Successfully saved data to: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error saving file: " + filePath.toAbsolutePath());
            e.printStackTrace();
            return false;
        }
    }

    private String sanitizeFilename(String inputName) {
        if (inputName == null || inputName.isEmpty()) {
            return "untitled";
        }
        // Replace common problematic characters with underscores
        return inputName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").substring(0, Math.min(inputName.length(), 50));
    }
}
