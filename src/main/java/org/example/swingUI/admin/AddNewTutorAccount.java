package org.example.swingUI.admin;

import org.example.manager.SessionManager;
import org.example.service.AdminService;

import javax.swing.*;
import java.awt.*;

public class AddNewTutorAccount extends JFrame {
    // Các trường nhập thông tin
    private JTextField nameField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField subjectField;
    private JButton submitButton;
    private AdminService adminService;

    // Constructor khởi tạo cửa sổ thêm mới tài khoản gia sư
    public AddNewTutorAccount(AdminService adminService) {
        this.adminService = adminService;
        setTitle("Thêm mới tài khoản gia sư"); // Tiêu đề của cửa sổ
        setSize(400, 300); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng cửa sổ khi bấm nút đóng
        setLocationRelativeTo(null); // Căn giữa cửa sổ
        initComponents(); // Gọi phương thức khởi tạo giao diện
    }

    // Phương thức khởi tạo giao diện
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout()); // Sử dụng GridBagLayout để căn chỉnh bố cục
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL; // Thành phần sẽ dãn theo chiều ngang

        // Tiêu đề chính của form
        JLabel titleLabel = new JLabel("Thêm mới tài khoản gia sư");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Nhãn và ô nhập tên gia sư
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên gia sư:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        // Nhãn và ô nhập tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("User Name:"), gbc);
        gbc.gridx = 1;
        userNameField = new JTextField(20);
        mainPanel.add(userNameField, gbc);

        // Nhãn và ô nhập mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Nhãn và ô nhập môn học đăng ký dạy
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Môn đăng kí dạy:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        mainPanel.add(subjectField, gbc);

        // Nút xác nhận tạo tài khoản
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Xác nhận tạo mới");
        mainPanel.add(submitButton, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Gắn sự kiện cho nút xác nhận
        submitButton.addActionListener(e -> handleSubmit());
    }

    // Xử lý sự kiện khi nhấn nút xác nhận
    private void handleSubmit() {
        String name = nameField.getText().trim();
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String subject = subjectField.getText().trim();

        // Lấy tên admin hiện tại từ session
        String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());

        // Kiểm tra thông tin nhập vào
        if (validateForm(name, username, password, subject)) {
            // Gọi service để tạo tài khoản gia sư mới
            boolean success = adminService.createTutorAccount(adminUsername, username, password, name, "0123456789", 1000000);

            if (success) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản gia sư thành công!");
                clearForm(); // Xóa nội dung trong form sau khi tạo thành công
                dispose(); // Đóng cửa sổ
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Kiểm tra tính hợp lệ của thông tin nhập vào
    private boolean validateForm(String name, String userName, String password, String subject) {
        return !name.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !subject.isEmpty();
    }

    // Xóa nội dung nhập vào trong form
    private void clearForm() {
        nameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        subjectField.setText("");
    }
}
