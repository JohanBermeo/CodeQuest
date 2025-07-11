package com.codequest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import com.codequest.model.interfaces.Identifiable;

/**
 * Manejador genérico de datos
 */
public class DataManager<T extends Identifiable & java.io.Serializable> {
    private List<T> data;
    private final FileHandler<T> fileHandler;
    
    public DataManager(FileHandler<T> fileHandler) {
        this.fileHandler = fileHandler;
        this.data = loadData();
    }

    public DataManager() {
        this.fileHandler = null;
        this.data = new ArrayList<>();
    }

    private List<T> loadData() {
        try {
            return fileHandler != null ? fileHandler.load() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar los datos persistidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveData() {
        if (fileHandler != null) {
            try {
                fileHandler.save(data);
            } catch (Exception e) {
                System.err.println("Error al guardar los datos: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("No se puede guardar datos, FileHandler no está definido.");
        }
    }

    public void setData(List<T> data) {
        this.data = data != null ? new ArrayList<>(data) : new ArrayList<>();
        saveData();
    }
    
    public List<T> getData() {
        return new ArrayList<>(data);
    }
    
    public void addData(T item) {
        if (item != null) {
            this.data.add(item);
            saveData();
        }
    }
    
    public boolean deleteData(int id) {
        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item.getId() == id) {
                iterator.remove();
                saveData();
                return true;
            }
        }
        return false;
    }
    
    public T findDataById(int id) {
        for (T item : data) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
    
    public int getDataCount() {
        return data.size();
    }
    
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    public void clearData() {
        data.clear();
    }
    
    public boolean existsById(int id) {
        return findDataById(id) != null;
    }
}