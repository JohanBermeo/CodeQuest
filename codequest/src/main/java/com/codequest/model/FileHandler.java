package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de archivos genérico
 */
public class FileHandler<T extends Serializable> { 
    private String basePath;
    
    public FileHandler(String basePath) {
        this.basePath = "data\\" + basePath;
    }
    
    public void save(List<T> data) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(basePath))) {
            oos.writeObject(data);
        } catch (Exception e) {
            throw new Exception("Error al guardar datos", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<T> load() throws Exception {
        File file = new File(basePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(basePath))) {
            return (List<T>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error al cargar datos", e);
        }
    }
    
    public String getBasePath() {
        return basePath;
    }
}