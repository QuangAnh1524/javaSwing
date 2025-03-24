package org.example.DAO;

import org.example.database.DatabaseConnection;
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
    private List<Schedule> getScheduleStudent(int student_id){
        List<Schedule> schedules = new ArrayList<>();
        String query = "select * from schedule where student_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setTutorId(rs.getInt("(tutor_id"));
                schedule.setStudentId(rs.getInt("student_id"));
                schedule.setStatus(rs.getString("status"));
                schedule.setLessonDate(rs.getTimestamp("lesson_date"));
                schedule.setEndDate(rs.getTimestamp("end_date"));
                schedules.add(schedule);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    // dang ky hoc cho sinh vien
    private boolean registerScheduleStudent(int student_id,int scheduleId, int tutorId) {
        String query = "update schedule set student_id = ?, status = 'booked' where schedule_id = ? and tutor_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student_id);
            statement.setInt(2, scheduleId);
            statement.setInt(3, tutorId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateProfileStudent(int studentId){
    }
    // check lich dang ky moi co trung khung gio hay khong
    public boolean checkScheduleValid(int student_id, int schedule_id){
        List<Schedule> listSchedule = getScheduleStudent(student_id);
        Schedule currentSchedule = getScheduleById(schedule_id);

        for(int i = 0;i < listSchedule.size(); i++){
            if(!(currentSchedule.getLessonDate().after(listSchedule.get(i).getEndDate())
                && currentSchedule.getEndDate().before(listSchedule.get(i).getLessonDate())
            )){
                return false;
            }
        }
        return true;
    }

    // lay lich theo id
    public Schedule getScheduleById(int schedule_id){
        Schedule schedule = new Schedule();
        String query = "select * from schedule where schedule_id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, schedule_id);
            ResultSet rs = state.executeQuery();
            schedule.setScheduleId(rs.getInt("schedule_id"));
            schedule.setTutorId(rs.getInt("(tutor_id"));
            schedule.setStudentId(rs.getInt("student_id"));
            schedule.setStatus(rs.getString("status"));
            schedule.setLessonDate(rs.getTimestamp("lesson_date"));
            schedule.setEndDate(rs.getTimestamp("end_date"));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return schedule;
    }

    //lấy lịch trống để hoc sinh dang ky
/*    public List<ScheduleRegister> getEmptySchedule() {
        List<ScheduleRegister> scheduleList = new ArrayList<>();
        String query = "select * from schedule join tutors on schedule.tutor_id = tutors.tutor_id where status = 'empty' order by tutor_id";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ScheduleRegister schedule = new ScheduleRegister();
                schedule.setScheduleId(resultSet.getInt("schedule_id"));
                schedule.setTutorId(resultSet.getInt("(tutor_id"));
                schedule.setTutorName(resultSet.getString("name"));
                schedule.setLessonDate(resultSet.getTime("lesson_date"));
                schedule.setEndDate(resultSet.getTimestamp("end_date"));
                schedule.setGrade(resultSet.getString("subject"));
                schedule.setPhoneNumber(resultSet.getString("phone_number"));
                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList;
    }*/
}
