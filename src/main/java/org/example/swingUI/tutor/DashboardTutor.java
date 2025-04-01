package org.example.swingUI.tutor;

import org.example.swingUI.listener.MenuListener;
import org.example.swingUI.student.*;

import javax.swing.*;
import java.awt.*;

public class DashboardTutor extends JFrame implements MenuListener{
    private ProfileTutor ProfileTutor;
    private ScheduleTutor schedule;
    private RegisterTutor register;
    private SalaryTutor salaryTutor;

    public DashboardTutor(){
        setSize(800, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        MenuTutor MenuTutor = new MenuTutor(this);
        add(MenuTutor, BorderLayout.WEST);

        // Panel đăng ký dạy
        register = new RegisterTutor();

        // Panel lịch day
        schedule = new ScheduleTutor();

        // Panel profile
        ProfileTutor = new ProfileTutor();

        //panel salary
        salaryTutor = new SalaryTutor();

        // Mặc định hiển thị giao diện lịch dạy
        add(register, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DashboardTutor().setVisible(true);
    }
    @Override
    public void onMenuSelected(String menuText) {
        switch (menuText) {
            case "Đăng ký dạy":
                schedule.setVisible(false);
                register.setVisible(true);
                ProfileTutor.setVisible(false);
                salaryTutor.setVisible(false);
                add(register, BorderLayout.CENTER);
                break;
            case "Lịch dạy":
                schedule.setVisible(true);
                register.setVisible(false);
                ProfileTutor.setVisible(false);
                salaryTutor.setVisible(false);
                add(schedule, BorderLayout.CENTER);
                break;
            case "Thông tin cá nhân":
                schedule.setVisible(false);
                register.setVisible(false);
                ProfileTutor.setVisible(true);
                salaryTutor.setVisible(false);
                add(ProfileTutor, BorderLayout.CENTER);
                break;
            case "Thống kê lương":
                schedule.setVisible(false);
                register.setVisible(false);
                ProfileTutor.setVisible(false);
                salaryTutor.setVisible(true);
                add(salaryTutor, BorderLayout.CENTER);
                break;
            case "Đăng xuất":
                System.out.println("Thoát ứng dụng.");
                break;
        }
        revalidate();
        repaint();
    }
}

