package org.example.swingUI.student;

import org.example.manager.SessionManager;
import org.example.model.ScheduleRegister;
import org.example.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScheduleStudent extends JPanel {
    private StudentService studentService;

    private JTable table;
    private DefaultTableModel model;


    public ScheduleStudent(StudentService studentService) {
        this.studentService = studentService;
        setLayout(new BorderLayout());

        JButton btnRefresh = new JButton("Refresh schedule");
        btnRefresh.setBackground(new Color(255, 102, 102));
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSchedule();
            }
        });

        // Tiêu đề
        JLabel titleLabel = new JLabel("Lịch học của bạn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Bảng dữ liệu
        String[] columnNames = {"Tên giáo viên", "Môn", "Giờ bắt đầu", "Giờ kết thúc", "Ngày bắt đầu", "Ngày kết thúc"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        header.setBackground(new Color(70, 130, 180)); // Màu nền header
        header.setForeground(Color.WHITE); // Màu chữ header
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Font chữ header

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loadSchedule();

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);

    }

    private void loadSchedule() {
        model.setRowCount(0);

        List<ScheduleRegister> schedules = studentService.getScheduleStudent(SessionManager.getInstance().getUserId());
        for (ScheduleRegister schedule : schedules) {
            model.addRow(new Object[]{
                    schedule.getTutorName(),
                    schedule.getSubject(),
                    schedule.getTimeStart(),
                    schedule.getTimeEnd(),
                    schedule.getDayStart(),
                    schedule.getDayEnd()
            });
        }
        model.fireTableDataChanged();
    }



}
