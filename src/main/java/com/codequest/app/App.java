package app;

import model.content.Challenge;
import model.content.Quest;
import model.content.Solution;
import model.content.ForumPost;
import model.User;
import ui.components.FileUploadDialog;
import ui.utils.FileUploadUtils;
import java.util.*;
import java.io.IOException;
import javax.swing.JFrame;

public class App {
    private User usuario;
    private DataManager<Challenge> challengesController;
    private FileHandlerSynchronous<Challenge> challengesDataHandler;
    private DataManager<Quest> questsController;
    private FileHandlerSynchronous<Quest> questsDataHandler;
    private DataManager<Solution> solutionsController;
    private FileHandlerSynchronous<Solution> solutionsDataHandler;
    private DataManager<ForumPost> forumPostsController;
    private FileHandlerSynchronous<ForumPost> forumPostsDataHandler;

    public App() {
        this.usuario = usuario;
    	this.challengesController = new DataManager<>();
    	this.questsController = new DataManager<>();
        this.solutionsController = new DataManager<>();
        this.forumPostsController = new DataManager<>();
    	this.challengesDataHandler = new FileHandlerSynchronous<>("challenges.dat");
    	this.questsDataHandler = new FileHandlerSynchronous<>("quests.dat");
    }
    

    public boolean uploadChallenge(String title, String description) {
        if (usuario == null || title == null || description == null || title.isBlank() || description.isBlank()) {
            return false;
        }
        try {
            String id = UUID.randomUUID().toString();
            Challenge challenge = new Challenge(id, title, description, usuario.getUsername());
            challengesController.addData(challenge);
            challengesDataHandler.save(challengesController.getData());
            return true;
        } catch (Exception e) {
        return false;
        }
    }


    public boolean addSolution(Challenge challenge, String solutionText) {
        if (challenge == null || solutionText == null || solutionText.isBlank()) {
            return false;
        }
        try {
            challenge.addLike(); 
            challengesDataHandler.save(challengesController.getData());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean uploadQuest(String title, String description) {
        if (usuario == null || title == null || description == null || title.isBlank() || description.isBlank()) {
            return false;
        }
        try {
            String id = UUID.randomUUID().toString();
            Quest quest = new Quest(id, title, description, usuario.getUsername());
            questsController.addData(quest);
            questsDataHandler.save(questsController.getData());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Challenge> showTopChallenges() {
        return challengesController.getData();
    }

    public List<Quest> showTopQuests() {
        return questsController.getData();
    }
}
