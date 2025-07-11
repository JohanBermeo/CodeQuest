package com.codequest.sync;

import java.io.File;
import java.io.IOException;


public class LockManager {

    private final File lockFile;

 
    public LockManager(File targetFile) {
        File parentDir = new File(System.getProperty("user.home"), "Documentos/compartida");
        String lockFileName = targetFile.getName() + ".lock";
        this.lockFile = new File(parentDir, lockFileName);
    }

    /**
     * @throws IOException Si no se puede crear el archivo de lock
     * @throws IllegalStateException Si ya existe un lock activo
     */
    public void acquireLock() throws IOException {
        int attempts = 0;
        while (lockFile.exists()) {
            try {
                Thread.sleep(100); // Esperar un poco antes de intentar de nuevo
                attempts++;
                if (attempts > 100) { // Esperar hasta 10 segundos máximo
                    throw new IllegalStateException("El archivo está bloqueado por otra instancia: " + lockFile.getName());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrumpido mientras esperaba el lock", e);
            }
        }

        if (!lockFile.createNewFile()) {
            throw new IOException("No se pudo crear el archivo de lock: " + lockFile.getAbsolutePath());
        }
    }

    /**
     * Elimina el archivo .lock
     */
    public void releaseLock() {
        if (lockFile.exists()) {
            if (!lockFile.delete()) {
                System.err.println("No se pudo eliminar el archivo de lock: " + lockFile.getAbsolutePath());
            }
        }
    }

    /**
     * Verifica si el archivo está actualmente bloqueado
     */
    public boolean isLocked() {
        return lockFile.exists();
    }
}
