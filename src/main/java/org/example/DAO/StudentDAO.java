package org.example.DAO;

import org.example.model.Schedule;
import org.example.model.ScheduleRegister;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    // lay lich da dang ky cua hoc sinh
    public List<ScheduleRegister> getScheduleStudent(int student_id){
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT " +
                "s.schedule_id," +
                "s.time_start," +
                "s.time_end," +
                "s.day_start," +
                "s.day_end," +
                "s.subject," +
                "t.tutor_id," +
                "t.name," +
                "t.phone_number," +
                "t.salary " +
                "FROM schedule s " +
                "JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "where student_id = (SELECT student_id FROM students WHERE user_id = ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student_id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ScheduleRegister schedule = new ScheduleRegister();
                schedule.setScheduleId(resultSet.getInt("schedule_id"));
                schedule.setTutorId(resultSet.getInt("tutor_id"));
                schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                schedule.setTutorName(resultSet.getString("name"));
                schedule.setPhoneNumber(resultSet.getString("phone_number"));
                schedule.setPrice(resultSet.getInt("salary"));
                schedule.setSubject(resultSet.getString("subject"));
                schedules.add(schedule);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    // dang ky hoc cho sinh vien
    public boolean registerScheduleStudent(int user_id,int scheduleId, int tutorId) {
        String query = "update schedule set student_id = (SELECT student_id FROM students WHERE user_id = ?), status = 'booked' where schedule_id = ? and tutor_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, scheduleId);
            statement.setInt(3, tutorId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // lay thong tin profile student
    public Student getProfileStudent(int userId){
        String query = "select * from students where user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Student student = new Student();
            while(resultSet.next()){
                student.setUserId(resultSet.getInt("user_id"));
                student.setStudentId(resultSet.getInt("student_id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setEmail(resultSet.getString("email"));
                student.setGrade(resultSet.getString("grade"));
                student.setPhoneNumber(resultSet.getString("phone_number"));
            }
            return student;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // cap nhat profile hoc sinh
    public boolean updateProfileStudent
    (int studentId, String name, int age, String grade, String phoneNumber, String email){
        String query = "update students set name = ?, age=?, grade =?, phone_number=?, email=? where user_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, grade);
            statement.setString(4, phoneNumber);
            statement.setString(5, email);
            statement.setInt(6, studentId);
            int rows = statement.executeUpdate();
            if(rows > 0){
                return true;
            }
            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


    // check lich dang ky moi co trung khung gio hay khong
    public boolean checkScheduleValid(int student_id, int schedule_id){
        List<ScheduleRegister> listSchedule = getScheduleStudent(student_id);
        if(listSchedule == null){
            return true;
        }
        Schedule currentSchedule = getScheduleById(schedule_id);

        for(int i = 0;i < listSchedule.size(); i++){
            if(!currentSchedule.getDayStart().isEqual(listSchedule.get(i).getDayStart())){
                return true;
            }
            if(!(currentSchedule.getTimeStart().isAfter(listSchedule.get(i).getTimeEnd())
                || currentSchedule.getTimeEnd().isBefore(listSchedule.get(i).getTimeStart())
            )){
                return false;
            }
        }
        return true;
    }

    //lấy lịch trống để hoc sinh dang ky
    public List<ScheduleRegister> getEmptySchedule() {
        List<ScheduleRegister> scheduleList = new ArrayList<>();
        String query = "SELECT * FROM schedule JOIN tutors ON schedule.tutor_id = tutors.tutor_id WHERE status = 'empty' ORDER BY tutors.tutor_id";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ScheduleRegister schedule = new ScheduleRegister();
                schedule.setScheduleId(resultSet.getInt("schedule_id"));
                schedule.setTutorId(resultSet.getInt("tutor_id"));
                schedule.setTutorName(resultSet.getString("name"));
                schedule.setPhoneNumber(resultSet.getString("phone_number"));
                schedule.setSubject(resultSet.getString("subject"));
                schedule.setPrice(resultSet.getInt("salary"));
                schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                scheduleList.add(schedule); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    // lay lich theo id
    private Schedule getScheduleById(int schedule_id){
        Schedule schedule = new Schedule();
        String query = "select * from schedule where schedule_id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, schedule_id);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setTutorId(rs.getInt("tutor_id"));
                schedule.setStudentId(rs.getInt("student_id"));
                schedule.setStatus(rs.getString("status"));
                schedule.setTimeStart(rs.getTime("time_start").toLocalTime());
                schedule.setTimeEnd(rs.getTime("time_end").toLocalTime());
                schedule.setDayStart(rs.getDate("day_start").toLocalDate());
                schedule.setDayEnd(rs.getDate("day_end").toLocalDate());
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return schedule;
    }






}
