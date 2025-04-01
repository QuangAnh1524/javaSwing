package org.example.swingUI.tutor;

import org.example.manager.SessionManager;
import org.example.service.AuthService;
import org.example.service.StudentService;
import org.example.swingUI.listener.MenuListener;
import org.example.swingUI.login.LoginForm;

import javax.swing.*;
import java.awt.*;

public class DashboardTutor extends JFrame implements MenuListener {
    private ProfileTutor profileTutor;
    private ScheduleTutor schedule;
    private RegisterTutor register;
    private SalaryTutor salaryTutor;
    private JPanel currentPanel;
    private AuthService authService;
    private StudentService studentService;

    public DashboardTutor(AuthService authService, StudentService studentService) {
        this.authService = authService;
        this.studentService = studentService;

        setSize(800, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MenuTutor menuTutor = new MenuTutor(this);
        add(menuTutor, BorderLayout.WEST);

        register = new RegisterTutor();
        schedule = new ScheduleTutor();
        profileTutor = new ProfileTutor();
        salaryTutor = new SalaryTutor();

        currentPanel = register;
        add(currentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    @Override
    public void onMenuSelected(String menuText) {
        if (currentPanel != null) {
            remove(currentPanel);
        }

        int userId = SessionManager.getInstance().getUserId();

        switch (menuText) {
            case "Đăng ký dạy":
                currentPanel = register;
                break;
            case "Lịch dạy":
                currentPanel = schedule;
                schedule.loadScheduleData(userId);
                break;
            case "Thông tin cá nhân":
                currentPanel = profileTutor;
                profileTutor.loadProfileData(userId);
                break;
            case "Thống kê lương":
                currentPanel = salaryTutor;
                break;
            case "Đăng xuất":
                System.out.println("Thoát ứng dụng.");
                SessionManager.getInstance().setUserId(-1);
                dispose();
                new LoginForm(authService, studentService).setVisible(true);
                return;
        }
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}