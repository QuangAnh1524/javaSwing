package org.example.swingUI.tutor;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private JTextField jTextFieldStartDate;
    private JLabel jLabelEndDate;
    private JTextField jTextFieldEndDate;
    private JButton jButtonSubmit;

    public RegisterTutor() {
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
        jTextFieldEndTime.setEditable(false); // Không cho phép sửa đổi thời gian kết thúc
        jLabelStartDate = new JLabel("Ngày bắt đầu");
        jTextFieldStartDate = new JTextField();
        jLabelEndDate = new JLabel("Ngày kết thúc");
        jTextFieldEndDate = new JTextField();
        jTextFieldEndDate.setEditable(false); // Không cho phép sửa đổi ngày kết thúc
        jButtonSubmit = new JButton("Đăng ký");

        // Thiết lập GroupLayout cho panel
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        // Thiết lập nhóm ngang (Horizontal Group)
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
                                        .addComponent(jTextFieldStartDate, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldEndDate, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(46, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150) // Điều chỉnh khoảng cách để căn giữa
                                .addComponent(jButtonSubmit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(150)) // Điều chỉnh khoảng cách để căn giữa

        );

        // Thiết lập nhóm dọc (Vertical Group)
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
                                        .addComponent(jTextFieldStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelStartDate))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldEndDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelEndDate))
                                .addGap(30, 30, 30)
                                .addComponent(jButtonSubmit)
                                .addContainerGap(56, Short.MAX_VALUE))
        );

        // Lắng nghe sự kiện chọn thời gian bắt đầu để tính toán và cập nhật thời gian kết thúc
        jComboBoxStartTime.addActionListener(e -> {
            String startTime = (String) jComboBoxStartTime.getSelectedItem();
            if (startTime != null) {
                // Giả sử thời gian bắt đầu có định dạng HH:mm
                String[] parts = startTime.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                hour += 2; // Thêm 2 giờ

                // Định dạng lại thời gian kết thúc
                String endTime = String.format("%02d:%02d", hour, minute);
                jTextFieldEndTime.setText(endTime);
            }
        });

        // Lắng nghe sự kiện thay đổi ngày bắt đầu để tính toán và cập nhật ngày kết thúc
        jTextFieldStartDate.addActionListener(e -> {
            try {
                String startDateStr = jTextFieldStartDate.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = sdf.parse(startDateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 2); // Thêm 2 tháng
                String endDateStr = sdf.format(calendar.getTime());
                jTextFieldEndDate.setText(endDateStr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Đăng ký lịch dạy");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new RegisterTutor());
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}