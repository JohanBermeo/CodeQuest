package auth;

import java.util.Date;

import java.util.ArrayList;

import model.DataManager;
import model.FileHandler;
import model.User;

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
        User user = userController.findDataById(username.hashCode());
        if (user == null || !user.validatePassword(password)) {
            return false;
        }
        return true;
    }
    
    public void createAccount(String username, String password,String email, Date birthday) throws Exception {
        validateUserData(username, password,email, birthday);

        boolean existingUser = userController.existsById(username.hashCode());
        
        if (existingUser) {
            throw new Exception("El usuario ya existe");
        }
        
        User newUser = new User(username, password, email, birthday);
        userController.addData(newUser);
        userFileHandler.save(userController.getData());
    }
    
    private void validateUserData(String username, String password,String email, Date birthday) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Usuario no puede estar vacío");
        }
        if (userController.existsById(username.hashCode())) {
            throw new Exception("El nombre de usuario ya está en uso");
        }
        if (password == null || password.length() < 8) {
            throw new Exception("Contraseña debe ser mayor a 8 caracteres");
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new Exception("Email inválido");
        }
        if (birthday == null) {
            throw new Exception("Fecha de nacimiento es requerida");
        }
        // Más validaciones...
    }
}
