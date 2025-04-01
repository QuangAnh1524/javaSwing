package org.example.swingUI.register;

import org.example.DAO.StudentDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.service.AuthService;
import org.example.service.StudentRegistrationService;
import org.example.service.StudentService;
import org.example.swingUI.login.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class RegisterForm extends JFrame {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField gradeField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton registerButton;
    private JLabel loginLink;

    private StudentRegistrationService registrationService;
    private AuthService authService;
    private StudentService studentService;

    public RegisterForm(AuthService authService, StudentService studentService) {
        this.authService = authService;
        this.studentService = studentService;
        initServices();

        setTitle("Đăng kí");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initServices() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            StudentDAO studentDAO = new StudentDAO(connection);
            this.registrationService = new StudentRegistrationService(userDAO, studentDAO);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Đăng kí");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Trường User Name
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("User Name:"), gbc);
        gbc.gridx = 1;
        userNameField = new JTextField(20);
        mainPanel.add(userNameField, gbc);

        // Trường Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Trường Xác nhận mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        mainPanel.add(confirmPasswordField, gbc);

        // Trường Họ và tên
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        // Trường Tuổi
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Tuổi:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(20);
        mainPanel.add(ageField, gbc);

        // Trường Lớp
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Lớp:"), gbc);
        gbc.gridx = 1;
        gradeField = new JTextField(20);
        mainPanel.add(gradeField, gbc);

        // Trường Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        mainPanel.add(phoneField, gbc);

        // Trường Email
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Nút Đăng kí
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Đăng kí");
        mainPanel.add(registerButton, gbc);

        // Link Đã có tài khoản?
        gbc.gridy = 10;
        loginLink = new JLabel("Đã có tài khoản?");
        loginLink.setForeground(Color.BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginLink, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Sự kiện nút Đăng kí
        registerButton.addActionListener(e -> handleRegister());

        // Sự kiện click vào link Đã có tài khoản?
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginForm(authService, studentService).setVisible(true);
            }
        });
    }

    private void handleRegister() {
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String grade = gradeField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Kiểm tra đầu vào
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                name.isEmpty() || ageStr.isEmpty() || grade.isEmpty() ||
                phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi StudentRegistrationService để đăng ký
        try {
            boolean success = registrationService.registerStudent(username, password, name, age, grade, phone, email);
            if (success) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                dispose();
                new LoginForm(authService, studentService).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        userNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        nameField.setText("");
        ageField.setText("");
        gradeField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                UserDAO userDAO = new UserDAO(connection);
                StudentDAO studentDAO = new StudentDAO(connection);
                AuthService authService = new AuthService(userDAO);
                StudentService studentService = new StudentService(studentDAO);
                new RegisterForm(authService, studentService).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}