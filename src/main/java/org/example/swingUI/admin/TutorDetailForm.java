package org.example.swingUI.admin;

import org.example.model.ScheduleRegister;
import org.example.model.Tutor;
import org.example.service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TutorDetailForm extends JFrame {
    private JTextField nameField;
    private JTextField userNameField;
    private JTextField phoneField;
    private JTextField subjectField;
    private JTextField salaryField;
    private JButton closeButton;
    private AdminService adminService;

    // Constructor nhận Tutor và AdminService để lấy thông tin username và môn học
    public TutorDetailForm(Tutor tutor, AdminService adminService) {
        this.adminService = adminService;
        setTitle("Chi tiết thông tin gia sư");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tạo tiêu đề
        JLabel titleLabel = new JLabel("Chi tiết thông tin gia sư");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Tạo nhãn và trường nhập liệu cho họ tên
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Họ tên:");
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setText(tutor.getName());
        nameField.setEditable(false); // Không cho chỉnh sửa
        mainPanel.add(nameField, gbc);

        // Tạo nhãn và trường nhập liệu cho username
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel, gbc);

        gbc.gridx = 1;
        userNameField = new JTextField(20);
        String username = adminService.getUsernameByUserId(tutor.getUserId());
        userNameField.setText(username != null ? username : "N/A");
        userNameField.setEditable(false);
        mainPanel.add(userNameField, gbc);

        // Tạo nhãn và trường nhập liệu cho số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        mainPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        phoneField.setText(tutor.getPhoneNumber());
        phoneField.setEditable(false);
        mainPanel.add(phoneField, gbc);

        // Tạo nhãn và trường nhập liệu cho môn dạy
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel subjectLabel = new JLabel("Môn dạy:");
        mainPanel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        subjectField = new JTextField(20);
        // Lấy danh sách lịch học của gia sư từ bảng schedules dựa trên tutor_id
        List<ScheduleRegister> schedules = adminService.getSchedulesByTutorId(tutor.getTutorId());
        // Lấy danh sách môn học từ các lịch, loại bỏ trùng lặp
        List<String> subjects = schedules.stream()
                .map(ScheduleRegister::getSubject)
                .distinct()
                .collect(Collectors.toList());
        subjectField.setText(subjects.isEmpty() ? "N/A" : String.join(", ", subjects));
        subjectField.setEditable(false);
        mainPanel.add(subjectField, gbc);

        // Tạo nhãn và trường nhập liệu cho lương
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel salaryLabel = new JLabel("Lương:");
        mainPanel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        salaryField = new JTextField(20);
        salaryField.setText(String.valueOf(tutor.getSalary()));
        salaryField.setEditable(false);
        mainPanel.add(salaryField, gbc);

        // Tạo nút đóng
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        closeButton = new JButton("Đóng");
        mainPanel.add(closeButton, gbc);

        // Thiết lập layout và thêm các thành phần
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Thêm sự kiện cho nút đóng
        closeButton.addActionListener(e -> dispose());
    }
}