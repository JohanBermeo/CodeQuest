package com.codequest.ui.components;
 
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.codequest.model.content.Challenge;
import com.codequest.service.Sync.FileUploadData; // Added import for service model
import com.codequest.service.Sync.SyncService;   // Added import for SyncService

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FileUploadDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel statusLabel;

    private Consumer<UploadData> onSubmitCallback;
    private String uploadType;
    private boolean isSubmitted = false;
    private SyncService syncService; // Added SyncService instance

    private JComboBox<String> challengeComboBox;
    private List<Challenge> challenges;

    public FileUploadDialog(JFrame parent, String title, String uploadType, Consumer<UploadData> onSubmitCallback) {
        super(parent, title, true);
        this.uploadType = uploadType;
        this.onSubmitCallback = onSubmitCallback;
        this.syncService = new SyncService(); // Initialize SyncService

        initializeComponents();
        setupLayouts();
        configureByType();
        setupEventListeners();

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setResizable(true);
    }

    public FileUploadDialog(JFrame parent, String title, String uploadType, Consumer<UploadData> onSubmitCallback, List<Challenge> challenges) {
        super(parent, title, true);
        this.uploadType = uploadType;
        this.onSubmitCallback = onSubmitCallback;
        this.challenges = challenges;
        this.syncService = new SyncService(); // Initialize SyncService

        initializeComponents();
        setupLayouts();
        configureByType();
        setupEventListeners();

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setResizable(true);
    }

    private void initializeComponents() {
        titleField = new JTextField(30);
        contentArea = new JTextArea(10, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 12));

        submitButton = new JButton("Enviar");
        cancelButton = new JButton("Cancelar");
        
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        

        styleButton(submitButton, new Color(46, 204, 113));
        styleButton(cancelButton, new Color(149, 165, 166));
    }

    private void setupLayouts() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(new TitledBorder("Información Básica"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        infoPanel.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        infoPanel.add(new JScrollPane(contentArea), gbc);

        if (uploadType.equals("SOLUTION")) {
            // Agregar campo para seleccionar el challenge relacionado
            gbc.gridx = 0;
            gbc.gridy = 1;
            infoPanel.add(new JLabel("Challenge relacionado:"), gbc);
            gbc.gridx = 1;
            challengeComboBox = new JComboBox<>();
            for (Challenge challenge : challenges) {
                challengeComboBox.addItem(challenge.getTitle());
            }
            infoPanel.add(challengeComboBox, gbc);
        }
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(submitButton);
        actionPanel.add(cancelButton);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusPanel, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        submitButton.addActionListener(e -> submitUpload());
        cancelButton.addActionListener(e -> cancelUpload());
    }

    private void configureByType() {
        switch (uploadType) {
            case "FORUMPOST":
                setTitle("Nueva publicación en el Foro");
                break;
            case "CHALLENGE":
                setTitle("Subir nuevo desafío");
                break;
            case "SOLUTION":
                setTitle("Agregar solución");
                break;
        }
    }

    private void submitUpload() {
        if (validateInput()) {
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();
            String selectedChallengeTitle = null;

            if (uploadType.equals("SOLUTION")) {
                selectedChallengeTitle = (String) challengeComboBox.getSelectedItem();
            }

            // Create UI layer UploadData for callback
            UploadData uiUploadData = new UploadData(title, content, uploadType, selectedChallengeTitle);

            // Create Service layer FileUploadData for saving
            com.codequest.service.Sync.FileUploadData serviceUploadData =
                new com.codequest.service.Sync.FileUploadData(title, content, uploadType, selectedChallengeTitle);

            // Save the data using SyncService
            boolean success = syncService.saveUploadData(serviceUploadData);

            if (success) {
                isSubmitted = true;
                if (onSubmitCallback != null) {
                    onSubmitCallback.accept(uiUploadData); // Pass UI specific data model
                }
                showStatus("Datos guardados exitosamente!", false);
                // Dispose after a short delay to allow user to see success message
                Timer timer = new Timer(1500, e -> dispose());
                timer.setRepeats(false);
                timer.start();
            } else {
                showStatus("Error: No se pudieron guardar los datos.", true);
                // Optionally, do not dispose the dialog if saving fails, allowing user to retry or copy data.
            }
        }
    }
    
    private boolean validateInput() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty()) {
            showStatus("Error: El título es obligatorio"+titleField.getText(), true);
            titleField.requestFocus();
            return false;
        }
        
        if (content.isEmpty()) {
            showStatus("Error: El contenido es obligatorio", true);
            contentArea.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void cancelUpload() {
        isSubmitted = false;
        dispose();
    }
    
    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Color.RED : new Color(46, 204, 113));
        
        // Limpiar mensaje después de 3 segundos
        Timer timer = new Timer(3000, e -> statusLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void styleButton(JButton button, Color backgroundColor) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(backgroundColor.darker()),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    public boolean isSubmitted() {
        return isSubmitted;
    }

    // Clase interna para datos de subida
    public static class UploadData {
        private final String title;
        private final String content;
        private final String type;
        private String challengeTitle;
        public UploadData(String title, String content, String type) {
            this.title = title;
            this.content = content;
            this.type = type;
        }
        public UploadData(String title, String content, String type, String challengeTitle) {
            this.title = title;
            this.content = content;
            this.type = type;
            this.challengeTitle = challengeTitle;
        }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getType() { return type; }
        public String getChallengeTitle() {
            return challengeTitle;
        }
    }
}