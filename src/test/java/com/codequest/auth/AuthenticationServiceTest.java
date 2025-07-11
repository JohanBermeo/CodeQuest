package com.codequest.auth;

import java.util.Date;

import com.codequest.model.User;
import com.codequest.model.DataManager;
import com.codequest.model.FileHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    public static final String testUserFile = "src/test/resources/test-data/test-users.dat";

    @BeforeEach
    void setUp() {
        FileHandler<User> fileHandler = new FileHandler<User>(testUserFile);
        DataManager<User> dataManager = new DataManager<User>(fileHandler);
        if (dataManager.isEmpty()) {
            dataManager.addData(new User("admin", "Admin12345", new Date()));
            dataManager.saveData(); 
        }
        authenticationService = new AuthenticationService(dataManager, fileHandler);
    }

    @Test
    public void testLoginWithValidCredentials() {
        assertTrue(authenticationService.login("admin", "Admin12345"), "Login should succeed with valid credentials");
    }

    @Test
    public void testLoginWithInexistentUser() {
        assertFalse(authenticationService.login("nonexistent", "1234"), "Login should fail for inexistent user");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        assertFalse(authenticationService.login("admin", "wrongpassword"), "Login should fail with invalid password");
    }

    @Test
    public void testLoginWithNullUsername() {
        assertFalse(authenticationService.login(null, "password"), "Login should throw exception for null username");
    }

    @Test
    public void testLoginWithNullPassword() {
        assertFalse(authenticationService.login("admin", null), "Login should throw exception for null password");
    }
}