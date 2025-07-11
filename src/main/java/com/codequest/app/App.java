package com.codequest.app;

import java.util.List;

import com.codequest.model.DataManager;
import com.codequest.model.FileHandler;
import com.codequest.model.content.Challenge;
import com.codequest.model.content.Quest;
import com.codequest.model.content.Solution;
import com.codequest.model.User;

public class App {
    private User usuario;
    private DataManager<Challenge> challengesController;
    private DataManager<Quest> questsController;
    private DataManager<Solution> solutionsController;

    private FileHandler<Challenge> challengesDataHandler = new FileHandler<>("data/challenges.dat");
    private FileHandler<Quest> questsDataHandler = new FileHandler<>("data/quests.dat");
    private FileHandler<Solution> solutionsDataHandler = new FileHandler<>("data/solutions.dat");

    public App(User usuario) {
        this.usuario = usuario;
    	this.challengesController = new DataManager<Challenge>(challengesDataHandler);
        this.solutionsController = new DataManager<Solution>(solutionsDataHandler);
        this.questsController = new DataManager<Quest>(questsDataHandler);

        Challenge.setIdCounter(challengesController.getData().size() > 0 ? challengesController.getData().get(challengesController.getData().size() - 1).getId() + 1 : 0);
        Quest.setIdCounter(questsController.getData().size() > 0 ? questsController.getData().get(questsController.getData().size() - 1).getId() + 1 : 0);
        Solution.setIdCounter(solutionsController.getData().size() > 0 ? solutionsController.getData().get(solutionsController.getData().size() - 1).getId() + 1 : 0);

        for (Challenge challenge : challengesController.getData()) {
            for (Solution solution : solutionsController.getData()) {
                if (solution.getChallengeId() == challenge.getId()) {
                    challenge.addSolution(solution.getId());
                }
            }
        }

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

    public Challenge getChallengeByTitle(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        for (Challenge challenge : challengesController.getData()) {
            if (challenge.getTitle().equalsIgnoreCase(title)) {
                return challenge;
            }
        }
        return null;
    }

    public boolean addSolution(Challenge challenge, String solutionText, String solutionTitle) {
        if (challenge == null || solutionText == null || solutionText.isBlank()) {
            return false;
        }
        try {
            Solution solution = new Solution(usuario.getUsername(), solutionTitle, solutionText, challenge.getId());
            solutionsController.addData(solution);
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
            return true;
        } catch (Exception e) {
            System.err.println("Error al subir el quest: " + e.getMessage());
            return false;
        }
    }
    
    public List<Challenge> showChallenges() {
        return challengesController.getData();
    }

    public List<Quest> showQuests() {
        return questsController.getData();
    }

    public List<Solution> showSolutions() {
        return solutionsController.getData();
    }
}
