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

// Lớp quản lý gia sư cho tài khoản admin
public class AdminTutorManagement extends JPanel {
    // Các thành phần giao diện
    private JTable tutorTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private AdminService adminService;

    // Map lưu trữ tên gia sư và các môn dạy
    HashMap<String, String> map = new HashMap<>();

    // Constructor
    public AdminTutorManagement() {
        initServices();
        setLayout(new BorderLayout());
        initComponents();
        loadTutorData();
    }

    // Khởi tạo các service cần thiết
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

    // Khởi tạo các thành phần giao diện
    private void initComponents() {
        // Tạo bảng dữ liệu
        String[] columnNames = {"STT", "Tên tutor", "User Name", "Môn Dạy", "Hành Động"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tutorTable = new JTable(tableModel);
        tutorTable.setRowHeight(40);
        tutorTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        tutorTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        tutorTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        tutorTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        tutorTable.getColumnModel().getColumn(4).setPreferredWidth(300);

        // Nút làm mới dữ liệu
        JButton btnRefresh = new JButton("Refresh schedule");
        btnRefresh.setBackground(new Color(255, 102, 102));
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTutorData();
            }
        });

        // Tạo thanh cuộn cho bảng
        JScrollPane scrollPane = new JScrollPane(tutorTable);

        // Tạo panel phía trên chứa tiêu đề và tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel titleSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Quản lí tài khoản gia sư");
        titleSearchPanel.add(titleLabel);

        // Thanh tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        titleSearchPanel.add(searchLabel);
        titleSearchPanel.add(searchField);
        titleSearchPanel.add(searchButton);

        // Panel chứa nút thêm mới
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm mới tài khoản");
        addButtonPanel.add(addButton);

        // Thêm các thành phần vào panel chính
        topPanel.add(titleSearchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);

        // Thêm sự kiện cho các nút
        addButton.addActionListener(e -> new AddNewTutorAccount(adminService).setVisible(true));
        searchButton.addActionListener(e -> searchTutor(searchField.getText()));

        // Cấu hình cột hành động với các nút
        tutorTable.getColumn("Hành Động").setCellRenderer(new ButtonRenderer());
        tutorTable.getColumn("Hành Động").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // Tải dữ liệu gia sư từ database
    private void loadTutorData() {
        tableModel.setRowCount(0);
        map.clear();

        // Lấy tất cả lịch trình
        List<ScheduleRegister> list = adminService.getAllSchedules();

        // Tạo map lưu trữ tên gia sư và các môn dạy
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

        // Thêm dữ liệu vào bảng
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

    // Tìm kiếm gia sư theo tên
    private void searchTutor(String searchText) {
        tableModel.setRowCount(0);
        map.clear();

        String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());
        List<ScheduleRegister> list = adminService.getAllSchedules();

        // Lọc gia sư theo từ khóa tìm kiếm
        for (ScheduleRegister row : list) {
            String name = row.getTutorName();
            String sub = row.getSubject();
            if (name.toLowerCase().contains(searchText.toLowerCase())) {
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
        }

        // Hiển thị kết quả tìm kiếm
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

    // Lớp hiển thị các nút trong cột hành động
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

    // Lớp xử lý sự kiện cho các nút trong cột hành động
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

            // Xử lý sự kiện khi nút sửa được nhấn
            editButton.addActionListener(e -> {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    Tutor tutor = adminService.getAllTutors().stream()
                            .filter(t -> t.getName().equals(name)).findFirst().orElse(null);
                    if (tutor != null) {
                        new TutorEditForm(tutor, adminService).setVisible(true);
                    }
                }
                fireEditingStopped();
            });

            // Xử lý sự kiện khi nút xóa được nhấn
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

            // Xử lý sự kiện khi nút xem chi tiết được nhấn
            detailButton.addActionListener(e -> {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    String adminUsername = adminService.getUsernameByUserId(SessionManager.getInstance().getUserId());
                    Tutor tutor = adminService.getAllTutors().stream()
                            .filter(t -> t.getName().equals(name)).findFirst().orElse(null);
                    if (tutor != null) {
                        new TutorDetailForm(tutor, adminService).setVisible(true); // Truyền AdminService
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