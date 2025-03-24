package org.example.DAO;

import org.example.database.DatabaseConnection;
import org.example.model.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {


    //Lấy danh sách lịch gia sư ddax đặt
    public List<Schedule> getScheduleByTutor(int tutorId) {
        List<Schedule> scheduleList = new ArrayList<>();
        String query = "select * from schedule where tutor_id = ? and status = 'booked'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleId(resultSet.getInt("schedule_id"));
                    schedule.setTutorId(resultSet.getInt("(tutor_id"));
                    schedule.setStudentId(resultSet.getInt("student_id"));
                    schedule.setStatus(resultSet.getString("status"));
                    schedule.setLessonDate(resultSet.getTimestamp("lesson_date"));
                    schedule.setEndDate(resultSet.getTimestamp("end_date"));
                    scheduleList.add(schedule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList;
    }

    //dang ky lich day
    public boolean bookSchedule(int scheduleId, int tutorId) {
        String query = "insert into schedule............ ";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            statement.setInt(2, scheduleId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //hủy lịch
    public boolean cancelSchedule(int scheduleId, int tutorId) {
        String query = "update schedule set tutor_id = NULL and status = 'empty' where schedule_id = ? and tutor_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, tutorId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
