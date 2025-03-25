package org.example.swingUI.tutor;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
public class ScheduleTutor extends JPanel{
    public ScheduleTutor(){
        setLayout(new BorderLayout());

        // setTitle
        JLabel titleLabel = new JLabel("Lịch dạy của bạn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //setTable
        String[] columnNames = {"Tên học viên", "Môn", "Giờ bắt đầu", "Giờ kết thúc", "Ngày bắt đầu", "Ngày kết thúc"};
        Object[][] data = {
                {"Nguyễn Khắc Minh", "Toán", "08:00", "09:30", "20-10-2025", "20-12-2025"},
                {"Hạ Cảnh Tùng", "Văn", "10:00", "11:30", "20-10-2025", "20-12-2025"},
                {"Nguyễn Quang Anh", "Anh", "14:00", "15:30", "20-10-2025", "20-12-2025"},
                {"", "Lý", "14:00", "15:30", "20-10-2025", "20-12-2025"},
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


//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Schedule Tutor");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new ScheduleTutor());
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
