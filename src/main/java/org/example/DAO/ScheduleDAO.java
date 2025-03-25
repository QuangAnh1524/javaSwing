package org.example.DAO;

import org.example.database.DatabaseConnection;
import org.example.model.Schedule;
import org.example.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    private Connection connection;

    public ScheduleDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Lấy danh sách lịch trống của chính gia sư (nếu cần giữ lại)
    public List<Schedule> getEmptyScheduleForTutor(int tutorId) {
        List<Schedule> scheduleList = new ArrayList<>();
        String query = "SELECT * FROM schedule WHERE status = 'empty' AND tutor_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleId(resultSet.getInt("schedule_id"));
                    schedule.setTutorId(resultSet.getInt("tutor_id"));
                    schedule.setStudentId(resultSet.getInt("student_id"));
                    schedule.setStatus(resultSet.getString("status"));
                    schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                    schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                    schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                    schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                    scheduleList.add(schedule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách lịch trống của gia sư", e);
        }
        return scheduleList;
    }

    // Lấy danh sách lịch của một gia sư cụ thể (cả trống và đã đặt)
    public List<Schedule> getScheduleByTutor(int tutorId) {
        List<Schedule> scheduleList = new ArrayList<>();
        String query = "SELECT * FROM schedule WHERE tutor_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleId(resultSet.getInt("schedule_id"));
                    schedule.setTutorId(resultSet.getInt("tutor_id"));
                    schedule.setStudentId(resultSet.getInt("student_id"));
                    schedule.setStatus(resultSet.getString("status"));
                    schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                    schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                    schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                    schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                    scheduleList.add(schedule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách lịch của gia sư", e);
        }
        return scheduleList;
    }

    // Kiểm tra lịch của gia sư có bị trùng không
    public boolean hasScheduleConflict(int tutorId, LocalDate dayStart, LocalTime timeStart, LocalTime timeEnd) {
        String query = "SELECT COUNT(*) FROM schedule WHERE tutor_id = ? AND day_start = ? AND " +
                "((time_start <= ? AND time_end >= ?) OR " +
                "(time_start <= ? AND time_end >= ?) OR " +
                "(time_start >= ? AND time_end <= ?))";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            statement.setDate(2, java.sql.Date.valueOf(dayStart));
            statement.setTime(3, java.sql.Time.valueOf(timeStart));
            statement.setTime(4, java.sql.Time.valueOf(timeStart));
            statement.setTime(5, java.sql.Time.valueOf(timeEnd));
            statement.setTime(6, java.sql.Time.valueOf(timeEnd));
            statement.setTime(7, java.sql.Time.valueOf(timeStart));
            statement.setTime(8, java.sql.Time.valueOf(timeEnd));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra trùng lịch", e);
        }
        return false;
    }

    // Gia sư xóa lịch (thay vì chỉ hủy gắn kết)
    public boolean deleteSchedule(int scheduleId, int tutorId) {
        String query = "DELETE FROM schedule WHERE schedule_id = ? AND tutor_id = ? AND status = 'empty'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, tutorId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa lịch của gia sư", e);
        }
    }

    // Lấy thông tin chi tiết của một lịch theo ID
    public Schedule getScheduleById(int scheduleId) {
        String query = "SELECT * FROM schedule WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, scheduleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleId(resultSet.getInt("schedule_id"));
                    schedule.setTutorId(resultSet.getInt("tutor_id"));
                    schedule.setStudentId(resultSet.getInt("student_id"));
                    schedule.setStatus(resultSet.getString("status"));
                    schedule.setTimeStart(resultSet.getTime("time_start").toLocalTime());
                    schedule.setTimeEnd(resultSet.getTime("time_end").toLocalTime());
                    schedule.setDayStart(resultSet.getDate("day_start").toLocalDate());
                    schedule.setDayEnd(resultSet.getDate("day_end").toLocalDate());
                    return schedule;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy lịch theo ID", e);
        }
        return null;
    }

    // Lấy thông tin gia sư cho một lịch
    public Tutor getTutorForSchedule(int scheduleId) {
        String query = "SELECT t.* FROM tutor t " +
                "JOIN schedule s ON t.tutor_id = s.tutor_id " +
                "WHERE s.schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, scheduleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Tutor tutor = new Tutor();
                    tutor.setTutorId(resultSet.getInt("tutor_id"));
                    tutor.setUserId(resultSet.getInt("user_id"));
                    tutor.setName(resultSet.getString("name"));
                    tutor.setPhoneNumber(resultSet.getString("phone_number"));
                    tutor.setSalary(resultSet.getInt("salary"));
                    return tutor;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy thông tin gia sư cho lịch", e);
        }
        return null;
    }

    // Gia sư tạo lịch dạy
    public boolean createScheduleForTutor(int tutorId, LocalDate dayStart, LocalDate dayEnd,
                                          LocalTime timeStart, LocalTime timeEnd) {
        // Kiểm tra trùng lịch trước khi tạo
        if (hasScheduleConflict(tutorId, dayStart, timeStart, timeEnd)) {
            return false; // Trả về false nếu lịch bị trùng
        }

        String query = "INSERT INTO schedule (tutor_id, time_start, time_end, day_start, day_end, status) " +
                "VALUES (?, ?, ?, ?, ?, 'empty')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            statement.setTime(2, java.sql.Time.valueOf(timeStart));
            statement.setTime(3, java.sql.Time.valueOf(timeEnd));
            statement.setDate(4, java.sql.Date.valueOf(dayStart));
            statement.setDate(5, java.sql.Date.valueOf(dayEnd));
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tạo lịch dạy cho gia sư", e);
        }
    }
}