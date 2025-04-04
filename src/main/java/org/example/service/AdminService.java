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


    // Quản lý học sinh
    public List<Student> getAllStudents(String adminUsername) {
        return studentDAO.getAllStudents();
    }

    public boolean deleteStudent(String adminUsername, int studentId) {
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
        boolean userCreated = userDAO.createUser(username, password, "tutor");
        if (userCreated) {
            int userId = userDAO.getUserIdByUsername(username);
            return tutorDAO.createTutor(name, phoneNumber, salary, userId);
        }
        return false;
    }

    public List<Tutor> getAllTutors() {
        return tutorDAO.getAllTutors();
    }




    public boolean deleteTutor(int tutorId) {
        Tutor tutor = tutorDAO.getTutorById(tutorId);
        if (tutor != null) {
            boolean deletedFromTutors = tutorDAO.deleteTutor(tutorId);
            if (deletedFromTutors) {
                return userDAO.deleteUser(tutor.getUserId());
            }
        }
        return false;
    }

    // Lấy danh sách lịch học của một gia sư cụ thể
    public List<ScheduleRegister> getSchedulesByTutorId(int tutorId) {
        return scheduleDAO.getSchedulesByTutorId(tutorId);
    }

    // Quản lý lịch học
    public List<ScheduleRegister> getAllSchedules() {
        return scheduleDAO.getAllSchedules();
    }

    public boolean deleteSchedule(String adminUsername, int scheduleId) {
        return scheduleDAO.deleteSchedule(scheduleId);
    }

    // Thêm phương thức mới để lấy username từ userId
    public String getUsernameByUserId(int userId) {
        return userDAO.getUsernameByUserId(userId);
    }

    // Thêm phương thức để cập nhật gia sư
    public boolean updateTutor(int tutorId, String name, String phoneNumber, int salary) {
        return tutorDAO.updateTutor(tutorId, name, phoneNumber, salary);
    }

    // tim kiem gia su theo ten
    public List<ScheduleRegister> getTutorSchedulesByName(String tutorName){
        return scheduleDAO.getTutorSchedulesByName(tutorName);
    }

}