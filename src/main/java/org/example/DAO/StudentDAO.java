package org.example.DAO;

import org.example.model.Schedule;
import org.example.model.ScheduleRegister;
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
    public List<Schedule> getScheduleStudent(int student_id){
        List<Schedule> schedules = new ArrayList<>();
        String query = "select * from schedule where student_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student_id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setScheduleId(resultSet.getInt("schedule_id"));
                schedule.setTutorId(resultSet.getInt("(tutor_id"));
                schedule.setStudentId(resultSet.getInt("student_id"));
                schedule.setStatus(resultSet.getString("status"));
                schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                schedules.add(schedule);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    // dang ky hoc cho sinh vien
    public boolean registerScheduleStudent(int student_id,int scheduleId, int tutorId) {
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

    // cap nhat profile hoc sinh
    public boolean updateProfileStudent
    (int studentId, String name, int age, String grade, String phoneNumber, String email){
        String query = "update students set name = ?, age=?, grade =?, phone_number=?, email=? where student_id=?";
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
        List<Schedule> listSchedule = getScheduleStudent(student_id);
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
        String query = "select * from schedule join tutors on schedule.tutor_id = tutors.tutor_id where status = 'empty' order by tutor_id";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ScheduleRegister schedule = new ScheduleRegister();
                schedule.setScheduleId(resultSet.getInt("schedule_id"));
                schedule.setTutorId(resultSet.getInt("(tutor_id"));
                schedule.setTutorName(resultSet.getString("name"));
                schedule.setPhoneNumber(resultSet.getString("phone_number"));
                schedule.setSubject(getSubjectByTutorId(resultSet.getInt("(tutor_id")));
                schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList;
    }

    // lay list subject cua giao vien de set thuoc tinh dang ky
    private String getSubjectByTutorId(int tutorId){
        String rs = "";
        String query = "SELECT s.subject_name\n" +
                "FROM subjects s\n" +
                "JOIN subject_tutor st ON s.subject_id = st.subject_id;" +
                "WHERE tutor_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tutorId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                rs += resultSet.getString("subject_name");
                if (!resultSet.isLast()) {
                    rs += ", ";
                }
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return rs;
    }

    // lay lich theo id
    private Schedule getScheduleById(int schedule_id){
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
            schedule.setTimeStart(rs.getTime("time_start").toLocalTime());
            schedule.setTimeEnd(rs.getTime("time_end").toLocalTime());
            schedule.setDayStart(rs.getDate("day_start").toLocalDate());
            schedule.setDayEnd(rs.getDate("day_end").toLocalDate());
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return schedule;
    }

}
