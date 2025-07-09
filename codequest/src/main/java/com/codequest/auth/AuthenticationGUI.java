package auth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.function.BiConsumer;

public class AuthenticationGUI extends JFrame {

    private AuthenticationService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField birthdayField;
    private JLabel birthdayLabel;
    private JTextField emailField;
    private JLabel emailLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton switchModeButton;
    private JLabel statusLabel;
    private boolean isLoginMode = true;
    private boolean isSuccess = false;
    private String usernameLogin = "";

    public AuthenticationGUI(AuthenticationService authService, BiConsumer<Boolean, String> onCloseCallback) {
        this.authService = authService;

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
        setSize(420, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        emailLabel = new JLabel("Correo Electrónico:");
        emailField = new JTextField(20);

        birthdayLabel = new JLabel("Fecha Nacimiento (DD/MM/YYYY):");
        birthdayField = new JTextField(20);
        birthdayField.setToolTipText("Formato: DD/MM/YYYY");

        loginButton = new JButton("Iniciar Sesión");
        registerButton = new JButton("Crear Cuenta");
        switchModeButton = new JButton("¿No tienes cuenta? Regístrate");

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);

        styleButton(loginButton);
        styleButton(registerButton);
        styleSwitchButton(switchModeButton);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("CodeQuest", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Usuario:"), gbc);

        gbc.gridy++;
        mainPanel.add(usernameField, gbc);

        gbc.gridy++;
        mainPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridy++;
        mainPanel.add(passwordField, gbc);

        gbc.gridy++;
        mainPanel.add(emailLabel, gbc);

        gbc.gridy++;
        mainPanel.add(emailField, gbc);

        gbc.gridy++;
        mainPanel.add(birthdayLabel, gbc);

        gbc.gridy++;
        mainPanel.add(birthdayField, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(mainPanel.getBackground());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel, gbc);

        gbc.gridy++;
        mainPanel.add(switchModeButton, gbc);

        gbc.gridy++;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> performRegistration());
        switchModeButton.addActionListener(e -> toggleMode());
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
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || birthdayStr.isEmpty() || email.isEmpty()) {
            showStatus("Por favor complete todos los campos", false);
            return;
        }

        try {
            Date birthday = parseBirthday(birthdayStr);
            authService.createAccount(username, password, email, birthday);
            showStatus("Cuenta creada exitosamente", true);
            setLoginMode();
        } catch (Exception ex) {
            showStatus(ex.getMessage(), false);
        }
    }

    private Date parseBirthday(String birthdayStr) throws Exception {
        String[] parts = birthdayStr.split("/");
        if (parts.length != 3) throw new Exception("Formato de fecha inválido.");
        return new Date(); // Simplificado
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
        emailLabel.setVisible(false);
        emailField.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(false);
        switchModeButton.setText("¿No tienes cuenta? Regístrate");
        clearFields();
    }

    private void setRegisterMode() {
        isLoginMode = false;
        birthdayField.setVisible(true);
        birthdayLabel.setVisible(true);
        emailLabel.setVisible(true);
        emailField.setVisible(true);
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
        emailField.setText("");
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setForeground(isSuccess ? new Color(39, 174, 96) : new Color(192, 57, 43));
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(41, 128, 185)),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleSwitchButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.ITALIC, 12));
        button.setForeground(new Color(52, 73, 94));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
} 

