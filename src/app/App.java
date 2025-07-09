package app;
import model.content.Challenge;
import model.content.Quest;
import model.User;
import java.util.*;


public class App {
    private User usuario;
    private DataManager<Challenge> challengesController;
    private FileHandlerSynchronous<Challenge> challengesDataHandler;
    private DataManager<Quest> questsController;
    private FileHandlerSynchronous<Quest> questsDataHandler;
    public App() {
    	 this.challengesController = new DataManager<>();
    	    this.questsController = new DataManager<>();
    	    this.challengesDataHandler = new FileHandlerSynchronous<>("challenges.dat");
    	    this.questsDataHandler = new FileHandlerSynchronous<>("quests.dat");
    }
    

    public void uploadChallenge() {
        try {
            String id = UUID.randomUUID().toString();
            String title = JOptionPane.showInputDialog(null, "Título del reto:");
            String description = JOptionPane.showInputDialog(null, "Descripción del reto:");
            if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Datos incompletos.");
                return;
            }

            Challenge challenge = new Challenge(id, title, description, usuario.getUsername());
            challengesController.addData(challenge);
            challengesDataHandler.save(challengesController.getData());

            JOptionPane.showMessageDialog(null, "Reto subido exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al subir reto: " + e.getMessage());
        }
    }


    public void addSolution(Challenge challenge) {
         try {
            String solutionText = JOptionPane.showInputDialog(null, "Escribe tu solución para el reto:\n" + challenge.getTitle());

            if (solutionText == null || solutionText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Solución vacía.");
                return;
            }

            challenge.addLike(); 
            challengesDataHandler.save(challengesController.getData());

            JOptionPane.showMessageDialog(null, "¡Solución registrada! El reto recibió un like.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar solución: " + e.getMessage());
        }
    }

    
    public void uploadQuest() {
        try {
            String id = UUID.randomUUID().toString();
            String title = JOptionPane.showInputDialog(null, "Título de la pregunta:");
            String description = JOptionPane.showInputDialog(null, "Descripción de la pregunta:");
            if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Datos incompletos.");
                return;
            }

            Quest quest = new Quest(id, title, description, usuario.getUsername());
            questsController.addData(quest);
            questsDataHandler.save(questsController.getData());

            JOptionPane.showMessageDialog(null, "Pregunta subida exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al subir la pregunta: " + e.getMessage());
        }
    }

    
    public List<Challenge> showTopChallenges() {
        return challengesController.getData();
    }

    public List<Quest> showTopQuests() {
        return questsController.getData();
    }
}
