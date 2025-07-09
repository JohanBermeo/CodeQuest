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
        // Implementar lógica
    }

    public void addSolution(Challenge challenge) {
        // Implementar lógica
    }

    public void uploadQuest() {
        // Implementar lógica
    }

    public List<Challenge> showTopChallenges() {
        return challengesController.getData();
    }

    public List<Quest> showTopQuests() {
        return questsController.getData();
    }
}