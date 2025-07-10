package com.codequest.app;

import java.util.List;
import java.io.IOException;
import javax.swing.JFrame;

import com.codequest.model.DataManager;
import com.codequest.app.FileHandlerSynchronous;
import com.codequest.model.content.Challenge;
import com.codequest.model.content.Quest;
import com.codequest.model.content.Solution;
import com.codequest.model.content.ForumPost;
import com.codequest.model.User;
import com.codequest.ui.utils.FileUploadUtils;
import com.codequest.ui.components.FileUploadDialog;

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
        // Aquí usamos hashCode, pero puedes cambiarlo si tienes otro criterio
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
            int id = 1;
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
            int id = 1;
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
