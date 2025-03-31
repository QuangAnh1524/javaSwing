package org.example.swingUI.tutor;


import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;

public class SalaryTutor extends JPanel {
    private JLabel jLabelTitle;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel tableModel;

    public SalaryTutor() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            // Nếu có DAO/Service cho lương, khởi tạo ở đây
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(600, 450);
        initComponents();
    }

    private void initComponents() {
        jLabelTitle = new JLabel("Thống kê lương tháng", SwingConstants.CENTER);
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 16));
        jLabelTitle.setOpaque(true);
        jLabelTitle.setForeground(new Color(70, 130, 180));
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] columnNames = {"Tên học viên", "Môn", "Thứ", "Số tiền / buổi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jTable = new JTable(tableModel);
        JTableHeader jTableHeader = jTable.getTableHeader();
        jTableHeader.setBackground(new Color(70, 130, 180));
        jTableHeader.setForeground(Color.WHITE);
        jTableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        jScrollPane = new JScrollPane(jTable);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                .addContainerGap())
        );

        addSampleData(); // Giữ nguyên dữ liệu mẫu
    }

    private void addSampleData() {
        String[][] data = {
                {"Nguyễn Văn A", "Toán", "Thứ 2", "500,000 VND"},
                {"Trần Thị B", "Văn", "Thứ 3", "400,000 VND"}
        };
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }
}