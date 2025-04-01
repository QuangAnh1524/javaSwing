package org.example.swingUI.admin;
import org.example.swingUI.listener.MenuListener;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame implements MenuListener {

    private AdminTutorManagement adminTutorManagement;
    private AdminSalaryManagement adminSalaryManagement;
    private AdminStudentManagement adminStudentManagement;

    public AdminDashboard() {
        setSize(800, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        MenuAdmin MenuAdmin = new MenuAdmin(this);
        add(MenuAdmin, BorderLayout.WEST);

        // Create instances of the management panels
        adminTutorManagement = new AdminTutorManagement();
        adminSalaryManagement = new AdminSalaryManagement();
        adminStudentManagement = new AdminStudentManagement();


        // Add the mainPanel to the center
        add(adminTutorManagement, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        new AdminDashboard().setVisible(true);
    }

    @Override
    public void onMenuSelected(String menuText) {
//        showPanel(menuText);
        switch (menuText) {
            case "Quản lý gia sư":
                adminSalaryManagement.setVisible(false);
                adminTutorManagement.setVisible(true);
                adminStudentManagement.setVisible(false);
                add(adminTutorManagement, BorderLayout.CENTER);
                break;
            case "Quản lý sinh viên":
                adminSalaryManagement.setVisible(false);
                adminTutorManagement.setVisible(false);
                adminStudentManagement.setVisible(true);
                add(adminStudentManagement, BorderLayout.CENTER);
                break;
            case "Thống kê lương":
                adminSalaryManagement.setVisible(true);
                adminTutorManagement.setVisible(false);
                adminStudentManagement.setVisible(false);
                add(adminSalaryManagement, BorderLayout.CENTER);
                break;
        }
        revalidate();
        repaint();
    }
}