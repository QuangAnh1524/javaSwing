package org.example.service;

import org.example.DAO.StudentDAO;
import org.example.DAO.UserDAO;
import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentRegistrationService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final Connection connection;

    public StudentRegistrationService(UserDAO userDAO, StudentDAO studentDAO) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean registerStudent(String username, String password, String name, int age,
                                   String grade, String phoneNumber, String email) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                grade == null || grade.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Tất cả các trường thông tin đều bắt buộc!");
        }

        if (age <= 0) {
            throw new IllegalArgumentException("Tuổi phải là số dương!");
        }

        if (userDAO.getUserIdByUsername(username) != -1) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại!");
        }

        try {
            // Tạo tài khoản trong bảng users
            boolean userCreated = userDAO.createUser(username, password, "student");
            if (!userCreated) {
                throw new SQLException("Không thể tạo tài khoản người dùng!");
            }

            // Lấy userId vừa tạo
            int userId = userDAO.getUserIdByUsername(username);
            if (userId == -1) {
                throw new SQLException("Không thể lấy ID người dùng vừa tạo!");
            }

            // Tạo thông tin học sinh trong bảng students
            boolean studentCreated = studentDAO.createStudent(userId, name, age, grade, phoneNumber, email);
            if (!studentCreated) {
                throw new SQLException("Không thể tạo thông tin học sinh!");
            }

            return true;

        } catch (SQLException e) {
            try {
                // Xóa user nếu đã tạo mà student thất bại
                int userId = userDAO.getUserIdByUsername(username);
                if (userId != -1) {
                    userDAO.deleteUser(userId);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
            return false;
    }


    // Đóng kết nối
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đóng kết nối: " + e.getMessage(), e);
        }
    }

//    // test dky
//    public static void main(String[] args) {
//        try {
//            Connection connection = DatabaseConnection.getConnection();
//            UserDAO userDAO = new UserDAO(connection);
//            StudentDAO studentDAO = new StudentDAO(connection);
//            StudentRegistrationService registrationService = new StudentRegistrationService(userDAO, studentDAO);
//
//            // Thử đăng ký một học sinh
//            boolean success = registrationService.registerStudent(
//                    "student123",
//                    "password123",
//                    "Nguyen Van A",
//                    15,
//                    "10A",
//                    "0123456789",
//                    "student123@example.com"
//            );
//
//            if (success) {
//                System.out.println("Đăng ký tài khoản học sinh thành công!");
//            } else {
//                System.out.println("Đăng ký thất bại!");
//            }
//
//            registrationService.closeConnection();
//        } catch (Exception e) {
//            System.err.println("Lỗi: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}