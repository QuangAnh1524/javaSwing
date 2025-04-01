package org.example.swingUI.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminSalaryManagement extends JPanel {
    private JTable salaryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton exportButton;

    public AdminSalaryManagement() {
        setLayout(new BorderLayout());

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for the title and search bar
        JPanel titlePanel = new JPanel(new BorderLayout());

        // Create the title
        JLabel titleLabel = new JLabel("Bảng lương tháng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Create a panel for the export button and search bar
        JPanel actionPanel = new JPanel(new BorderLayout());

        // Create the export button panel
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exportButton = new JButton("Xuất file");
        exportPanel.add(exportButton);
        actionPanel.add(exportPanel, BorderLayout.WEST);

        // Create the search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        actionPanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Create table model with column names
        String[] columnNames = {"STT", "Tên gia sư", "Môn dạy", "Số lượng lớp", "Lương / lớp", "Tổng lương"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salaryTable = new JTable(tableModel);

        // Set row height
        salaryTable.setRowHeight(40);

        // Set column widths
        salaryTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        salaryTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên gia sư
        salaryTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Môn dạy
        salaryTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Số lượng lớp
        salaryTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Lương / lớp
        salaryTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Tổng lương

        // Add some sample data
        addSampleData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(salaryTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set layout and add components
        add(mainPanel, BorderLayout.CENTER);

        // Add action listener for the export button
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the export button click event here
                exportTableData();
            }
        });
    }

    private void addSampleData() {
        Object[][] data = {
                {"1", "Nguyen Van A", "Toán", "3", "200000", "600000"},
                {"2", "Tran Thi B", "Lý", "2", "250000", "500000"},
                {"3", "Le Van C", "Hóa", "4", "150000", "600000"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void exportTableData() {
        // Implement export logic here
        JOptionPane.showMessageDialog(this, "Exporting table data...");
    }
}