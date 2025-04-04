package org.example.swingUI.login;

import org.example.DAO.StudentDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.service.AuthService;
import org.example.service.StudentService;
import org.example.swingUI.admin.AdminDashboard;
import org.example.swingUI.register.RegisterForm;
import org.example.swingUI.student.DashboardStudent;
import org.example.swingUI.tutor.DashboardTutor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginForm extends JFrame {
    private JTextField userNameField;
    private JPasswordField passField;
    private AuthService auth;
    private StudentService studentService;


    public LoginForm(AuthService auth, StudentService studentService) {
        this.auth = auth;
        this.studentService = studentService;
        setTitle("Login Form");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel left - image screen
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/java/org/example/resources/screen_login.png");
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        leftPanel.setBounds(0, 0, 400, 450);
        add(leftPanel);

        // Panel right - form login
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(400, 0, 400, 450);
        rightPanel.setBackground(new Color(102, 153, 255));
        rightPanel.setLayout(null);
        add(rightPanel);

        // Title "Welcome!"
        JLabel titleLabel = new JLabel("Welcome !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(130, 30, 200, 30);
        rightPanel.add(titleLabel);

        // Label "Login To Your Account"
        JLabel loginLabel = new JLabel("Login To Your Account");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setBounds(120, 60, 200, 30);
        rightPanel.add(loginLabel);

        // Label Username
        JLabel userLabel = new JLabel("Username :");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 120, 100, 20);
        rightPanel.add(userLabel);

        // Icon Username
        ImageIcon userIcon = new ImageIcon(new ImageIcon("src/main/java/org/example/resources/ic_user.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setBounds(50, 145, 24, 24);
        rightPanel.add(userIconLabel);

        // UsernameField
        userNameField = new JTextField();
        userNameField.setBounds(80, 145, 270, 30);
        rightPanel.add(userNameField);

        // Label Password
        JLabel passLabel = new JLabel("Password :");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 185, 100, 20);
        rightPanel.add(passLabel);

        // Icon Password
        ImageIcon passIcon = new ImageIcon(new ImageIcon("src/main/java/org/example/resources/ic_password.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JLabel passIconLabel = new JLabel(passIcon);
        passIconLabel.setBounds(50, 215, 24, 24);
        rightPanel.add(passIconLabel);

        // PasswordField
        passField = new JPasswordField();
        passField.setBounds(80, 215, 270, 30);
        rightPanel.add(passField);

        // Button Login
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(80, 260, 270, 40);
        loginButton.setBackground(Color.RED);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(loginButton);

        // Button Register
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(80, 310, 270, 40);
        registerButton.setBackground(new Color(0, 153, 76));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(registerButton);

        // ActionListener for login button
        loginButton.addActionListener(e -> handleLogin());

        // ActionListener for register button
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterForm(auth, studentService).setVisible(true);
        });
    }

    private void handleLogin() {
        String username = userNameField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean isValid = auth.login(username, password);
            if (isValid) {
                userNameField.setText("");
                passField.setText("");
                int userId = auth.getUserIdByUsername(username);
                SessionManager.getInstance().setUserId(userId);
                String role = auth.getRoleByUsername(username);

                dispose();
                if ("tutor".equals(role)) {
                    new DashboardTutor(auth, studentService).setVisible(true);
                } else if ("student".equals(role)) {
                    new DashboardStudent(this, studentService).setVisible(true);
                } else if ("admin".equals(role)) {
                    new AdminDashboard().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                UserDAO userDAO = new UserDAO(connection);
                StudentDAO studentDAO = new StudentDAO(connection);
                AuthService authService = new AuthService(userDAO);
                StudentService studentService = new StudentService(studentDAO);
                new LoginForm(authService, studentService).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}