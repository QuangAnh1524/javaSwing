package org.example.swingUI.controller;

import org.example.service.AuthService;
import org.example.swingUI.listener.LoginListener;

import javax.swing.*;

public class LoginController implements LoginListener {
    private final JFrame loginForm;

    public LoginController(JFrame loginForm) {
        this.loginForm = loginForm;
    }

    @Override
    public void onLogin(String username, String password) {
        AuthService authService = new AuthService();
        boolean isValid = authService.login(username, password);

        if (isValid) {
            JOptionPane.showMessageDialog(loginForm, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // chuyển sang giao diện chính ở đây
            // loginForm.dispose();
        } else {
            JOptionPane.showMessageDialog(loginForm, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}