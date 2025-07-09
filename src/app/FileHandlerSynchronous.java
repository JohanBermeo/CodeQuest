package app;
import java.util.*;

public class FileHandlerSynchronous<T> {
    private String basePath;

    public FileHandlerSynchronous(String basePath) {
        this.basePath = basePath;
    }

    public void save(List<T> data) {
        // Implementar lógica de guardado
    }

    public List<T> load() {
        // Implementar lógica de carga
        return new ArrayList<>();
    }
}