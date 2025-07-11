package  app;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import app.App;
import model.User;
import model.content.Quest;
import model.content.Challenge;
import model.content.Solution;

public class AppGUI extends JFrame {
    private User user;
    private App app; 
    private Consumer<Boolean> onExitCallback;
    private JPanel mainPanel; 

    public AppGUI(User user, App app, Consumer<Boolean> onExitCallback) {
        this.user = user;
        this.app = app;
        this.onExitCallback = onExitCallback;
        initializeComponents();
    }

    public void initializeComponents() {
        JFrame frame = new JFrame("CodeQuest");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (onExitCallback != null) {
                    onExitCallback.accept(true); 
                }
                frame.dispose();
            }
        });
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout(0, 0));

        // Panel superior con título y botones
        Object[] itemsTop = createTopPanel();
        JPanel topPanel = (JPanel) itemsTop[0];
        JButton foroButton = (JButton) itemsTop[1];
        JButton challengesButton = (JButton) itemsTop[2];

        // Panel principal que se actualizará
        mainPanel = new JPanel(new BorderLayout());

        // Panel inferior para botones contextuales
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 245));

        // Paneles de Foro
        Object[] itemsForo = createForoPanel(new ArrayList<>());
        JPanel foroPanel = (JPanel) itemsForo[0];
        JButton createQuestButton = (JButton) itemsForo[1];

        // Panel de Challenges
        Object[] itemsChallenges = createChallengesPanel(new ArrayList<>());
        JPanel challengesPanel = (JPanel) itemsChallenges[0];
        JButton createChallengeButton = (JButton) itemsChallenges[1];
        JButton createSolutionButton = (JButton) itemsChallenges[2];

        // Acción para "Foro"
        foroButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(foroPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();

            // Actualizar botones del panel inferior
            bottomPanel.removeAll();
            bottomPanel.add(createQuestButton);
            bottomPanel.revalidate();
            bottomPanel.repaint();
        });

        // Acción para "Challenges"
        challengesButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(challengesPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();

            // Actualizar botones del panel inferior
            bottomPanel.removeAll();
            bottomPanel.add(createSolutionButton);
            bottomPanel.add(createChallengeButton);
            bottomPanel.revalidate();
            bottomPanel.repaint();
        });

        // Por defecto, mostrar el panel de foro y su botón contextual
        mainPanel.add(foroPanel, BorderLayout.CENTER);
        bottomPanel.add(createQuestButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(new Color(52, 152, 219));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

   public Object[] createTopPanel() {
      // Panel superior con título y bienvenida
      JLabel titleLabel = new JLabel("Bienvenid@ "+ user.getUsername());
      // JLabel titleLabel = new JLabel(Integer.toString(user.getChallengesLikedIds().size()) + " Retos");
      titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
      titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

      // Panel para botones superiores (Foro y Challenges)
      JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
      topRightPanel.setOpaque(false);

      JButton foroButton = new JButton("Foro");
      foroButton.setBackground(new Color(52, 152, 219));
      JButton challengesButton = new JButton("Retos");
      challengesButton.setBackground(new Color(150, 50, 0));

      JButton[] topButtons = { foroButton, challengesButton };
      for (JButton btn : topButtons) {
         btn.setFocusPainted(false);
         btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
         btn.setForeground(Color.WHITE);
         btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
         btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         topRightPanel.add(btn);
      }

      // Panel superior combinado
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.setPreferredSize(new Dimension(0, 77)); 
      JPanel titlePanel = new JPanel(new GridLayout(2, 1));
      titlePanel.setPreferredSize(new Dimension(0, 60));
      
      titlePanel.setOpaque(false);
      titlePanel.add(titleLabel);
      topPanel.add(titlePanel, BorderLayout.CENTER);
      topPanel.add(topRightPanel, BorderLayout.EAST);
      topPanel.setBackground(new Color(245, 245, 245));
      topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

      return new Object[] { topPanel, foroButton, challengesButton };
   }

    /**
     * Crea y retorna un JPanel que muestra una lista de objetos Quest con todos sus atributos.
    */	
    public Object[] createForoPanel(List<Quest> quests1) {
      JPanel foroPanel = new JPanel();
      foroPanel.setLayout(new BoxLayout(foroPanel, BoxLayout.Y_AXIS));
      foroPanel.setBackground(new Color(52, 152, 219));

      // Título centrado "Foro"
      JLabel tituloLabel = new JLabel("Foro");
      tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
      tituloLabel.setForeground(Color.WHITE);
      tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
      foroPanel.add(Box.createRigidArea(new Dimension(0, 15)));
      foroPanel.add(tituloLabel);
      foroPanel.add(Box.createRigidArea(new Dimension(0, 20)));

      List<Quest> quests = new ArrayList<>();
      quests.add(new Quest("Aventura en la Montaña", "Explora la montaña y encuentra el tesoro perdido.", "Juan"));
      quests.add(new Quest("Resuelve el Laberinto", "Encuentra la salida del laberinto en menos de 5 minutos.", "Ana"));
      quests.add(new Quest("Desafío de Programación", "Resuelve el problema de algoritmos más rápido que tus compañeros.", "Carlos"));
      quests.add(new Quest("Búsqueda del Dragón", "Derrota al dragón y salva el reino.", "Lucía"));
      quests.add(new Quest("Puzzle Matemático", "Resuelve el puzzle matemático usando lógica.", "Pedro"));
      quests.add(new Quest("Carrera de Obstáculos", "Supera todos los obstáculos en el menor tiempo posible.", "María"));
      quests.add(new Quest("Misión Espacial", "Llega a Marte y regresa a salvo.", "Sofía"));
      quests.add(new Quest("Escape de la Isla", "Encuentra la manera de escapar de la isla desierta.", "Miguel"));
      quests.add(new Quest("Construye un Puente", "Construye un puente resistente usando materiales limitados.", "Elena"));
      quests.add(new Quest("Rescate Submarino", "Rescata a los buzos atrapados bajo el mar.", "Andrés"));

      if (quests == null || quests.isEmpty()) {
         foroPanel.add(new JLabel("No hay quests para mostrar."));
      } else {
         for (Quest quest : quests) {
            JPanel questPanel = new JPanel();
            questPanel.setLayout(new BorderLayout());
            questPanel.setBackground(new Color(236, 240, 241));
            questPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Dimension fixedSize = new Dimension(700, 105);
            questPanel.setMaximumSize(fixedSize);
            questPanel.setPreferredSize(fixedSize);
            questPanel.setMinimumSize(fixedSize);

            // Panel central para la info del quest
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);
            infoPanel.add(new JLabel("Título: " + quest.getTitle()));
            infoPanel.add(new JLabel("Descripción: " + quest.getDescription()));
            infoPanel.add(new JLabel("Autor: " + quest.getAuthor()));

            questPanel.add(infoPanel, BorderLayout.CENTER);

            JButton likeButton = createLikeBtn(quest.getId(), user::getQuestsLikedIds,
               user::addQuestLikedId, user::removeQuestLikedId);

            JPanel rightBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            rightBtnPanel.add(likeButton);

            questPanel.add(rightBtnPanel, BorderLayout.SOUTH);
            questPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            foroPanel.add(questPanel);
            foroPanel.add(Box.createRigidArea(new Dimension(0, 10)));
         }
      }

      // Agregar foroPanel a un JScrollPane para permitir scroll
      JScrollPane scrollPane = new JScrollPane(foroPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(700, 575));

      JPanel containerPanel = new JPanel();
      containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
      containerPanel.setBackground(new Color(52, 152, 219));
      containerPanel.add(scrollPane);
      containerPanel.add(Box.createVerticalGlue()); // Para empujar el contenido hacia arriba

      // Botón contextual para el foro
      JButton crearQuestButton = new JButton("Crear Publicación");
      crearQuestButton.setBackground(new Color(39, 174, 96));
      crearQuestButton.setForeground(Color.WHITE);
      crearQuestButton.setFocusPainted(false);
      crearQuestButton.setFont(new Font("SansSerif", Font.BOLD, 16));
      crearQuestButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
      crearQuestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      crearQuestButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(containerPanel, "Acción para crear un nuevo Quest");
      });

      return new Object[] { containerPanel, crearQuestButton };

   }

    public Object[] createChallengesPanel(List<Challenge> challengesList) {
      JPanel challengesPanel = new JPanel();
      challengesPanel.setLayout(new BoxLayout(challengesPanel, BoxLayout.Y_AXIS));
      challengesPanel.setBackground(new Color(150, 50, 0));

      // Título centrado "Challenges"
      JLabel tituloLabel = new JLabel("Retos");
      tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
      tituloLabel.setForeground(Color.WHITE);
      tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
      challengesPanel.add(Box.createRigidArea(new Dimension(0, 15)));
      challengesPanel.add(tituloLabel);
      challengesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

      // Lista de challenges de ejemplo si la lista está vacía
      List<Challenge> challenges = (challengesList == null || challengesList.isEmpty()) ? new ArrayList<>() : challengesList;
      if (challenges.isEmpty()) {
         challenges.add(new Challenge("Suma de Números", "Escribe una función que sume dos números.", "Juan"));
         challenges.add(new Challenge("Palíndromo", "Verifica si una palabra es un palíndromo.", "Ana"));
         challenges.add(new Challenge("Ordenar Lista", "Ordena una lista de enteros de menor a mayor.", "Carlos"));
         challenges.add(new Challenge("Contar Vocales", "Cuenta el número de vocales en una cadena.", "Lucía"));
         challenges.add(new Challenge("Fibonacci", "Calcula el n-ésimo número de Fibonacci.", "Pedro"));
         challenges.add(new Challenge("Factorial", "Calcula el factorial de un número.", "María"));
         challenges.add(new Challenge("Invertir Cadena", "Invierte una cadena de texto.", "Sofía"));
         challenges.add(new Challenge("Buscar Elemento", "Busca un elemento en un arreglo.", "Miguel"));
         challenges.add(new Challenge("Máximo Común Divisor", "Encuentra el MCD de dos números.", "Elena"));
         challenges.add(new Challenge("Números Primos", "Determina si un número es primo.", "Andrés"));
      }

      // Crear soluciones de ejemplo y asignarlas a algunos challenges
      List<Solution> allSolutions = new ArrayList<>();
      allSolutions.add(new Solution("Luis", "Usé un bucle for para sumar los números.", challenges.get(0).getId()));
      allSolutions.add(new Solution("Ana", "Utilicé recursividad para sumar.", challenges.get(0).getId()));
      allSolutions.add(new Solution("Pedro", "Invertí la cadena con StringBuilder.", challenges.get(6).getId()));
      allSolutions.add(new Solution("Lucía", "Verifiqué palíndromos con dos punteros.", challenges.get(1).getId()));
      allSolutions.add(new Solution("Carlos", "Usé el algoritmo de Euclides para el MCD.", challenges.get(8).getId()));

      // Asignar soluciones a los challenges correspondientes
      for (Solution sol : allSolutions) {
         for (Challenge ch : challenges) {
            if (ch.getId() == sol.getChallengeId()) {
                  ch.addSolution(sol.getId());
            }
         }
      }

      for (Challenge challenge : challenges) {
         JPanel challengePanel = new JPanel();
         challengePanel.setLayout(new BorderLayout());
         challengePanel.setBackground(new Color(236, 240, 241));
         challengePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

         Dimension fixedSize = new Dimension(700, 105);
         challengePanel.setMaximumSize(fixedSize);
         challengePanel.setPreferredSize(fixedSize);
         challengePanel.setMinimumSize(fixedSize);

         // Panel central para la info del challenge
         JPanel infoPanel = new JPanel();
         infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
         infoPanel.setOpaque(false);
         infoPanel.add(new JLabel("Título: " + challenge.getTitle()));
         infoPanel.add(new JLabel("Descripción: " + challenge.getDescription()));
         infoPanel.add(new JLabel("Autor: " + challenge.getAuthor()));

         challengePanel.add(infoPanel, BorderLayout.CENTER);

         JButton likeButton = createLikeBtn(challenge.getId(), user::getChallengesLikedIds,
            user::addChallengeLikedId, user::removeChallengeLikedId);

         JPanel rightBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

         // Si el challenge tiene soluciones
         if (!challenge.getSolutions().isEmpty()) {
            Solution[] solutionsArray = new Solution[challenge.getSolutions().size()];
            for (Solution sol : allSolutions) {
               if (sol.getChallengeId() == challenge.getId()) {
                  int index = challenge.getSolutions().indexOf(sol.getId());
                  if (index >= 0) {
                     solutionsArray[index] = sol;
                  }
               }
            }
            JButton verSolucionesBtn = createSolutionBtn(solutionsArray, challenge.getTitle());
            rightBtnPanel.add(verSolucionesBtn);
         }
         
         rightBtnPanel.add(likeButton);
         
         challengePanel.add(rightBtnPanel, BorderLayout.SOUTH);
         challengePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
         challengesPanel.add(challengePanel);
         challengesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      }

      // Agregar challengesPanel a un JScrollPane para permitir scroll
      JScrollPane scrollPane = new JScrollPane(challengesPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(700, 575));

      JPanel containerPanel = new JPanel();
      containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
      containerPanel.setBackground(new Color(52, 152, 219));
      containerPanel.add(scrollPane);
      containerPanel.add(Box.createVerticalGlue());

      // Botón contextual para challenges
      JButton createChallengeButton = new JButton("Crear Challenge");
      createChallengeButton.setBackground(new Color(39, 174, 96));
      createChallengeButton.setForeground(Color.WHITE);
      createChallengeButton.setFocusPainted(false);
      createChallengeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
      createChallengeButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
      createChallengeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      createChallengeButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(containerPanel, "Acción para los challenges");
      });

      // Botón contextual para challenges
      JButton createSolutionButton = new JButton("Subir Solución");
      createSolutionButton.setBackground(new Color(200, 100, 0));
      createSolutionButton.setForeground(Color.WHITE);
      createSolutionButton.setFocusPainted(false);
      createSolutionButton.setFont(new Font("SansSerif", Font.BOLD, 16));
      createSolutionButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
      createSolutionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      createSolutionButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(containerPanel, "Acción para los challenges");
      });

      return new Object[] { containerPanel, createChallengeButton, createSolutionButton };
   }

   private JButton createSolutionBtn(Solution[] solutionsArray, String challengeTitle) {
      JButton verSolucionesBtn = new JButton("Ver soluciones");
      verSolucionesBtn.setBackground(new Color(200, 100, 0));
      verSolucionesBtn.setForeground(Color.WHITE);
      verSolucionesBtn.setFocusPainted(false);
      verSolucionesBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
      verSolucionesBtn.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
      verSolucionesBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      verSolucionesBtn.addActionListener(e -> {
         // Crear panel para mostrar soluciones de este challenge
         JPanel solucionesPanel = new JPanel();
         solucionesPanel.setLayout(new BoxLayout(solucionesPanel, BoxLayout.Y_AXIS));
         solucionesPanel.setBackground(new Color(236, 240, 241));
         solucionesPanel.add(new JLabel("Soluciones para el challenge: " + challengeTitle));
         solucionesPanel.add(Box.createRigidArea(new Dimension(0, 10)));

         Dimension fixedSolSize = new Dimension(395, 70);

         for (Solution sol : solutionsArray) {
            JPanel solPanel = new JPanel();
            solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
            solPanel.setBackground(new Color(220, 220, 220));
            solPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            solPanel.setMaximumSize(fixedSolSize);
            solPanel.setPreferredSize(fixedSolSize);
            solPanel.setMinimumSize(fixedSolSize);

            solPanel.add(new JLabel("Autor: " + sol.getAuthor()));
            solPanel.add(new JLabel("Explicación: " + sol.getExplication()));
            solucionesPanel.add(solPanel);
            solucionesPanel.add(Box.createRigidArea(new Dimension(0, 8)));
         }

         // Mostrar soluciones en un JOptionPane
         JScrollPane scrollSol = new JScrollPane(solucionesPanel);
         scrollSol.setPreferredSize(new Dimension(400, 250));
         JOptionPane.showMessageDialog(null, scrollSol, "Soluciones", JOptionPane.INFORMATION_MESSAGE);
      });

      return verSolucionesBtn;
   }

   private JButton createLikeBtn(int postId, Supplier<Set<Integer>> getlikedIds, Consumer<Integer> addLikedId, Consumer<Integer> removeLikedId) {
      String likeText;
      Color likeColor;

      if (getlikedIds.get().contains(postId)) {
         likeText = "No me gusta";
         likeColor = new Color(152, 52, 19);
      } else {
         likeText = "Me gusta";
         likeColor = new Color(39, 174, 96);
      }

      JButton likeButton = new JButton(likeText);
      likeButton.setBackground(likeColor);
      likeButton.setForeground(Color.WHITE);
      likeButton.setFocusPainted(false);
      likeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
      likeButton.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
      likeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      
      likeButton.addActionListener(e -> {
         if (getlikedIds.get().contains(postId)) {
            // Quitar like
            removeLikedId.accept(postId);
            likeButton.setText("Me gusta");
            likeButton.setBackground(new Color(39, 174, 96));
         } else {
            // Dar like
            addLikedId.accept(postId);
            likeButton.setText("No me gusta");
            likeButton.setBackground(new Color(152, 52, 19));
         }
      });

      return likeButton;
   }
}

