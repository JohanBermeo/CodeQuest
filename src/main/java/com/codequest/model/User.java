package com.codequest.model;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable; 
import com.codequest.model.interfaces.Identifiable;

/**
 * Clase User mejorada
 */
public class User implements Identifiable, Serializable { 
    private static final long serialVersionUID = 1L; 

    private final int id;
    private String username;
    private String passwordHash;
    private Date birthday;
    private Set<Integer> challengesLikedIds = new HashSet<>();
    private Set<Integer> questsLikedIds = new HashSet<>();

    private Date dateCreated;

    public User(String username, String password, Date birthday) {
        this.id = username.hashCode();
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.birthday = new Date(birthday.getTime()); // Copia defensiva
        this.dateCreated = new Date();
    }

    public boolean validatePassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        // Implementar hash seguro (BCrypt, etc.)
        // Simplificado para el ejemplo
        return Integer.toString(password.hashCode());
    }

    // Getters
    @Override
    public int getId() {
        return username.hashCode();
    }

    public boolean idEquals(int id) {
        return this.id == id;
    }

    public String getUsername() {
        return username;
    }

    public Date getBirthday() { 
        return new Date(birthday.getTime()); 
    }

    public Date getDateCreated() {
        return new Date(dateCreated.getTime());
    }

    public Set<Integer> getChallengesLikedIds() {
        return new HashSet<>(challengesLikedIds);
    }

    public Set<Integer> getQuestsLikedIds() {
        return new HashSet<>(questsLikedIds); 
    }

    public void addChallengeLikedId(int challengeId) {
        challengesLikedIds.add(challengeId);
    }

    public void removeChallengeLikedId(int challengeId) {
        challengesLikedIds.remove(challengeId);
    }

    public void addQuestLikedId(int solutionId) {
        questsLikedIds.add(solutionId);
    }

    public void removeQuestLikedId(int solutionId) {
        questsLikedIds.remove(solutionId);
    }
}