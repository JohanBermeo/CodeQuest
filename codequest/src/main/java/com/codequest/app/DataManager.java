package app;

import java.util.*;

public class DataManager<T> {
    private List<T> data = new ArrayList<>();
    public void setData(List<T> data) { this.data = data; }
    public List<T> getData() { return data; }
    public void addData(T t) { data.add(t); }
    public boolean deleteData(T t) { return data.remove(t); }
    public T findDataById(int id) { return data.get(id); }
    public int getDataCount() { return data.size(); }
    public boolean isEmpty() { return data.isEmpty(); }
    public boolean existsById(int id) { return id >= 0 && id < data.size(); }
}
