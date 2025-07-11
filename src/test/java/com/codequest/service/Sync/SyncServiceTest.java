package com.codequest.service.Sync;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SyncServiceTest {

    private SyncService syncService;
    private Path currentTestDir; // To store the path provided by @TempDir

    @TempDir
    Path tempDir; // JUnit 5 temporary directory, injected before each test

    @BeforeEach
    void setUp() {
        currentTestDir = tempDir; // Use the fresh temp directory for each test
        syncService = new SyncService(currentTestDir.toString());
    }

    @AfterEach
    void tearDown() {
        // No explicit cleanup needed for files within @TempDir, JUnit handles it.
        // If SyncService created directories outside of currentTestDir (which it shouldn't with the new constructor),
        // those would need manual cleanup.
    }

    @Test
    void testSaveUploadData_ValidData_CreatesFile() throws IOException {
        FileUploadData data = new FileUploadData("Test Title", "Test Content", "TESTTYPE");
        boolean result = syncService.saveUploadData(data);

        assertTrue(result, "saveUploadData should return true for valid data.");

        // Path expectedDir = currentTestDir; // The directory is now the tempDir itself
        assertTrue(Files.exists(currentTestDir) && Files.isDirectory(currentTestDir), "Upload directory should exist.");

        try (Stream<Path> files = Files.list(currentTestDir)) {
            Path savedFile = files
                .filter(f -> f.getFileName().toString().startsWith("TESTTYPE_Test_Title_"))
                .findFirst()
                .orElse(null);

            assertNotNull(savedFile, "A file should have been created for the upload.");

            List<String> lines = Files.readAllLines(savedFile);
            assertTrue(lines.contains("Title: Test Title"), "File content should include title.");
            assertTrue(lines.contains("Type: TESTTYPE"), "File content should include type.");
            assertTrue(lines.contains("--- Content ---"), "File content should include content separator.");
            assertTrue(lines.contains("Test Content"), "File content should include the actual content.");
        }
    }

    @Test
    void testSaveUploadData_ValidDataWithChallenge_CreatesFileWithChallenge() throws IOException {
        FileUploadData data = new FileUploadData("Solution Title", "Solution Content", "SOLUTION", "Challenge1");
        boolean result = syncService.saveUploadData(data);

        assertTrue(result, "saveUploadData should return true for valid data with challenge.");

        // Path expectedDir = currentTestDir;
        try (Stream<Path> files = Files.list(currentTestDir)) {
            Path savedFile = files
                .filter(f -> f.getFileName().toString().startsWith("SOLUTION_Solution_Title_"))
                .findFirst()
                .orElse(null);

            assertNotNull(savedFile, "A file should have been created for the solution upload.");

            List<String> lines = Files.readAllLines(savedFile);
            assertTrue(lines.contains("Title: Solution Title"));
            assertTrue(lines.contains("Type: SOLUTION"));
            assertTrue(lines.contains("Challenge Title: Challenge1"));
            assertTrue(lines.contains("Solution Content"));
        }
    }

    @Test
    void testSaveUploadData_NullData_ReturnsFalse() {
        boolean result = syncService.saveUploadData(null);
        assertFalse(result, "saveUploadData should return false for null data.");
    }

    @Test
    void testSaveUploadData_FilenameSanitization() throws IOException {
        // Title with characters that need sanitization
        String problematicTitle = "T@e#s$t%^&*( )+={[]}|\\:;\"'<>,.?/";
        FileUploadData data = new FileUploadData(problematicTitle, "Content", "SANITIZE");
        syncService.saveUploadData(data);

        // Path expectedDir = currentTestDir;
        try (Stream<Path> files = Files.list(currentTestDir)) {
            Path savedFile = files
                .filter(f -> f.getFileName().toString().startsWith("SANITIZE_T_e_s_t_")) // Check for sanitized part
                .findFirst()
                .orElse(null);

            assertNotNull(savedFile, "File with sanitized name should be created.");
            // Example: "SANITIZE_T_e_s_t__________test_title_ sanitized_20231027_123456_789.txt"
            // The exact sanitized name depends on the implementation of sanitizeFilename
            // This test confirms a file is created and starts with the sanitized prefix.
            String fileName = savedFile.getFileName().toString();
            assertFalse(fileName.contains("@"), "Filename should not contain '@'");
            assertFalse(fileName.contains("#"), "Filename should not contain '#'");
            assertTrue(fileName.contains("_"), "Filename should use underscores for sanitized characters.");
        }
    }

    @Test
    void testDirectoryCreation() {
        // The directory (currentTestDir) should be created by the SyncService constructor
        // if it didn't exist, or SyncService should correctly use it if it does.
        assertTrue(Files.exists(currentTestDir) && Files.isDirectory(currentTestDir),
                   "Upload directory '" + currentTestDir.toString() + "' should exist and be a directory.");
    }
}
