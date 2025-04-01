package org.example.service;

import org.example.DAO.ScheduleDAO;
import org.example.DAO.StudentDAO;
import org.example.DAO.TutorDAO;
import org.example.DAO.UserDAO;
import org.example.model.ScheduleRegister;
import org.example.model.Student;
import org.example.model.Tutor;

import java.util.List;

public class AdminService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final TutorDAO tutorDAO;
    private final ScheduleDAO scheduleDAO;
    private final AuthService authService;

    public AdminService(UserDAO userDAO, StudentDAO studentDAO, TutorDAO tutorDAO,
                        ScheduleDAO scheduleDAO, AuthService authService) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.tutorDAO = tutorDAO;
        this.scheduleDAO = scheduleDAO;
        this.authService = authService;
    }

    // Kiểm tra quyền Admin
    private void checkAdminPermission(String username) {
        if (!authService.isAdmin(username)) {
            throw new SecurityException("Only admin can perform this action");
        }
    }

    // Quản lý học sinh
    public List<Student> getAllStudents(String adminUsername) {
        checkAdminPermission(adminUsername);
        return studentDAO.getAllStudents();
    }

    public boolean deleteStudent(String adminUsername, int studentId) {
        checkAdminPermission(adminUsername);
        Student student = studentDAO.getProfileStudent(studentId);
        if (student != null) {
            boolean deletedFromStudents = studentDAO.deleteStudent(studentId);
            if (deletedFromStudents) {
                return userDAO.deleteUser(student.getUserId());
            }
        }
        return false;
    }

    // Quản lý gia sư
    public boolean createTutorAccount(String adminUsername, String username, String password,
                                      String name, String phoneNumber, int salary) {
        checkAdminPermission(adminUsername);
        boolean userCreated = userDAO.createUser(username, password, "tutor");
        if (userCreated) {
            int userId = userDAO.getUserIdByUsername(username);
            return tutorDAO.createTutor(name, phoneNumber, salary, userId);
        }
        return false;
    }

    public List<Tutor> getAllTutors(String adminUsername) {
        checkAdminPermission(adminUsername);
        return tutorDAO.getAllTutors();
    }

    public boolean deleteTutor(String adminUsername, int tutorId) {
        checkAdminPermission(adminUsername);
        Tutor tutor = tutorDAO.getTutorById(tutorId);
        if (tutor != null) {
            boolean deletedFromTutors = tutorDAO.deleteTutor(tutorId);
            if (deletedFromTutors) {
                return userDAO.deleteUser(tutor.getUserId());
            }
        }
        return false;
    }

    // Quản lý lịch học
    public List<ScheduleRegister> getAllSchedules(String adminUsername) {
        checkAdminPermission(adminUsername);
        return scheduleDAO.getAllSchedules();
    }

    public boolean deleteSchedule(String adminUsername, int scheduleId) {
        checkAdminPermission(adminUsername);
        return scheduleDAO.deleteSchedule(scheduleId);
    }
}