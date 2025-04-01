package org.example.service;

import org.example.DAO.StudentDAO;
import org.example.DAO.UserDAO;
import org.example.model.Schedule;
import org.example.model.ScheduleRegister;
import org.example.model.Student;

import java.util.List;


public class StudentService {

    private StudentDAO studentDao;
    private UserDAO userDAO;
    private StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDao = studentDAO;
    }

    public StudentService(StudentDAO studentDAO, UserDAO userDAO) {
        this.studentDao = studentDAO;
        this.userDAO = userDAO;
    }


    // lay lich da dang ky cua hoc sinh
    public List<ScheduleRegister> getScheduleStudent(int student_id){
        return studentDao.getScheduleStudent(student_id);
    }

    // dang ky hoc cho sinh vien
    public boolean registerScheduleStudent(int student_id,int scheduleId, int tutorId) {
        if(studentDao.checkScheduleValid(student_id, scheduleId)){
            return studentDao.registerScheduleStudent(student_id, scheduleId, tutorId);
        }
        else return false;
    }

    // cap nhat profile hoc sinh
    public boolean updateProfileStudent
    (int studentId, String name, int age, String grade, String phoneNumber, String email){
        return studentDao.updateProfileStudent(studentId, name, age, grade, phoneNumber, email);
    }

    //lấy lịch trống để hoc sinh dang ky
    public List<ScheduleRegister> getEmptySchedule() {
        return studentDao.getEmptySchedule();
    }

    public Student getProfileStudent(int userId){
        return studentDao.getProfileStudent(userId);
    }

    //hoc sinh dky
    public boolean registerStudent(String username, String password, String name, int age,
                                   String grade, String phoneNumber, String email) {
        StudentRegistrationService regService = new StudentRegistrationService(userDAO, studentDAO);
        boolean result = regService.registerStudent(username, password, name, age, grade, phoneNumber, email);
        regService.closeConnection();
        return result;
    }

}
