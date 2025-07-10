package com.codequest.sync;

import com.codequest.model.content.Quest;
import java.io.*;
import java.util.List;

public class QuestFileHandler {
    private static final String PATH = "Documentos/compartida/quests.dat";

    public static void saveQuests(List<Quest> quests) throws Exception {
        if (!LockManager.acquireLock(PATH)) {
            throw new Exception("Otro usuario está editando los quests.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH))) {
            oos.writeObject(quests);
        } finally {
            LockManager.releaseLock(PATH);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Quest> loadQuests() throws Exception {
        File file = new File(PATH);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Quest>) ois.readObject();
        }
    }
}
