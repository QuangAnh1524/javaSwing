package org.example.service;

import org.example.DAO.StudentDAO;
import org.example.model.Schedule;
import org.example.model.ScheduleRegister;
import org.example.model.Student;

import java.util.List;


public class StudentService {

    private StudentDAO studentDao;

    public StudentService(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    public StudentService(StudentDAO studentDAO, ScheduleService scheduleService) {
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


}
