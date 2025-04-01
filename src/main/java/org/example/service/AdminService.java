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

    public AdminService(UserDAO userDAO, StudentDAO studentDAO, TutorDAO tutorDAO,
                        ScheduleDAO scheduleDAO, AuthService authService) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.tutorDAO = tutorDAO;
        this.scheduleDAO = scheduleDAO;
    }



    // tạo mới gia sư
    public boolean createTutorAccount(String username, String password,
                                      String name, String phoneNumber, int salary) {
        boolean userCreated = userDAO.createUser(username, password, "tutor");
        if (userCreated) {
            int userId = userDAO.getUserIdByUsername(username);
            return tutorDAO.createTutor(name, phoneNumber, salary, userId);
        }
        return false;
    }


    // lấy tất cả thông tin gia sư
    public List<Tutor> getAllTutors() {
        return tutorDAO.getAllTutors();
    }

    // xóa gia su
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

    // Quản lý lịch học
    public List<ScheduleRegister> getAllSchedules() {
        return scheduleDAO.getAllSchedules();
    }

    // tim kiem gia su theo ten gia su

    //danh sach lich day cua gia su

    //tim kiem theo username hoc sinh

}