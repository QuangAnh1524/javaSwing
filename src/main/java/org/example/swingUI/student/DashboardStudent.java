package org.example.swingUI.student;

import org.example.DAO.UserDAO;
import org.example.manager.SessionManager;
import org.example.service.AuthService;
import org.example.service.StudentService;
import org.example.swingUI.listener.MenuListener;
import org.example.swingUI.login.LoginForm;

import javax.swing.*;
import java.awt.*;

public class DashboardStudent extends JFrame implements MenuListener {
    private ScheduleStudent schedule;
    private RegisterStudent register;
    private ProfileStudent ProfileStudent;


    private LoginForm loginForm;
    private StudentService studentService;

    public DashboardStudent(LoginForm loginForm,StudentService studentService){
        this.studentService = studentService;
        this.loginForm = loginForm;


        setSize(800, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        MenuStudent MenuStudent = new MenuStudent(this);
        add(MenuStudent, BorderLayout.WEST);

        // Panel đăng ký học
        register = new RegisterStudent();

        // Panel lịch học
        schedule = new ScheduleStudent();

        ProfileStudent = new ProfileStudent(studentService);

        // Mặc định hiển thị giao diện đăng ký học
        add(register, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void onMenuSelected(String menuText) {

        switch (menuText) {
            case "Đăng ký học":
                schedule.setVisible(false);
                register.setVisible(true);
                ProfileStudent.setVisible(false);
                add(register, BorderLayout.CENTER);
                break;
            case "Lịch học":
                schedule.setVisible(true);
                register.setVisible(false);
                ProfileStudent.setVisible(false);
                add(schedule, BorderLayout.CENTER);
                break;
            case "Thông tin cá nhân":
                schedule.setVisible(false);
                register.setVisible(false);
                ProfileStudent.setVisible(true);
                add(ProfileStudent, BorderLayout.CENTER);
                break;
            case "Đăng xuất":
                int option = JOptionPane.showConfirmDialog(this, "Do you want to log out?", "Log out", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    loginForm.setVisible(true);
                    SessionManager.getInstance().setUserId(0);
                    dispose();
                }
                break;
        }
        revalidate();
        repaint();
    }

}