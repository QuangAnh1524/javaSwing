package org.example.service;

import org.example.DAO.UserDAO;

public class AuthService {
    private boolean isLoggedIn = false;  // Trạng thái đăng nhập

    public boolean login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        boolean isValid = userDAO.validateUser(username, password);
        if (isValid) {
            isLoggedIn = true;
        }
        return isValid;
    }

    public void logout() {
        if (isLoggedIn) {
            System.out.println("Bạn đã đăng xuất!");
            isLoggedIn = false;
        } else {
            System.out.println("Bạn chưa đăng nhập!");
        }
    }

    public boolean isAuthenticated() {
        return isLoggedIn;
    }
}
