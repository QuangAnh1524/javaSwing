package org.example.service;

import org.example.DAO.UserDAO;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String username, String password) {
        return userDAO.validateUser(username, password);
    }
}
