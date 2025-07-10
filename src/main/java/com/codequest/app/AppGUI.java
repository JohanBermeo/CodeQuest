package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.function.Consumer;
import model.User;
import java.util.*;
import model.content.*;

public class AppGUI extends JFrame {
    private User user;
    private Consumer<Void> onExitCallback;
    private App app;

    public AppGUI(User user, App app, Consumer<Void> onExitCallback) {
        this.user = user;
        this.app = app;
        this.onExitCallback = onExitCallback;
        initializeComponents();
    }

    public void initializeComponents() {
        JFrame frame = new JFrame("CodeQuest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        frame.setLayout(new BorderLayout(20, 20));
        
        JLabel titleLabel = new JLabel("CodeQuest");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(44, 62, 80));

        JLabel welcomeLabel = new JLabel("Bienvenido, " + user.getUsername());
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(33, 47, 61));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.setBackground(new Color(230, 240, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        topPanel.add(titleLabel);
        topPanel.add(welcomeLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        buttonPanel.setBackground(Color.WHITE);

        JButton uploadChallengeButton = new JButton("Subir Reto");
        JButton addSolutionButton = new JButton("Agregar Solución a Reto");
        JButton uploadQuestButton = new JButton("Subir Misión");
        JButton showTopChallengesButton = new JButton("Mostrar Retos Top");
        JButton showTopQuestsButton = new JButton("Mostrar Misiones Top");
        JButton exitButton = new JButton("Salir");

        JButton[] buttons = { uploadChallengeButton, addSolutionButton, uploadQuestButton,
                              showTopChallengesButton, showTopQuestsButton, exitButton };
        for (JButton button : buttons) {
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));
            button.setBackground(new Color(72, 133, 237));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 103, 184)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15))
            );
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            buttonPanel.add(button);
        }

        // Pasarle los datos que el usuario ponga en todos los ActionsListeners para crear los challenges y quests

        uploadChallengeButton.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextArea descArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(descArea);
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(new JLabel("Título:"), BorderLayout.NORTH);
            panel.add(titleField, BorderLayout.CENTER);
            panel.add(new JLabel("Descripción:"), BorderLayout.SOUTH);
            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(descPanel, BorderLayout.AFTER_LAST_LINE);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Subir Reto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String title = titleField.getText();
                String desc = descArea.getText();
                boolean success = app.uploadChallenge(title, desc);
                JOptionPane.showMessageDialog(frame, success ? "Reto subido correctamente" : "Error al subir el reto");
            }
        });


        addSolutionButton.addActionListener(e -> {
            // Obtener lista de retos para seleccionar
            List<Challenge> challenges = app.showTopChallenges();
            if (challenges.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay retos disponibles");
                return;
            }
            String[] challengeTitles = challenges.stream().map(Challenge::getTitle).toArray(String[]::new);
            JComboBox<String> challengeBox = new JComboBox<>(challengeTitles);
            JTextArea solutionArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(solutionArea);
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(new JLabel("Selecciona un reto:"), BorderLayout.NORTH);
            panel.add(challengeBox, BorderLayout.CENTER);
            panel.add(new JLabel("Solución:"), BorderLayout.SOUTH);
            JPanel solPanel = new JPanel(new BorderLayout());
            solPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(solPanel, BorderLayout.AFTER_LAST_LINE);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Agregar Solución", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int idx = challengeBox.getSelectedIndex();
                String solutionText = solutionArea.getText();
                boolean success = app.addSolution(challenges.get(idx), solutionText);
                JOptionPane.showMessageDialog(frame, success ? "Solución agregada correctamente" : "Error al agregar solución");
            }
        });


        uploadQuestButton.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextArea descArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(descArea);
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(new JLabel("Título:"), BorderLayout.NORTH);
            panel.add(titleField, BorderLayout.CENTER);
            panel.add(new JLabel("Descripción:"), BorderLayout.SOUTH);
            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(descPanel, BorderLayout.AFTER_LAST_LINE);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Subir Misión", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String title = titleField.getText();
                String desc = descArea.getText();
                boolean success = app.uploadQuest(title, desc);
                JOptionPane.showMessageDialog(frame, success ? "Misión subida correctamente" : "Error al subir la misión");
            }
        });

        showTopChallengesButton.addActionListener(e -> {
            List<Challenge> challenges = app.showTopChallenges();
            JOptionPane.showMessageDialog(frame, "Retos: " + challenges.size());
        });

        showTopQuestsButton.addActionListener(e -> {
            List<Quest> quests = app.showTopQuests();
            JOptionPane.showMessageDialog(frame, "Misiones: " + quests.size());
        });

        exitButton.addActionListener(e -> {
            if (onExitCallback != null) onExitCallback.accept(null);
            frame.dispose();
        });

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

