package org.example.DAO;

import org.example.model.ScheduleRegister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Tạo một lịch học mới trong database
     */
    public boolean createSchedule(int tutorId, String subject, LocalTime timeStart,
                                  LocalTime timeEnd, LocalDate dayStart, LocalDate dayEnd) {
        String query = "INSERT INTO schedule (tutor_id, subject, time_start, time_end, day_start, day_end, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'empty')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            statement.setString(2, subject);
            statement.setObject(3, timeStart);
            statement.setObject(4, timeEnd);
            statement.setObject(5, dayStart);
            statement.setObject(6, dayEnd);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating schedule: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy danh sách các lịch học còn trống
     */
    public List<ScheduleRegister> getAvailableSchedules() {
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT s.schedule_id, s.tutor_id, s.subject, s.time_start, s.time_end, " +
                "s.day_start, s.day_end, t.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s " +
                "JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "WHERE s.status = 'empty' " +
                "ORDER BY s.tutor_id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ScheduleRegister schedule = mapResultSetToScheduleRegister(resultSet);
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching available schedules: " + e.getMessage(), e);
        }
        return schedules;
    }

    /**
     * Lấy danh sách lịch học của một gia sư
     */
    public List<ScheduleRegister> getTutorSchedules(int tutorId) {
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT s.schedule_id, s.tutor_id, s.subject, s.time_start, s.time_end, " +
                "s.day_start, s.day_end, t.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s " +
                "JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "WHERE s.tutor_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ScheduleRegister schedule = mapResultSetToScheduleRegister(resultSet);
                    schedules.add(schedule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutor schedules: " + e.getMessage(), e);
        }
        return schedules;
    }

    /**
     * Hủy một lịch học nếu nó chưa được đặt
     */
    public boolean cancelSchedule(int tutorId, int scheduleId) {
        String query = "DELETE FROM schedule WHERE schedule_id = ? AND tutor_id = ? AND status = 'empty'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, tutorId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error cancelling schedule: " + e.getMessage(), e);
        }
    }

    /**
     * Kiểm tra xem lịch mới có xung đột với lịch hiện tại của gia sư không
     */
    public boolean isScheduleConflict(int tutorId, LocalTime timeStart, LocalTime timeEnd,
                                      LocalDate dayStart, LocalDate dayEnd) {
        String query = "SELECT COUNT(*) " +
                "FROM schedule " +
                "WHERE tutor_id = ? " +
                "AND ((day_start <= ? AND day_end >= ?) " +
                "OR (time_start < ? AND time_end > ?))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            statement.setObject(2, dayEnd);
            statement.setObject(3, dayStart);
            statement.setObject(4, timeEnd);
            statement.setObject(5, timeStart);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking schedule conflict: " + e.getMessage(), e);
        }
        return false;
    }

    // Helper method để ánh xạ ResultSet thành ScheduleRegister
    private ScheduleRegister mapResultSetToScheduleRegister(ResultSet resultSet) throws SQLException {
        ScheduleRegister schedule = new ScheduleRegister();
        schedule.setScheduleId(resultSet.getInt("schedule_id"));
        schedule.setTutorId(resultSet.getInt("tutor_id"));
        schedule.setSubject(resultSet.getString("subject"));
        schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
        schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
        schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
        schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
        schedule.setTutorName(resultSet.getString("tutor_name"));
        schedule.setPhoneNumber(resultSet.getString("phone_number"));
        schedule.setPrice(resultSet.getInt("salary"));
        return schedule;
    }
}