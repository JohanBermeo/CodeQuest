package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Manejador de archivos genérico
 */
public class FileHandler<T extends java.io.Serializable> { 
    private String filePath;
    
    public FileHandler(String filePath) {
        this.filePath = "data\\"+filePath;
    }
    
    public void save(List<T> data) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<T> load() throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error al cargar datos", e);
        }
    }
    
    public String getBasePath() {
        return filePath;
    }
}