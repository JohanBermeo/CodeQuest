package com.codequest.ui.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Utilidades para manejo de archivos subidos
 */
public class FileUploadUtils {
    private static final String UPLOAD_DIRECTORY = "uploads";
    
    /**
     * Guarda los archivos subidos en el directorio de uploads
     * @param files Lista de archivos a guardar
     * @param publicationId ID de la publicación asociada
     * @return Lista de rutas donde se guardaron los archivos
     */
    public static List<String> saveUploadedFiles(List<File> files, String publicationId) throws IOException {
        // Crear directorio si no existe
        Path uploadDir = Paths.get(UPLOAD_DIRECTORY, publicationId);
        Files.createDirectories(uploadDir);
        
        return files.stream()
            .map(file -> {
                try {
                    String fileName = generateUniqueFileName(file.getName());
                    Path targetPath = uploadDir.resolve(fileName);
                    Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    return targetPath.toString();
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar archivo: " + file.getName(), e);
                }
            })
            .toList();
    }
    
    /**
     * Genera un nombre único para el archivo
     */
    private static String generateUniqueFileName(String originalName) {
        String extension = "";
        int lastDotIndex = originalName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalName.substring(lastDotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
    
    /**
     * Elimina los archivos asociados a una publicación
     */
    public static void deletePublicationFiles(String publicationId) {
        try {
            Path uploadDir = Paths.get(UPLOAD_DIRECTORY, publicationId);
            if (Files.exists(uploadDir)) {
                Files.walk(uploadDir)
                    .sorted((a, b) -> b.compareTo(a)) // Eliminar archivos antes que directorios
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Error al eliminar: " + path);
                        }
                    });
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar archivos de la publicación: " + publicationId);
        }
    }
}