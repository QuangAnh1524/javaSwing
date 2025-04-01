package org.example.swingUI.admin;

import org.example.DAO.ScheduleDAO;
import org.example.DAO.StudentDAO;
import org.example.DAO.TutorDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.model.Tutor;
import org.example.service.AdminService;
import org.example.service.AuthService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AdminSalaryManagement extends JPanel {
    private JTable salaryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private AdminService adminService;

    public AdminSalaryManagement() {
        initServices();
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initServices() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            StudentDAO studentDAO = new StudentDAO(connection);
            TutorDAO tutorDAO = new TutorDAO(connection);
            ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
            AuthService authService = new AuthService(userDAO);
            this.adminService = new AdminService(userDAO, studentDAO, tutorDAO, scheduleDAO, authService);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Bảng lương tháng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        titlePanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"STT", "Tên gia sư", "Môn dạy", "Số lượng lớp", "Lương / lớp", "Tổng lương", "Hành Động"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salaryTable = new JTable(tableModel);
        salaryTable.setRowHeight(40);
        salaryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        salaryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        salaryTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        salaryTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        salaryTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        salaryTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        salaryTable.getColumnModel().getColumn(6).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(salaryTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        salaryTable.getColumn("Hành Động").setCellRenderer(new ButtonRenderer());
        salaryTable.getColumn("Hành Động").setCellEditor(new ButtonEditor(new JCheckBox()));

        add(mainPanel, BorderLayout.CENTER);

    }


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

    class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel();
        private final JButton approveButton = new JButton("Duyệt");

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.add(approveButton);

            approveButton.addActionListener(e -> {
                int selectedRow = salaryTable.getSelectedRow();
                if (selectedRow != -1) {
                    JOptionPane.showMessageDialog(null, "Đã thanh toán lương cho gia sư!");
                    approveButton.setText("Đã thanh toán");
                    approveButton.setEnabled(false);
                }
                fireEditingStopped();
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