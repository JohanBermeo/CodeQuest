package model;

import java.util.ArrayList;
import model.interfaces.Identifiable;
import java.util.Iterator;
import java.util.List;


/**
 * Manejador genérico de datos
 */
public class DataManager<T extends Identifiable> {
    private List<T> data;
    
    public DataManager() {
        this.data = new ArrayList<>();
    }
    
    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }
    
    public List<T> getData() {
        return this.data;
    }
    
    public void addData(T item) {
        if (item != null) {
            this.data.add(item);
        }
    }
    
    public boolean deleteData(int id) {
        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item.getId() == id) {
                iterator.remove();
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