package org.example.swingUI.tutor;

import org.example.DAO.TutorDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.model.Tutor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ProfileTutor extends JPanel {
    private JButton jButtonSave;
    private JLabel jLabelName;
    private JLabel jLabelPhone;
    private JLabel jLabelSalary;
    private JTextField jTextFieldName;
    private JTextField jTextFieldPhone;
    private JTextField jTextFieldSalary;
    private TutorDAO tutorDAO;

    public ProfileTutor() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            tutorDAO = new TutorDAO(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(600, 450);
        initComponents();
    }

    private void initComponents() {
        jLabelName = new JLabel("Họ và tên:");
        jTextFieldName = new JTextField();
        jLabelPhone = new JLabel("Số điện thoại:");
        jTextFieldPhone = new JTextField();
        jLabelSalary = new JLabel("Lương:");
        jTextFieldSalary = new JTextField();
        jButtonSave = new JButton("Lưu");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelName)
                                        .addComponent(jLabelPhone)
                                        .addComponent(jLabelSalary))
                                .addGap(20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextFieldName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldPhone, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldSalary, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150)
                                .addComponent(jButtonSave, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelName)
                                .addComponent(jTextFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelPhone)
                                .addComponent(jTextFieldPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelSalary)
                                .addComponent(jTextFieldSalary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(30)
                        .addComponent(jButtonSave)
        );

        jButtonSave.addActionListener(e -> {
            try {
                int userId = SessionManager.getInstance().getUserId();
                int tutorId = tutorDAO.getTutorIdByUserId(userId);
                if (tutorId == -1) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy gia sư tương ứng với tài khoản này!");
                    return;
                }

                String name = jTextFieldName.getText();
                String phone = jTextFieldPhone.getText();
                int salary = Integer.parseInt(jTextFieldSalary.getText());
                boolean success = tutorDAO.updateTutor(tutorId, name, phone, salary);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
    }

    public void loadProfileData(int userId) {
        int tutorId = tutorDAO.getTutorIdByUserId(userId);
        if (tutorId == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy gia sư tương ứng với tài khoản này!");
            return;
        }

        Tutor tutor = tutorDAO.getTutorById(tutorId);
        if (tutor != null) {
            jTextFieldName.setText(tutor.getName());
            jTextFieldPhone.setText(tutor.getPhoneNumber());
            jTextFieldSalary.setText(String.valueOf(tutor.getSalary()));
        }
    }
}
