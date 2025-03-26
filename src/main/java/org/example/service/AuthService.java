package org.example.service;

import org.example.DAO.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private UserDAO userDAO;
    private boolean isLoggedIn = false;

    public AuthService(UserDAO dao){
        this.userDAO = dao;
    }
    public boolean login(String username, String password) {

        boolean isValid = userDAO.validateUser(username, password);
        if (isValid) {
            isLoggedIn = true;
        }
        return isValid;
    }

    public String getRoleByUsername(String username){
        return userDAO.getRoleByUsername(username);
    }


    public int getUserIdByUsername(String username){
        return userDAO.getUserIdByUsername(username);
    }

    public boolean logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
            return true;
        }
        return false;
    }

}