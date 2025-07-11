package sync;

import model.content.Challenge;
import java.io.*;
import java.util.List;

public class ChallengeFileHandler {
    private static final String PATH = "Documentos/compartida/challenges.dat";

    public static void saveChallenges(List<Challenge> challenges) throws Exception {
        if (!LockManager.acquireLock(PATH)) {
            throw new Exception("Otro usuario está editando los challenges.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH))) {
            oos.writeObject(challenges);
        } finally {
            LockManager.releaseLock(PATH);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Challenge> loadChallenges() throws Exception {
        File file = new File(PATH);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Challenge>) ois.readObject();
        }
    }
}
