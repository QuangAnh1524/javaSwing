package org.example.swingUI.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminStudentManagement extends JPanel {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;

    public AdminStudentManagement() {
        setLayout(new BorderLayout());

        // Create table model with column names
        String[] columnNames = {"STT", "Schedule_id", "User Name", "Tên sinh viên", "Môn học", "Gia sư"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);

        // Set row height
        studentTable.setRowHeight(40);

        // Set column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Schedule_id
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(150); // User Name
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Tên sinh viên
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Môn học
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Gia sư

        // Add some sample data
        addSampleData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Create a panel for the title, search form and "Thêm mới tài khoản" button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Create a sub-panel for the title and search form
        JPanel titleSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Quản lí tài khoản sinh viên");
        titleSearchPanel.add(titleLabel);

        // Create the search form
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        titleSearchPanel.add(searchLabel);
        titleSearchPanel.add(searchField);
        titleSearchPanel.add(searchButton);

        // Add the titleSearchPanel to the top panel
        topPanel.add(titleSearchPanel, BorderLayout.WEST);

        // Set layout and add components
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the search button click event here
                String searchText = searchField.getText();
                searchStudent(searchText);
            }
        });
    }

    private void addSampleData() {
        Object[][] data = {
                {"1", "SCH001", "studentA", "Nguyen Van A", "Toán", "Tutor A"},
                {"2", "SCH002", "studentB", "Tran Thi B", "Lý", "Tutor B"},
                {"3", "SCH003", "studentC", "Le Van C", "Hóa", "Tutor C"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void searchStudent(String searchText) {
        // Implement search logic here
        JOptionPane.showMessageDialog(null, "Searching for: " + searchText);
    }
}