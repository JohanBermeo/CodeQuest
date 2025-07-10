package com.codequest.model;

import java.io.Serializable; 
import com.codequest.model.interfaces.Identifiable;
import java.util.Date;

/**
 * Clase User mejorada
 */
public class User implements Identifiable, Serializable { 
    private static final long serialVersionUID = 1L; 
    
    private final int id;
    private String username;
    private String passwordHash;
    private Date birthday;

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
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}