package model;

import java.io.Serializable; 
import model.interfaces.Identifiable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Clase User mejorada
 */
public class User implements Identifiable, Serializable { 
    private static final long serialVersionUID = 1L; 
    
    private final int id;
    private String username;
    private String passwordHash;
    private Date birthday;
    private List<User> followers;
    private List<User> following;
    private Date dateCreated;
    
    public User(String username, String password, Date birthday) {
        this.id = username.hashCode();
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.birthday = new Date(birthday.getTime()); // Copia defensiva
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.dateCreated = new Date();
    }
    
    public boolean validatePassword(String password) {
        return hashPassword(password).equals(this.passwordHash);
    }
    
    public void followUser(User user) {
        if (user != null && !following.contains(user) && !user.equals(this)) {
            following.add(user);
            user.addFollower(this);
        }
    }
    
    public void unfollowUser(User user) {
        if (user != null && following.contains(user)) {
            following.remove(user);
            user.removeFollower(this);
        }
    }
    
    private void addFollower(User user) {
        if (user != null && !followers.contains(user)) {
            followers.add(user);
        }
    }
    
    private void removeFollower(User user) {
        followers.remove(user);
    }
    
    private String hashPassword(String password) {
        // Implementar hash seguro (BCrypt, etc.)
        // Simplificado para el ejemplo
        return Integer.toString(password.hashCode());
    }
    
    // Getters
    @Override
    public int getId() {
        return id;
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
    
    public List<User> getFollowers() { 
        return new ArrayList<>(followers); 
    }
    
    public List<User> getFollowing() { 
        return new ArrayList<>(following); 
    }
    
    public Date getDateCreated() {
        return new Date(dateCreated.getTime());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}