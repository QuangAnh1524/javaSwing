package org.example.swingUI.admin;

import javax.swing.*;
import java.awt.*;

public class TutorDetailForm extends JFrame {
    private JTextField nameField;
    private JTextField userNameField;
    private JTextField phoneField;
    private JTextField subjectField;
    private JTextField salaryField;
    private JButton closeButton;

    public TutorDetailForm(String name, String userName, String phone, String subject, String salary) {
        setTitle("Chi tiết thông tin gia sư");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the title
        JLabel titleLabel = new JLabel("Chi tiết thông tin gia sư");
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
        JLabel nameLabel = new JLabel("Họ tên:");
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setText(name);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel, gbc);

        gbc.gridx = 1;
        userNameField = new JTextField(20);
        userNameField.setText(userName);
        mainPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        mainPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        phoneField.setText(phone);
        mainPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel subjectLabel = new JLabel("Môn dạy:");
        mainPanel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        subjectField = new JTextField(20);
        subjectField.setText(subject);
        mainPanel.add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel salaryLabel = new JLabel("Lương / tháng:");
        mainPanel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        salaryField = new JTextField(20);
        salaryField.setText(salary);
        mainPanel.add(salaryField, gbc);

        // Create the close button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        closeButton = new JButton("Đóng");
        mainPanel.add(closeButton, gbc);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Add action listener for the close button
        closeButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorDetailForm("Nguyễn Khắc Minh", "minhnguyen2712", "0327593620", "Toán", "1000000").setVisible(true));
    }
}
