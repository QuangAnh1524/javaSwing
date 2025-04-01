package org.example.swingUI.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminTutorManagement extends JPanel {
    private JTable tutorTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;

    public AdminTutorManagement() {
        setLayout(new BorderLayout());

        // Create table model with column names
        String[] columnNames = {"STT", "Tên tutor", "User Name", "Môn Dạy", "Hành Động"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        tutorTable = new JTable(tableModel);

        // Set row height
        tutorTable.setRowHeight(40);

        // Set column widths
        tutorTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        tutorTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên tutor
        tutorTable.getColumnModel().getColumn(2).setPreferredWidth(200); // User Name
        tutorTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Môn Dạy
        tutorTable.getColumnModel().getColumn(4).setPreferredWidth(300); // Hành Động

        // Add some sample data
        addSampleData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(tutorTable);

        // Create a panel for the title, search form and "Thêm mới tài khoản" button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Create a sub-panel for the title and search form
        JPanel titleSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Quản lí tài khoản gia sư");
        titleSearchPanel.add(titleLabel);

        // Create the search form
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        titleSearchPanel.add(searchLabel);
        titleSearchPanel.add(searchField);
        titleSearchPanel.add(searchButton);

        // Create a sub-panel for the "Thêm mới tài khoản" button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm mới tài khoản");
        addButtonPanel.add(addButton);

        // Add the titleSearchPanel and buttonPanel to the top panel
        topPanel.add(titleSearchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);

        // Set layout and add components
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener for the "Thêm mới tài khoản" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the "Thêm mới tài khoản" button click event here
                new AddNewTutorAccount().setVisible(true);
            }
        });

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the search button click event here
                String searchText = searchField.getText();
                searchTutor(searchText);
            }
        });

        // Add action listeners for the "Sửa", "Xóa", and "Xem chi tiết" buttons
        tutorTable.getColumn("Hành Động").setCellRenderer(new ButtonRenderer());
        tutorTable.getColumn("Hành Động").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void addSampleData() {
        Object[][] data = {
                {"1", "Nguyen Van A", "nguyenvana@gmail.com", "Toán", ""},
                {"2", "Tran Thi B", "tranthib@gmail.com", "Lý", ""},
                {"3", "Le Van C", "levanc@gmail.com", "Hóa", ""}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void searchTutor(String searchText) {
        // Implement search logic here
        JOptionPane.showMessageDialog(null, "Searching for: " + searchText);
    }

    // Renderer class for the buttons in the "Hành Động" column
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Sửa");
        private final JButton deleteButton = new JButton("Xóa");
        private final JButton detailButton = new JButton("Xem chi tiết");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(editButton);
            add(deleteButton);
            add(detailButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor class for the buttons in the "Hành Động" column
    class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel();
        private final JButton editButton = new JButton("Sửa");
        private final JButton deleteButton = new JButton("Xóa");
        private final JButton detailButton = new JButton("Xem chi tiết");

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.add(editButton);
            panel.add(deleteButton);
            panel.add(detailButton);

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the "Sửa" button click event here
                    int selectedRow = tutorTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String name = (String) tableModel.getValueAt(selectedRow, 1);
                        String userName = (String) tableModel.getValueAt(selectedRow, 2);
                        String phone = "0327593620"; // Giá trị điền cứng (cần lấy trong db)
                        String subject = (String) tableModel.getValueAt(selectedRow, 3);
                        String salary = "1000000"; // Giá trị điền cứng

                        new TutorEditForm(name, userName, phone, subject, salary).setVisible(true);
                    }
                    fireEditingStopped();
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the "Xóa" button click event here
                    int selectedRow = tutorTable.getSelectedRow();
                    if (selectedRow != -1) {
                        JOptionPane.showMessageDialog(null, "Xóa button clicked!");
                    }
                    fireEditingStopped();
                }
            });

            detailButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tutorTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String name = (String) tableModel.getValueAt(selectedRow, 1);
                        String userName = (String) tableModel.getValueAt(selectedRow, 2);
                        String phone = "0327593620"; // Giá trị điền cứng (cần lấy trong db)
                        String subject = (String) tableModel.getValueAt(selectedRow, 3);
                        String salary = "1000000"; // Giá trị điền cứng

                        new TutorDetailForm(name, userName, phone, subject, salary).setVisible(true);
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