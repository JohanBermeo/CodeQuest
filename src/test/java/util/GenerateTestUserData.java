package util;

import model.User;
import model.DataManager;
import java.util.Date;
import java.util.ArrayList;
import java.io.File;

public class GenerateTestUserData {
    public static void main(String[] args) {
        String testFile = "src/test/resources/test-data/test-users.dat";
        File dir = new File("src/test/resources/test-data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        DataManager<User> dataManager = new DataManager<>(testFile);
        
        // Limpiar datos existentes
        dataManager.setData(new ArrayList<>());
        
        // Agregar usuarios de prueba
        dataManager.addData(new User("admin", "admin123", "admin@test.com", new Date()));
        dataManager.addData(new User("user1", "password1", "user1@test.com", new Date()));
        dataManager.addData(new User("guest", "guest123", "guest@test.com", new Date()));
        
        dataManager.saveData();

        System.out.println("Datos de prueba generados en: " + testFile);
        System.out.println("Usuarios creados: " + dataManager.getData().size());
    }
}