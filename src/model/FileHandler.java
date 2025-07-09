package model;

import java.util.List;

public interface FileHandler<T> {
    void save(List<T> data) throws Exception;
    List<T> load() throws Exception;
}
