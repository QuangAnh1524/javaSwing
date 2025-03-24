package org.example.swingUI.student;

import org.example.swingUI.listener.MenuListener;

import javax.swing.*;
import java.awt.*;

public class DashboardStudent extends JFrame implements MenuListener {
    private ScheduleStudent schedule;
    private RegisterStudent register;
    private ProfileStudent ProfileStudent;
    public DashboardStudent(){
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

        ProfileStudent = new ProfileStudent();

        // Mặc định hiển thị giao diện đăng ký học
        add(register, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new DashboardStudent().setVisible(true);
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
                System.out.println("Thoát ứng dụng.");
                break;
        }
        revalidate();
        repaint();
    }
}