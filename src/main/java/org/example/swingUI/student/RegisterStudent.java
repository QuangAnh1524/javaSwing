package org.example.swingUI.student;

import org.example.manager.SessionManager;
import org.example.model.ScheduleRegister;
import org.example.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterStudent extends JPanel {
    private StudentService studentService;
    private JPanel mainPanel;
    private Map<JButton, ScheduleRegister> map = new HashMap<>(); // Lưu button và lịch học

    public RegisterStudent(StudentService studentService) {
        this.studentService = studentService;
        setSize(600, 450);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loadSchedule();

        JPanel findPanel = new JPanel(new FlowLayout());
        JTextField txtFind = new JTextField();
        txtFind.setPreferredSize(new Dimension(200, 30));
        JButton btnFind = new JButton("Tìm kiếm");

        findPanel.add(txtFind);
        findPanel.add(btnFind);

        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                String key = txtFind.getText().toLowerCase();
                if(key.isEmpty()){
                    loadSchedule();
                }
                else{
                    List<ScheduleRegister> schedules = studentService.getEmptySchedule();
                    for (ScheduleRegister schedule : schedules) {
                        if(schedule.getSubject().toLowerCase().equals(key)){
                            mainPanel.add(createChildPanel(schedule));
                        }
                    }
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
        });





        add(findPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    private void loadSchedule() {
        mainPanel.removeAll();
        List<ScheduleRegister> schedules = studentService.getEmptySchedule();
        for (ScheduleRegister schedule : schedules) {
            mainPanel.add(createChildPanel(schedule));
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }



    private JPanel createChildPanel(ScheduleRegister schedule) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Nút đăng ký
        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBackground(new Color(179, 218, 255));
        btnRegister.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Lưu button và lịch học vào HashMap
        map.put(btnRegister, schedule);


        // Xử lý sự kiện khi bấm vào nút đăng ký
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScheduleRegister schedule = map.get(e.getSource());
                if (schedule != null) {
                    int option = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(btnRegister), "Do you want to register?", "Register", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        boolean rs =  studentService.registerScheduleStudent(
                                SessionManager.getInstance().getUserId(),
                                schedule.getScheduleId(),
                                schedule.getTutorId());
                        if(rs){
                            JOptionPane.showMessageDialog( SwingUtilities.getWindowAncestor(btnRegister), "Register successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            loadSchedule();
                        }
                        else {
                            JOptionPane.showMessageDialog( SwingUtilities.getWindowAncestor(btnRegister), "Register failed, please check your schedule!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Panel hiển thị thông tin lịch học
        JPanel labelPanel = new JPanel(new GridLayout(8, 1));
        String[] labels = {"Họ và tên: ", "Số điện thoại: ", "Chuyên môn: ", "Khung giờ: ",
                "Ngày bắt đầu: ","Ngày kết thúc: ", "Giá: "};
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        String[] values = {
                schedule.getTutorName(),
                schedule.getPhoneNumber(),
                schedule.getSubject(),
                schedule.getTimeStart().toString() + " - " + schedule.getTimeEnd().toString(),
                schedule.getDayStart().toString(),
                schedule.getDayEnd().toString(),
                currencyFormat.format(schedule.getPrice()) + " VND"
        };

        // Hiển thị thông tin lịch học
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + values[i], SwingConstants.LEFT);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label.setForeground(new Color(135, 135, 135));
            labelPanel.add(label);
        }

        panel.add(btnRegister, BorderLayout.NORTH);
        panel.add(labelPanel, BorderLayout.CENTER);
        return panel;
    }




}
