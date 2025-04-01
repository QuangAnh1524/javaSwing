package org.example.swingUI.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminSalaryManagement extends JPanel {
    private JTable salaryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;

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

        // Create the search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        titlePanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Create table model with column names
        String[] columnNames = {"STT", "Tên gia sư", "Môn dạy", "Số lượng lớp", "Lương / lớp", "Tổng lương", "Hành Động"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
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
        salaryTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Hành Động

        // Add some sample data
        addSampleData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(salaryTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for the "Duyệt" buttons
        salaryTable.getColumn("Hành Động").setCellRenderer(new ButtonRenderer());
        salaryTable.getColumn("Hành Động").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Set layout and add components
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addSampleData() {
        Object[][] data = {
                {"1", "Nguyen Van A", "Toán", "3", "200000", "600000", "Duyệt"},
                {"2", "Tran Thi B", "Lý", "2", "250000", "500000", "Duyệt"},
                {"3", "Le Van C", "Hóa", "4", "150000", "600000", "Duyệt"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    // Renderer class for the buttons in the "Hành Động" column
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton approveButton = new JButton("Duyệt");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(approveButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor class for the buttons in the "Hành Động" column
    class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel();
        private final JButton approveButton = new JButton("Duyệt");

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.add(approveButton);

            approveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the "Duyệt" button click event here
                    int selectedRow = salaryTable.getSelectedRow();
                    if (selectedRow != -1) {
                        JOptionPane.showMessageDialog(null, "Đã thanh toán lương cho gia sư!");
                        approveButton.setText("Đã thanh toán");
                        approveButton.setEnabled(false);
                    }
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}