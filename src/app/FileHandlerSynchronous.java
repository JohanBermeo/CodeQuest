package app;

import model.FileHandler;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;

/**
 * Manejador de archivos que evita edición simultánea (con bloqueo de archivo).
 */
public class FileHandlerSynchronous<T> implements FileHandler<T> {
    private final String basePath;

    public FileHandlerSynchronous(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void save(List<T> data) throws Exception {
        File file = new File(basePath);
        boolean saved = false;
        int attempts = 0;

        while (!saved && attempts < 20) {
            attempts++;
            try (
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    FileChannel channel = raf.getChannel()
            ) {
                FileLock lock = channel.tryLock();

                if (lock != null) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                        oos.writeObject(data);
                        saved = true;
                    } finally {
                        lock.release();
                    }
                } else {
                    Thread.sleep(1000); // espera antes de reintentar
                }

            } catch (IOException | InterruptedException e) {
                throw new Exception("Error al guardar archivo: " + e.getMessage(), e);
            }
        }

        if (!saved) {
            throw new Exception("Otro usuario está editando este archivo. Intenta guardar más tarde.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> load() throws Exception {
        File file = new File(basePath);
        if (!file.exists()) return null;

        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al cargar archivo: " + e.getMessage(), e);
        }
    }
}
