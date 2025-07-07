package auth;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import auth.AuthenticationService;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.function.BiConsumer;


/**
 * Interfaz gráfica para el servicio de autenticación
 * Maneja tanto el login como el registro de usuarios
 */
public class AuthenticationGUI extends JFrame {

    private AuthenticationService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField birthdayField;
    private JLabel birthdayLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton switchModeButton;
    private JLabel statusLabel;
    private boolean isLoginMode = true;
    private boolean isSuccess = false;
    private String usernameLogin = "";

    /**
     * Constructor de la interfaz de autenticación
     * @param authService Servicio de autenticación
     * @param onCloseCallback Callback que se ejecuta al cerrar la ventana
     */
    public AuthenticationGUI(AuthenticationService authService, BiConsumer<Boolean, String> onCloseCallback) {
        this.authService = authService;
        
        // Callback para manejar el cierre de la ventana
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (onCloseCallback != null && isSuccess) {
                    
                    onCloseCallback.accept(true, usernameLogin);
                }
            }
        });

        initializeComponents();
        setupLayout();
        setupEventListeners();
        setLoginMode();
    }
    
    private void initializeComponents() {
        setTitle("CodeQuest - Autenticación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Campos de entrada
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Campo fecha de nacimiento (solo para registro)
        birthdayLabel = new JLabel("Fecha Nacimiento (DD/MM/YYYY):");
        birthdayField = new JTextField(20);
        birthdayField.setToolTipText("Formato: DD/MM/YYYY");
        
        // Botones
        loginButton = new JButton("Iniciar Sesión");
        registerButton = new JButton("Crear Cuenta");
        switchModeButton = new JButton("¿No tienes cuenta? Regístrate");
        
        // Estado
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setPreferredSize(new Dimension(215, 20));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Título
        JLabel titleLabel = new JLabel("CodeQuest", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(59, 89, 182));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Campo usuario
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1; 
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 0; gbc.gridy = 2; 
        usernameField.setPreferredSize(new Dimension(200, 25)); // Hace el campo más ancho
        mainPanel.add(usernameField, gbc);
        
        // Campo contraseña
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 0; gbc.gridy = 4; 
        passwordField.setPreferredSize(new Dimension(200, 25)); // Hace el campo más ancho
        mainPanel.add(passwordField, gbc);
        
        // Campo fecha nacimiento (solo registro)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(birthdayLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 6;
        birthdayField.setPreferredSize(new Dimension(200, 25)); // Hace el campo más ancho
        mainPanel.add(birthdayField, gbc);
        
        // Botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(buttonPanel, gbc);
        
        // // Botón cambiar modo
        gbc.gridy = 8; gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(switchModeButton, gbc);
        
        // // Estado
        gbc.gridy = 9;
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        
        switchModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMode();
            }
        });
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Por favor complete todos los campos", false);
            return;
        }
        
        try {
            boolean success = authService.login(username, password);
            if (success) {
                showStatus("Login exitoso", true);
                isSuccess = true; 
                usernameLogin = username; 
                this.dispose();
            } else {
                showStatus("Credenciales incorrectas", false);
            }
        } catch (Exception ex) {
            showStatus("Error: " + ex.getMessage(), false);
        }
    }
    
    private void performRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String birthdayStr = birthdayField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty() || birthdayStr.isEmpty()) {
            showStatus("Por favor complete todos los campos", false);
            return;
        }
        
        try {
            Date birthday = parseBirthday(birthdayStr);
            authService.createAccount(username, password, birthday);
            showStatus("Cuenta creada exitosamente", true);
            setLoginMode();
        } catch (Exception ex) {
            showStatus(ex.getMessage(), false);
        }
    }
    
    private Date parseBirthday(String birthdayStr) throws Exception {
        // Implementación simple - en producción usar DateFormat
        String[] parts = birthdayStr.split("/");
        if (parts.length != 3) {
            throw new Exception("Formato de fecha inválido.");
        }
        // Simplificado para el ejemplo
        return new Date();
    }
    
    private void toggleMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            setLoginMode();
        } else {
            setRegisterMode();
        }
    }
    
    private void setLoginMode() {
        isLoginMode = true;
        birthdayField.setVisible(false);
        birthdayLabel.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(false);
        switchModeButton.setText("¿No tienes cuenta? Regístrate");
        clearFields();
    }
    
    private void setRegisterMode() {
        isLoginMode = false;
        birthdayField.setVisible(true);
        birthdayLabel.setVisible(true);
        loginButton.setVisible(false);
        registerButton.setVisible(true);
        switchModeButton.setText("¿Ya tienes cuenta? Inicia sesión");
        clearFields();
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        birthdayField.setText("");
        statusLabel.setText(" ");
    }
    
    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setForeground(isSuccess ? Color.GREEN : Color.RED);
    }
    
}