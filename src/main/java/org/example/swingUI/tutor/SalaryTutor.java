package org.example.swingUI.tutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SalaryTutor extends JPanel {
    private JLabel jLabelTitle;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel tableModel;

    public SalaryTutor() {
        setSize(600, 450);
        initComponents();
    }

    private void initComponents() {
        jLabelTitle = new JLabel("Thống kê lương tháng", SwingConstants.CENTER);
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 16));
        jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTitle.setOpaque(true);
        jLabelTitle.setForeground(new Color(70, 130, 180));
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        String[] columnNames = {"Tên học viên", "Môn", "Thứ", "Số tiền / buổi"};
        tableModel = new DefaultTableModel(columnNames, 0);

        jTable = new JTable(tableModel);
        JTableHeader jTableHeader = jTable.getTableHeader();
        jTableHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jTableHeader.setBackground(new Color(70, 130, 180)); // Màu nền header
        jTableHeader.setForeground(Color.WHITE); // Màu chữ header
        jTableHeader.setFont(new Font("Arial", Font.BOLD, 14)); // Font chữ header
        jScrollPane = new JScrollPane(jTable);
        // Thiết lập GroupLayout cho panel
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        // Thiết lập nhóm ngang (Horizontal Group)
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                                .addContainerGap())
        );

        // Thiết lập nhóm dọc (Vertical Group)
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                .addContainerGap())
        );

        // Thêm dữ liệu mẫu vào bảng
        addSampleData();
    }

    private void addSampleData() {
        String[][] data = {
                {"Nguyễn Văn A", "Toán", "Thứ 2", "500,000 VND"},
                {"Trần Thị B", "Văn", "Thứ 3", "400,000 VND"},
                {"Lê Văn C", "Anh", "Thứ 4", "450,000 VND"},
                {"Phạm Thị D", "Lý", "Thứ 5", "500,000 VND"},
                {"Hoàng Văn E", "Hóa", "Thứ 6", "550,000 VND"}
        };

        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Thống kê lương");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new SalaryTutor());
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}