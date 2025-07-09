import javax.swing.*;

import auth.AuthenticationService;
import auth.AuthenticationGUI;
import model.DataManager;
import model.FileHandler;
import model.User;
import app.AppGUI;
import app.App; 
/**
 * Clase principal para ejecutar la aplicación CodeQuest
 * Punto de entrada del sistema con interfaz gráfica
*/
public class CodeQuest {

    private DataManager<User> userController;
    private FileHandler<User> userFileHandler;
    private static AuthenticationService authService;
    private static User currentUser;
    private static AuthenticationGUI authGUI;

    /**
     * Constructor de la aplicación CodeQuest
     * Inicializa los controladores de datos y el servicio de autenticación
     */
    public CodeQuest() {
        // Inicializar el controlador de datos de usuarios
        this.userController = new DataManager<User>();
        this.userFileHandler = new FileHandler<User>("users.dat");
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
            App app = new App();
            AppGUI appGUI = new AppGUI(currentUser, app, (v) -> callbackMenu(true));
            appGUI.setVisible(true);
        } 
    }

    private void callbackMenu(boolean isChangeUser) {
        if (isChangeUser) {
            // Cerrar la ventana actual y volver a mostrar la GUI de autenticación
            authGUI.clearFields();
            authGUI.setVisible(true);
            currentUser = null; // Limpiar el usuario actual
        } 
    }
}
