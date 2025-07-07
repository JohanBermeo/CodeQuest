import javax.swing.*;

import auth.AuthenticationService;
import auth.AuthenticationGUI;
import model.DataManager;
import model.FileHandler;
import model.User;

/**
 * Clase principal para ejecutar la aplicación ConnectHub
 * Punto de entrada del sistema con interfaz gráfica
 */
public class CodeQuest {

    private DataManager<User> userController;
    private FileHandler<User> userFileHandler;
    private static AuthenticationService authService;
    private static User currentUser;
    private static AuthenticationGUI authGUI;

    /**
     * Constructor de la aplicación ConnectHub
     * Inicializa los controladores de datos y el servicio de autenticación
     */
    public CodeQuest() {
        // Inicializar el controlador de datos de usuarios
        this.userController = new DataManager<>();
        this.userFileHandler = new FileHandler<>("users.dat");
    }

    /**
     * Método principal que inicia la aplicación
     * @param args argumentos de línea de comandos
     */
    public void main() {
                
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
            // Inicializa la aplicación
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
    
    /**
     * Método auxiliar para mostrar información sobre la aplicación
     */
    public static void showAbout() {
        String aboutText = "ConnectHub v1.0\n" +
                          "Una red social moderna\n" +
                          "Desarrollado con Java Swing\n\n" +
                          "Características:\n" +
                          "• Autenticación de usuarios\n" +
                          "• Publicaciones multimedia\n" +
                          "• Feed en tiempo real\n" +
                          "• Interfaz intuitiva";
        
        JOptionPane.showMessageDialog(null, aboutText, "Acerca de ConnectHub", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
}