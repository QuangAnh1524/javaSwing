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
import org.example.swingUI.login.LoginForm;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        StudentDAO studentDAO = new StudentDAO(conn);

        AuthService authService = new AuthService(userDAO);
        StudentService studentService = new StudentService(studentDAO);
        LoginForm loginForm = new LoginForm(authService, studentService);
        loginForm.setVisible(true);
    }
}