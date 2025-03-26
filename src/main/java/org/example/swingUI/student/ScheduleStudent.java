package org.example.swingUI.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
public class ScheduleStudent extends JPanel {
    public ScheduleStudent() {
        setLayout(new BorderLayout());
        // Tiêu đề
        JLabel titleLabel = new JLabel("Lịch học của bạn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        // Bảng dữ liệu
        String[] columnNames = {"Tên giáo viên", "Môn", "Giờ bắt đầu", "Giờ kết thúc", "Ngày bắt đầu", "Ngày kết thúc"};
        Object[][] data = {
                {"Nguyễn Văn A ", "Toán", "08:00", "09:30"},
                {"Trần Thị B", "Văn", "10:00", "11:30"},
                {"Lê Văn C", "Anh", "14:00", "15:30"},
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        header.setBackground(new Color(70, 130, 180)); // Màu nền header
        header.setForeground(Color.WHITE); // Màu chữ header
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Font chữ header
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

}
