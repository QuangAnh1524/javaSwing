package org.example.swingUI.admin;

import org.example.manager.SessionManager;
import org.example.service.AdminService;

import javax.swing.*;
import java.awt.*;

public class AddNewTutorAccount extends JFrame {
    private JTextField nameField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField subjectField;
    private JButton submitButton;
    private AdminService adminService;

    public AddNewTutorAccount(AdminService adminService) {
        this.adminService = adminService;
        setTitle("Thêm mới tài khoản gia sư");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Thêm mới tài khoản gia sư");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên gia sư:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("User Name:"), gbc);
        gbc.gridx = 1;
        userNameField = new JTextField(20);
        mainPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Môn đăng kí dạy:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        mainPanel.add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Xác nhận tạo mới");
        mainPanel.add(submitButton, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String subject = subjectField.getText().trim();
        String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());

        if (validateForm(name, username, password, subject)) {
            boolean success = adminService.createTutorAccount(adminUsername, username, password, name, "0123456789", 1000000);
            if (success) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản gia sư thành công!");
                clearForm();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateForm(String name, String userName, String password, String subject) {
        return !name.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !subject.isEmpty();
    }

    private void clearForm() {
        nameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        subjectField.setText("");
    }
}