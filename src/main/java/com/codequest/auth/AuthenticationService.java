package com.codequest.auth;

import java.util.Date;
import java.util.ArrayList;

import com.codequest.model.DataManager;
import com.codequest.model.FileHandler;
import com.codequest.model.User;

/**
 * Separación de responsabilidades - Autenticación
 */
public class AuthenticationService {
    private DataManager<User> userController;
    private FileHandler<User> userFileHandler;
    
    public AuthenticationService(DataManager<User> userController, FileHandler<User> userFileHandler) {
        this.userController = userController;
        this.userFileHandler = userFileHandler;

        try {
            this.userController.setData(userFileHandler.load());
        } catch (Exception e) {
            this.userController.setData(new ArrayList<User>());
            throw new RuntimeException("Error al cargar los datos de usuarios", e);
        }
    }
    
    public boolean login(String username, String password) {
        // Validaciones de entrada
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.length() <= 8) {
            return false;
        }
        User user = userController.findDataById(username.hashCode());
        if (user == null) {
            return false;
        }
        // Compara la contraseña
        return user.validatePassword(password);
    }
    
    public void createAccount(String username, String password, Date birthday) throws Exception {
        validateUserData(username, password, birthday);

        boolean existingUser = userController.existsById(username.hashCode());
        
        if (existingUser) {
            throw new Exception("El usuario ya existe");
        }
        
        User newUser = new User(username, password, birthday);
        userController.addData(newUser);
        userController.saveData();
        userFileHandler.save(userController.getData());
    }
    
    private void validateUserData(String username, String password, Date birthday) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Usuario no puede estar vacío");
        }
        if (userController.existsById(username.hashCode())) {
            throw new Exception("El nombre de usuario ya está en uso");
        }
        if (password == null || password.length() <= 8) {
            throw new Exception("Contraseña debe ser mayor a 8 caracteres");
        }
        if (birthday == null) {
            throw new Exception("Fecha de nacimiento es requerida");
        }
    }
}
