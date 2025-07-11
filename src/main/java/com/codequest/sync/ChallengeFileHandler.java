package com.codequest.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.codequest.model.content.Challenge;

public class ChallengeFileHandler {
    private static final String PATH = "compartida/CodeQuest/challenges.dat";

    public static void saveChallenges(List<Challenge> challenges) throws Exception {
        File file = new File(PATH);
        LockManager lockManager = new LockManager(file); 
        try {
            lockManager.acquireLock(); 

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(challenges);
            }
        } finally {
            lockManager.releaseLock(); 
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
