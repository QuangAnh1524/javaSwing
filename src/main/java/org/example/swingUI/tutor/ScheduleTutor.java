package org.example.swingUI.tutor;

import org.example.DAO.TutorDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.model.ScheduleRegister;
import org.example.service.ScheduleService;
import org.example.DAO.ScheduleDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ScheduleTutor extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ScheduleService scheduleService;
    private TutorDAO tutorDAO;

    public ScheduleTutor() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
            scheduleService = new ScheduleService(scheduleDAO);
            tutorDAO = new TutorDAO(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Lịch dạy của bạn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] columnNames = {"Tên học viên", "Môn", "Giờ bắt đầu", "Giờ kết thúc", "Ngày bắt đầu", "Ngày kết thúc"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadScheduleData(int userId) {
        int tutorId = tutorDAO.getTutorIdByUserId(userId);
        if (tutorId == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy gia sư tương ứng với tài khoản này!");
            return;
        }

        tableModel.setRowCount(0);
        List<ScheduleRegister> schedules = scheduleService.getTutorSchedules(tutorId);
        for (ScheduleRegister schedule : schedules) {
            Object[] row = {
                    schedule.getTutorName(),
                    schedule.getSubject(),
                    schedule.getTimeStart().toString(),
                    schedule.getTimeEnd().toString(),
                    schedule.getDayStart().toString(),
                    schedule.getDayEnd().toString()
            };
            tableModel.addRow(row);
        }
    }
}