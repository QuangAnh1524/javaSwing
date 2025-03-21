package org.example.service;

import org.example.DAO.UserDAO;

public class AuthService {
    private boolean isLoggedIn = false; 

    public boolean login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        boolean isValid = userDAO.validateUser(username, password);
        if (isValid) {
            isLoggedIn = true;
        }
        return isValid;
    }

    public boolean logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
            return true;
        }
        return false;
    }

    public boolean isAuthenticated() {
        return isLoggedIn;
    }
}