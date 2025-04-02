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
    private Connection connection;

    public ScheduleDAO() {}

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
     * Lấy danh sách lịch day của một gia sư
     */
    public List<ScheduleRegister> getTutorSchedules(int tutorId)  {
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT s.schedule_id, s.tutor_id, s.subject, s.time_start, s.time_end, " +
                "s.day_start, s.day_end, st.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s " +
                "JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "JOIN students st ON st.student_id = s.student_id " +
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

    public List<ScheduleRegister> getAllSchedules() {
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT s.*, t.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s JOIN tutors t ON s.tutor_id = t.tutor_id";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ScheduleRegister sr = new ScheduleRegister();
                sr.setScheduleId(rs.getInt("schedule_id"));
                sr.setTutorId(rs.getInt("tutor_id"));
                sr.setSubject(rs.getString("subject"));
                sr.setTimeStart(rs.getTime("time_start").toLocalTime());
                sr.setTimeEnd(rs.getTime("time_end").toLocalTime());
                sr.setDayStart(rs.getDate("day_start").toLocalDate());
                sr.setDayEnd(rs.getDate("day_end").toLocalDate());
                sr.setTutorName(rs.getString("tutor_name"));
                sr.setPhoneNumber(rs.getString("phone_number"));
                sr.setPrice(rs.getInt("salary"));
                schedules.add(sr);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all schedules: " + e.getMessage(), e);
        }
        return schedules;
    }

    public boolean deleteSchedule(int scheduleId) {
        String query = "DELETE FROM schedule WHERE schedule_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting schedule: " + e.getMessage(), e);
        }
    }

    public List<ScheduleRegister> getTutorSchedulesByName(String tutorName)  {
        List<ScheduleRegister> schedules = new ArrayList<>();
        String query = "SELECT s.schedule_id, s.tutor_id, s.subject, s.time_start, s.time_end, " +
                "s.day_start, s.day_end, t.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s " +
                "JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "WHERE t.name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + tutorName + "%");
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
    // Lấy danh sách lịch học của một gia sư cụ thể dựa trên tutor_id
    public List<ScheduleRegister> getSchedulesByTutorId(int tutorId) {
        List<ScheduleRegister> schedules = new ArrayList<>();
        // Truy vấn lấy lịch học từ bảng schedule, kết hợp với bảng tutors để lấy thông tin gia sư
        String query = "SELECT s.*, t.name AS tutor_name, t.phone_number, t.salary " +
                "FROM schedule s JOIN tutors t ON s.tutor_id = t.tutor_id " +
                "WHERE s.tutor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tutorId); // Gán giá trị tutor_id vào truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ScheduleRegister sr = new ScheduleRegister();
                    sr.setScheduleId(rs.getInt("schedule_id"));
                    sr.setTutorId(rs.getInt("tutor_id"));
                    sr.setSubject(rs.getString("subject"));
                    sr.setTimeStart(rs.getTime("time_start").toLocalTime());
                    sr.setTimeEnd(rs.getTime("time_end").toLocalTime());
                    sr.setDayStart(rs.getDate("day_start").toLocalDate());
                    sr.setDayEnd(rs.getDate("day_end").toLocalDate());
                    sr.setTutorName(rs.getString("tutor_name"));
                    sr.setPhoneNumber(rs.getString("phone_number"));
                    sr.setPrice(rs.getInt("salary"));
                    schedules.add(sr);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching schedules for tutor " + tutorId + ": " + e.getMessage(), e);
        }
        return schedules;
    }
}
