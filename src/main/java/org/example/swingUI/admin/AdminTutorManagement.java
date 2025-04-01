package org.example.swingUI.admin;

import org.example.DAO.ScheduleDAO;
import org.example.DAO.StudentDAO;
import org.example.DAO.TutorDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.manager.SessionManager;
import org.example.model.ScheduleRegister;
import org.example.model.Tutor;
import org.example.service.AdminService;
import org.example.service.AuthService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class AdminTutorManagement extends JPanel {
    private JTable tutorTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private AdminService adminService;

    HashMap<String, String> map = new HashMap<>();

    public AdminTutorManagement() {
        initServices();
        setLayout(new BorderLayout());
        initComponents();
        loadTutorData();
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
        String[] columnNames = {"STT", "Tên tutor", "User Name", "Môn Dạy", "Hành Động"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tutorTable = new JTable(tableModel);
        tutorTable.setRowHeight(40);
        tutorTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        tutorTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        tutorTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        tutorTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        tutorTable.getColumnModel().getColumn(4).setPreferredWidth(300);

        JButton btnRefresh = new JButton("Refresh schedule");
        btnRefresh.setBackground(new Color(255, 102, 102));
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTutorData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tutorTable);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel titleSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Quản lí tài khoản gia sư");
        titleSearchPanel.add(titleLabel);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        titleSearchPanel.add(searchLabel);
        titleSearchPanel.add(searchField);
        titleSearchPanel.add(searchButton);

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm mới tài khoản");
        addButtonPanel.add(addButton);

        topPanel.add(titleSearchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        addButton.addActionListener(e -> new AddNewTutorAccount(adminService).setVisible(true));
        searchButton.addActionListener(e -> searchTutor(searchField.getText()));

        tutorTable.getColumn("Hành Động").setCellRenderer(new ButtonRenderer());
        tutorTable.getColumn("Hành Động").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadTutorData() {
        tableModel.setRowCount(0);
        map.clear();

        List<ScheduleRegister> list = adminService.getAllSchedules();

        for (ScheduleRegister row : list) {
            String name = row.getTutorName();
            String sub = row.getSubject();
            if (!map.containsKey(name)) {
                map.put(name, sub);
            } else {
                String tmp = map.get(name);
                if (!tmp.contains(sub)) {
                    tmp = tmp + ", " + sub;
                    map.put(name, tmp);
                }
            }
        }

        int[] i = {1};
        map.forEach((name, subjects) -> {
            Tutor tutor = adminService.getAllTutors().stream()
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .orElse(null);
            String userName = tutor != null ? adminService.getUsernameByUserId(tutor.getUserId()) : "N/A";

            tableModel.addRow(new Object[]{
                    i[0]++,
                    name,
                    userName,
                    subjects,
                    ""
            });
        });
    }

    private void searchTutor(String searchText) {
        map.clear();
        tableModel.setRowCount(0);
        if(searchText.isEmpty()){
            loadTutorData();
            return;
        }
        int i = 1;
        String name;
        String sub;

        List<ScheduleRegister> list = adminService.getTutorSchedulesByName(searchText);
        for (ScheduleRegister row : list){
            name = row.getTutorName();
            sub = row.getSubject();
            if(!map.containsKey(name)){
                map.put(name, sub);
            }
            else {
                String tmp = map.get(name);
                tmp = tmp + ", " + sub;
                map.put(name, tmp);
            }
        }
        map.forEach((k, v) -> {
            tableModel.addRow(new Object[]{
                    i, k, v.substring(0, v.length()-2)
            });
        });
    }

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

            editButton.addActionListener(e -> {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());
                    Tutor tutor = adminService.getAllTutors().stream()
                            .filter(t -> t.getName().equals(name)).findFirst().orElse(null);
                    if (tutor != null) {
                        new TutorEditForm(tutor, adminService).setVisible(true);
                    }
                }
                fireEditingStopped();
            });

            deleteButton.addActionListener(e -> {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    Tutor tutor = adminService.getAllTutors().stream()
                            .filter(t -> t.getName().equals(name)).findFirst().orElse(null);
                    if (tutor != null && adminService.deleteTutor(tutor.getTutorId())) {
                        JOptionPane.showMessageDialog(null, "Xóa gia sư thành công!");
                        loadTutorData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fireEditingStopped();
            });

            detailButton.addActionListener(e -> {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    Tutor tutor = adminService.getAllTutors().stream()
                            .filter(t -> t.getName().equals(name)).findFirst().orElse(null);
                    if (tutor != null) {
                        new TutorDetailForm(tutor).setVisible(true);
                    }
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