package util;


import java.util.Date;
import java.util.ArrayList;
import java.io.File;

import com.codequest.model.User;
import com.codequest.model.DataManager;

public class GenerateTestUserData {
    public static void main(String[] args) {
        String testFile = "src/test/resources/test-data/test-users.dat";
        File dir = new File("src/test/resources/test-data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        com.codequest.model.FileHandler<User> fileHandler = new com.codequest.model.FileHandler<>(testFile);
        DataManager<User> dataManager = new DataManager<>(fileHandler);
        
        // Limpiar datos existentes
        dataManager.setData(new ArrayList<>());
        
        // Agregar usuarios de prueba
        dataManager.addData(new User("admin", "Admin12345", new Date()));
        dataManager.addData(new User("user1", "password", new Date()));
        dataManager.addData(new User("guest", "guest12345", new Date()));
        
        dataManager.saveData();

        System.out.println("Datos de prueba generados en: " + testFile);
        System.out.println("Usuarios creados: " + dataManager.getData().size());
    }
}