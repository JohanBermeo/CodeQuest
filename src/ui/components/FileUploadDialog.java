package  ui.components;
 
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FileUploadDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final int MAX_FILE_SIZE_MB = 10;
    private static final String[] ALLOWED_EXTENSIONS = {".txt", ".java", ".py", ".cpp", ".c", ".js", ".html", ".css", ".md", ".pdf", ".docx", ".doc"};
    
    private JTextField titleField;
    private JTextArea contentArea;
    private JTextField categoryField;
    private JLabel categoryLabel;
    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private JButton addFileButton;
    private JButton removeFileButton;
    private JButton submitButton;
    private JButton cancelButton;
    private List<File> uploadedFiles;
    private JLabel statusLabel;

    private List<File> selectedFiles;
    private Consumer<UploadData> onSubmitCallback;
    private String uploadType;
    private boolean isSubmitted = false;

    public FileUploadDialog(JFrame parent, String title, String uploadType, Consumer<UploadData> onSubmitCallback) {
        super(parent, title, true);
        this.uploadType = uploadType;
        this.onSubmitCallback = onSubmitCallback;
        this.selectedFiles = new ArrayList<>();

        initializeComponents();
        setupLayouts();
        setupEventListeners();
        configureByType();

        setSize(600, 700);
        setLocationRelativeTo(parent);
        setResizable(true);
    }

    private void initializeComponents() {
        titleField = new JTextField(30);
        contentArea = new JTextArea(10, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
       
        categoryField = new JTextField(20);
        categoryLabel = new JLabel("Categoria:");

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setVisibleRowCount(5);
        
        addFileButton = new JButton("Agregar Archivo");
        removeFileButton = new JButton("Eliminar Archivo");
        submitButton = new JButton("Enviar");
        cancelButton = new JButton("Cancelar");
        
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        styleButton(addFileButton, new Color(52, 152, 219));
        styleButton(removeFileButton, new Color(231, 76, 60));
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
        infoPanel.add(categoryField, gbc);

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
        
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.setBorder(new TitledBorder("Archivos Adjuntos"));
        
        JPanel fileButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fileButtonPanel.add(addFileButton);
        fileButtonPanel.add(removeFileButton);
        fileButtonPanel.add(new JLabel("Máximo 10MB por archivo"));

        filePanel.add(fileButtonPanel, BorderLayout.NORTH);
        filePanel.add(new JScrollPane(fileList), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(submitButton);
        actionPanel.add(cancelButton);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(filePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusPanel, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        addFileButton.addActionListener(e -> selectFile());
        removeFileButton.addActionListener(e -> removeSelectedFile());
        submitButton.addActionListener(e -> submitUpload());
        cancelButton.addActionListener(e -> cancelUpload());

        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                removeFileButton.setEnabled(fileList.getSelectedIndex() != -1);
            }
        });
    }

    private void configureByType() {
        switch (uploadType) {
            case "FORUMPOST":
                setTitle("Nueva publicación en el Foro");
                categoryLabel.setVisible(true);
                categoryField.setVisible(true);
                break;
            case "CHALLENGE":
                setTitle("Subir nuevo desafío");
                categoryLabel.setVisible(true);
                categoryField.setVisible(true);
                break;
            case "SOLUTION":
                setTitle("Agregar solución");
                categoryLabel.setVisible(true);
                categoryField.setVisible(true);
                break;
        }
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            if (validateFile(selectedFile)) {
                selectedFiles.add(selectedFile);
                fileListModel.addElement(selectedFile.getName() + " (" + formatFileSize(selectedFile.length()) + ")");
                showStatus("Archivo agregado correctamente", false);
            }
        }
    }
    
    private boolean validateFile(File file) {
        // Validar tamaño
        long fileSizeInMB = file.length() / (1024 * 1024);
        if (fileSizeInMB > MAX_FILE_SIZE_MB) {
            showStatus("Error: El archivo excede el tamaño máximo de " + MAX_FILE_SIZE_MB + "MB", true);
            return false;
        }
        
        // Validar extensión
        String fileName = file.getName().toLowerCase();
        boolean validExtension = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (fileName.endsWith(ext)) {
                validExtension = true;
                break;
            }
        }
        
        if (!validExtension) {
            showStatus("Error: Tipo de archivo no permitido", true);
            return false;
        }
        
        // Validar que no esté duplicado
        for (File existingFile : selectedFiles) {
            if (existingFile.getName().equals(file.getName())) {
                showStatus("Error: El archivo ya fue agregado", true);
                return false;
            }
        }
        
        return true;
    }
    
    private void removeSelectedFile() {
        int selectedIndex = fileList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedFiles.remove(selectedIndex);
            fileListModel.remove(selectedIndex);
            showStatus("Archivo eliminado", false);
        }
    }
    
    private void submitUpload() {
        if (validateInput()) {
            UploadData uploadData = new UploadData(
                titleField.getText().trim(),
                contentArea.getText().trim(),
                categoryField.getText().trim(),
                uploadType,
                new ArrayList<>(selectedFiles)
            );
            
            isSubmitted = true;
            if (onSubmitCallback != null) {
                onSubmitCallback.accept(uploadData);
            }
            dispose();
        }
    }
    
    private boolean validateInput() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        
        if (title.isEmpty()) {
            showStatus("Error: El título es obligatorio", true);
            titleField.requestFocus();
            return false;
        }
        
        if (content.isEmpty()) {
            showStatus("Error: El contenido es obligatorio", true);
            contentArea.requestFocus();
            return false;
        }
        
        if (uploadType.equals("FORUM_POST") && categoryField.getText().trim().isEmpty()) {
            showStatus("Error: La categoría es obligatoria para posts del foro", true);
            categoryField.requestFocus();
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
    
    private String formatFileSize(long bytes) {
        if (bytes >= 1024 * 1024) {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        } else if (bytes >= 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else {
            return bytes + " bytes";
        }
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

    public static class UploadData {
        private final String title;
        private final String content;
        private final String category;
        private final String type;
        private final List<File> files;
        
        public UploadData(String title, String content, String category, String type, List<File> files) {
            this.title = title;
            this.content = content;
            this.category = category;
            this.type = type;
            this.files = files;
        }
        
        // Getters
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getCategory() { return category; }
        public String getType() { return type; }
        public List<File> getFiles() { return files; }
    }
}