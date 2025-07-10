package com.codequest;

import javax.swing.*;

import com.codequest.auth.*;
import com.codequest.model.FileHandler;
import com.codequest.model.DataManager;
import com.codequest.model.User;
import com.codequest.app.App;
import com.codequest.app.AppGUI;

/**
 * Clase principal para ejecutar la aplicación CodeQuest
 * Punto de entrada del sistema con interfaz gráfica
*/
public class CodeQuest {

    private User currentUser;
    private App app;
    private AppGUI appGUI;
    private AuthenticationGUI authGUI;
    private AuthenticationService authService;
    private DataManager<User> userController;
    private FileHandler<User> userFileHandler;

    /**
     * Constructor de la aplicación CodeQuest
     * Inicializa los controladores de datos y el servicio de autenticación
     */
    public CodeQuest() {
        // Inicializar el controlador de datos de usuarios
        this.userFileHandler = new FileHandler<User>("users.dat");
        this.userController = new DataManager<User>(userFileHandler);
    }

    public void start() {
                
        // Crear el servicio de autenticación
        try {
            authService = new AuthenticationService(userController, userFileHandler);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear y mostrar la interfaz de autenticación
        authGUI = new AuthenticationGUI(authService, 
            (Boolean isLogin, String username) -> callbackAuthentication(isLogin, username));
        authGUI.setVisible(true);
    }

    private void callbackAuthentication(Boolean isLogin, String username) {
        if (isLogin) {
            currentUser = userController.findDataById(username.hashCode());
            // Inicializa la aplicación
            app = new App(currentUser); 
            appGUI = new AppGUI(currentUser, app, (Boolean isChangeUser) -> callbackMenu(true));
            appGUI.setVisible(true);
        } 
    }

    private void callbackMenu(boolean isChangeUser) {
        if (isChangeUser) {
            // Cerrar la ventana actual y volver a mostrar la GUI de autenticación
            appGUI.dispose();
            authGUI.clearFields();
            authGUI.setVisible(true);
            currentUser = null; // Limpiar el usuario actual
        } 
    }
}
