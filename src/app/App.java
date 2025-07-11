package  app;

import java.util.List;

import  model.DataManager;
import  model.FileHandlerSynchronous;
import  model.content.Challenge;
import  model.content.Quest;
import  model.content.Solution;
import  model.content.ForumPost;
import  model.User;
import  ui.utils.FileUploadUtils;
import  ui.components.FileUploadDialog;

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

    public App(User usuario) {
        this.usuario = usuario;
    	this.challengesController = new DataManager<Challenge>();
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
            Challenge challenge = new Challenge(title, description, usuario.getUsername());
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
            Quest quest = new Quest(title, description, usuario.getUsername());
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
