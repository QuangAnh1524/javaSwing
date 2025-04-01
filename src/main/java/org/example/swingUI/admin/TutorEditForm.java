package org.example.swingUI.admin;

import org.example.manager.SessionManager;
import org.example.model.Tutor;
import org.example.service.AdminService;

import javax.swing.*;
import java.awt.*;

public class TutorEditForm extends JFrame {
    private JTextField nameField;
    private JTextField userNameField;
    private JTextField phoneField;
    private JTextField subjectField;
    private JTextField salaryField;
    private JButton updateButton;
    private Tutor tutor;
    private AdminService adminService;

    public TutorEditForm(Tutor tutor, AdminService adminService) {
        this.tutor = tutor;
        this.adminService = adminService;
        setTitle("Sửa thông tin gia sư");
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

        JLabel titleLabel = new JLabel("Sửa thông tin gia sư");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(tutor.getName(), 20);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("User Name:"), gbc);
        gbc.gridx = 1;
        userNameField = new JTextField(20);
        String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());
        userNameField.setText(adminService.getUsernameByUserId(tutor.getUserId()));
        userNameField.setEditable(false);
        mainPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(tutor.getPhoneNumber(), 20);
        mainPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Môn dạy:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField("N/A", 20);
        mainPanel.add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Lương / tháng:"), gbc);
        gbc.gridx = 1;
        salaryField = new JTextField(String.valueOf(tutor.getSalary()), 20);
        mainPanel.add(salaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        updateButton = new JButton("Cập nhật");
        mainPanel.add(updateButton, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        updateButton.addActionListener(e -> handleUpdate());
    }

    private void handleUpdate() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String salaryStr = salaryField.getText().trim();

        try {
            int salary = Integer.parseInt(salaryStr);
            boolean success = adminService.updateTutor(tutor.getTutorId(), name, phone, salary);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin gia sư thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lương phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}