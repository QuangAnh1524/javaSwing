package org.example.swingUI.tutor;


import org.example.DAO.TutorDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.model.ScheduleTutorSalary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SalaryTutor extends JPanel {
    private JLabel jLabelTitle;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel tableModel;
    private TutorDAO tutorDAO;

    private int sumSalary;

    public SalaryTutor() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            tutorDAO = new TutorDAO(connection);
            sumSalary = 0;
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

        String[] columnNames = {"Tên học viên", "Môn", "Số tiền"};
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

        addSampleData();
    }

    private void addSampleData() {
        int tutorId = tutorDAO.getTutorIdByUserId(SessionManager.getInstance().getUserId());
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        List<ScheduleTutorSalary> data = tutorDAO.getTutorSalary(tutorId);
        for (ScheduleTutorSalary row : data) {
            tableModel.addRow(new Object[]{row.getStudentName(), row.getSubjectName(), currencyFormat.format(row.getSalary()) + " VND"});
            sumSalary += (row.getSalary()*16);
        }
        String sum = currencyFormat.format(sumSalary) + " VND";
        tableModel.addRow(new String[]{"", "", sum});
    }
}