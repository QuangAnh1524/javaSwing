package org.example;

import org.example.DAO.ScheduleDAO;
import org.example.DAO.StudentDAO;
import org.example.DAO.TutorDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.model.Tutor;
import org.example.service.AdminService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;
import org.example.service.StudentService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            StudentDAO studentDAO = new StudentDAO(conn);
            TutorDAO tutorDAO = new TutorDAO(conn);
            ScheduleDAO scheduleDAO = new ScheduleDAO(conn);

            AuthService authService = new AuthService(userDAO);
            StudentService studentService = new StudentService(studentDAO, new ScheduleService(scheduleDAO));
            AdminService adminService = new AdminService(userDAO, studentDAO, tutorDAO, scheduleDAO, authService);

            // Test Admin
            authService.login("admin", "admin123");
            System.out.println("Is admin: " + authService.isAdmin("admin"));

            List<Tutor> tutors = adminService.getAllTutors("admin");
            System.out.println("Tutors: " + tutors.size());

            adminService.createTutorAccount("admin", "tutor2", "pass789", "Le Van C", "0912345678", 250000);
            System.out.println("New tutor created: " + adminService.getAllTutors("admin").size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}