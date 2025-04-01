package org.example.swingUI.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewTutorAccount extends JFrame {
    private JTextField nameField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField subjectField;
    private JButton submitButton;

    public AddNewTutorAccount() {
        setTitle("Thêm mới tài khoản gia sư");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the title
        JLabel titleLabel = new JLabel("Thêm mới tài khoản gia sư");
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
        JLabel nameLabel = new JLabel("Tên gia sư:");
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel, gbc);

        gbc.gridx = 1;
        userNameField = new JTextField(20);
        mainPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Password:");
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel subjectLabel = new JLabel("Môn đăng kí dạy:");
        mainPanel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        subjectField = new JTextField(20);
        mainPanel.add(subjectField, gbc);

        // Create the submit button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Xác nhận tạo mới");
        mainPanel.add(submitButton, gbc);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Add action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the submit button click event here
                String name = nameField.getText();
                String userName = userNameField.getText();
                String password = new String(passwordField.getPassword());
                String subject = subjectField.getText();

                // Perform form validation and data processing here
                if (validateForm(name, userName, password, subject)) {
                    JOptionPane.showMessageDialog(null, "Tài khoản gia sư mới đã được tạo thành công!");
                    // Clear form fields
                    clearForm();

                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validateForm(String name, String userName, String password, String subject) {
        // Simple form validation
        return !name.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !subject.isEmpty();
    }

    private void clearForm() {
        nameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        subjectField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddNewTutorAccount().setVisible(true);
            }
        });
    }
}