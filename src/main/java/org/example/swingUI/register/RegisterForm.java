package org.example.swingUI.register;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel loginLink;

    public RegisterForm() {
        setTitle("Đăng kí");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the title
        JLabel titleLabel = new JLabel("Đăng kí");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Create form labels and fields
        gbc.gridy = 1;
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel, gbc);

        gbc.gridx = 1;
        userNameField = new JTextField(20);
        mainPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:");
        mainPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        mainPanel.add(confirmPasswordField, gbc);

        // Create the register button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Đăng kí");
        mainPanel.add(registerButton, gbc);

        // Create the login link
        gbc.gridy = 5;
        loginLink = new JLabel("Đã có tài khoản?");
        loginLink.setForeground(Color.BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginLink, gbc);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}