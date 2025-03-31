package org.example;

import org.example.DAO.ScheduleDAO;
import org.example.DAO.StudentDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;
import org.example.service.AuthService;
import org.example.service.ScheduleService;
import org.example.service.StudentService;
import org.example.swingUI.login.LoginForm;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection db = new DatabaseConnection();

            UserDAO userDAO = new UserDAO();
            AuthService authService = new AuthService(userDAO);

            StudentDAO studentDAO = new StudentDAO(DatabaseConnection.getConnection());
            StudentService studentService = new StudentService(studentDAO);


            ScheduleDAO scheduleDAO = new ScheduleDAO(DatabaseConnection.getConnection());
            ScheduleService scheduleService = new ScheduleService(scheduleDAO);
            LoginForm form = new LoginForm(authService,studentService);
            form.setVisible(true);
        });
    }
}
