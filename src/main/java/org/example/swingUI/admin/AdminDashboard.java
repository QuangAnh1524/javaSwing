package org.example.swingUI.admin;

import org.example.manager.SessionManager;
import org.example.service.AuthService;
import org.example.service.StudentService;
import org.example.swingUI.listener.MenuListener;
import org.example.swingUI.login.LoginForm;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame implements MenuListener {
    private AdminTutorManagement adminTutorManagement;
    private AdminSalaryManagement adminSalaryManagement;
    private AuthService auth;
    private StudentService studentService;

    public AdminDashboard() {
        setSize(800, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MenuAdmin menuAdmin = new MenuAdmin(this);
        add(menuAdmin, BorderLayout.WEST);

        adminTutorManagement = new AdminTutorManagement();
        adminSalaryManagement = new AdminSalaryManagement();

        add(adminTutorManagement, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void onMenuSelected(String menuText) {
        remove(adminTutorManagement);
        remove(adminSalaryManagement);

        switch (menuText) {
            case "Quản lý gia sư":
                add(adminTutorManagement, BorderLayout.CENTER);
                adminTutorManagement.setVisible(true);
                break;
            case "Thống kê lương":
                add(adminSalaryManagement, BorderLayout.CENTER);
                adminSalaryManagement.setVisible(true);
                break;
            case "Đăng xuất":
                dispose();
                new LoginForm(auth, studentService).setVisible(true);
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new AdminDashboard().setVisible(true);

    }
}