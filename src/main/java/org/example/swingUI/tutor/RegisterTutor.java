package org.example.swingUI.tutor;

import org.example.DAO.TutorDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.service.ScheduleService;
import org.example.DAO.ScheduleDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import com.toedter.calendar.JDateChooser;

public class RegisterTutor extends JPanel {
    private JLabel jLabelTutorName;
    private JTextField jTextFieldTutorName;
    private JLabel jLabelSubject;
    private JTextField jTextFieldSubject;
    private JLabel jLabelStartTime;
    private JComboBox<String> jComboBoxStartTime;
    private JLabel jLabelEndTime;
    private JTextField jTextFieldEndTime;
    private JLabel jLabelStartDate;
    private JDateChooser jDateChooserStartDate;
    private JLabel jLabelEndDate;
    private JDateChooser jDateChooserEndDate;
    private JButton jButtonSubmit;

    private ScheduleService scheduleService;
    private TutorDAO tutorDAO;

    public RegisterTutor() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
            scheduleService = new ScheduleService(scheduleDAO);
            tutorDAO = new TutorDAO(connection); // Khởi tạo TutorDAO
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu!");
        }
        setSize(600, 450);
        initComponents();
    }

    private void initComponents() {
        jLabelTutorName = new JLabel("Tên gia sư");
        jTextFieldTutorName = new JTextField();
        jLabelSubject = new JLabel("Tên môn học");
        jTextFieldSubject = new JTextField();
        jLabelStartTime = new JLabel("Thời gian bắt đầu");
        jComboBoxStartTime = new JComboBox<>(new String[]{"08:00", "09:00", "10:00", "13:00", "14:00", "15:00", "16:00", "17:00"});
        jLabelEndTime = new JLabel("Thời gian kết thúc");
        jTextFieldEndTime = new JTextField();
        jTextFieldEndTime.setEditable(false);
        jLabelStartDate = new JLabel("Ngày bắt đầu");
        jDateChooserStartDate = new JDateChooser();
        jLabelEndDate = new JLabel("Ngày kết thúc");
        jDateChooserEndDate = new JDateChooser();
        jDateChooserEndDate.setEnabled(false);
        jButtonSubmit = new JButton("Đăng ký");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTutorName, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelSubject, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelStartTime, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelEndTime, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelStartDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelEndDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldTutorName, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldSubject, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxStartTime, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldEndTime, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jDateChooserStartDate, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jDateChooserEndDate, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(46, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150)
                                .addComponent(jButtonSubmit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(150))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldTutorName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelTutorName))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelSubject))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBoxStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelStartTime))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldEndTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelEndTime))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jDateChooserStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelStartDate))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jDateChooserEndDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelEndDate))
                                .addGap(30, 30, 30)
                                .addComponent(jButtonSubmit)
                                .addContainerGap(56, Short.MAX_VALUE))
        );

        jComboBoxStartTime.addActionListener(e -> {
            String startTime = (String) jComboBoxStartTime.getSelectedItem();
            if (startTime != null) {
                String[] parts = startTime.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                hour += 2;
                String endTime = String.format("%02d:%02d", hour, minute);
                jTextFieldEndTime.setText(endTime);
            }
        });

        jDateChooserStartDate.addPropertyChangeListener("date", evt -> {
            java.util.Date startDate = jDateChooserStartDate.getDate();
            if (startDate != null) {
                LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localEndDate = localStartDate.plusMonths(2);
                jDateChooserEndDate.setDate(java.util.Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        });

        jButtonSubmit.addActionListener(e -> {
            try {
                String subject = jTextFieldSubject.getText();
                String startTimeStr = (String) jComboBoxStartTime.getSelectedItem();
                String endTimeStr = jTextFieldEndTime.getText();
                java.util.Date startDate = jDateChooserStartDate.getDate();
                java.util.Date endDate = jDateChooserEndDate.getDate();

                if (startDate == null || endDate == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và ngày kết thúc!");
                    return;
                }

                LocalTime timeStart = LocalTime.parse(startTimeStr);
                LocalTime timeEnd = LocalTime.parse(endTimeStr);
                LocalDate dayStart = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dayEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int userId = SessionManager.getInstance().getUserId(); // Lấy user_id từ session
                int tutorId = tutorDAO.getTutorIdByUserId(userId); // Lấy tutor_id từ user_id

                if (tutorId == -1) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy gia sư tương ứng với tài khoản này!");
                    return;
                }

                boolean success = scheduleService.createSchedule(tutorId, subject, timeStart, timeEnd, dayStart, dayEnd);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Đăng ký lịch dạy thành công!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Có thể lịch bị trùng!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
    }

    private void clearFields() {
        jTextFieldTutorName.setText("");
        jTextFieldSubject.setText("");
        jComboBoxStartTime.setSelectedIndex(0);
        jTextFieldEndTime.setText("");
        jDateChooserStartDate.setDate(null);
        jDateChooserEndDate.setDate(null);
    }
}