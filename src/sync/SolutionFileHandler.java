package sync;

import model.content.Solution;
import java.io.*;
import java.util.List;

public class SolutionFileHandler {
    private static final String PATH = "Documentos/compartida/solutions.dat";

    public static void saveSolutions(List<Solution> solutions) throws Exception {
        if (!LockManager.acquireLock(PATH)) {
            throw new Exception("Otro usuario está editando las soluciones.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH))) {
            oos.writeObject(solutions);
        } finally {
            LockManager.releaseLock(PATH);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Solution> loadSolutions() throws Exception {
        File file = new File(PATH);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Solution>) ois.readObject();
        }
    }
}
