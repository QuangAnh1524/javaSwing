package org.example.DAO;

import org.example.model.ScheduleTutorSalary;
import org.example.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorDAO {
    private final Connection connection;

    public TutorDAO(Connection connection) {
        this.connection = connection;
    }

    // Tạo gia sư mới
    public boolean createTutor(String name, String phoneNumber, int salary, int userId) {
        String query = "INSERT INTO tutors (name, phone_number, salary, user_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setInt(3, salary);
            stmt.setInt(4, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating tutor: " + e.getMessage(), e);
        }
    }

    // Lấy danh sách tất cả gia sư
    public List<Tutor> getAllTutors() {
        List<Tutor> tutors = new ArrayList<>();
        String query = "SELECT * FROM tutors";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setTutorId(rs.getInt("tutor_id"));
                tutor.setUserId(rs.getInt("user_id"));
                tutor.setName(rs.getString("name"));
                tutor.setPhoneNumber(rs.getString("phone_number"));
                tutor.setSalary(rs.getInt("salary"));
                tutors.add(tutor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutors: " + e.getMessage(), e);
        }
        return tutors;
    }

    // Xóa gia sư
    public boolean deleteTutor(int tutorId) {
        String deleteScheduleQuery = "DELETE FROM schedule WHERE tutor_id = ?";
        String deleteTutorQuery = "DELETE FROM tutors WHERE tutor_id = ?";

        try (PreparedStatement scheduleStmt = connection.prepareStatement(deleteScheduleQuery);
             PreparedStatement tutorStmt = connection.prepareStatement(deleteTutorQuery)) {

            // Xóa các bản ghi liên quan trong bảng schedule trước
            scheduleStmt.setInt(1, tutorId);
            scheduleStmt.executeUpdate();

            // Xóa tutor
            tutorStmt.setInt(1, tutorId);
            int rowsAffected = tutorStmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting tutor: " + e.getMessage(), e);
        }
    }

    // Lấy gia sư theo ID (dùng khi xóa)
    public Tutor getTutorById(int tutorId) {
        String query = "SELECT * FROM tutors WHERE tutor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tutorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tutor tutor = new Tutor();
                    tutor.setTutorId(rs.getInt("tutor_id"));
                    tutor.setUserId(rs.getInt("user_id"));
                    tutor.setName(rs.getString("name"));
                    tutor.setPhoneNumber(rs.getString("phone_number"));
                    tutor.setSalary(rs.getInt("salary"));
                    return tutor;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutor: " + e.getMessage(), e);
        }
        return null;
    }

    //cap nhat thong tin gsu
    public boolean updateTutor(int tutorId, String name, String phoneNumber, int salary) {
        String query = "UPDATE tutors SET name = ?, phone_number = ?, salary = ? WHERE tutor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setInt(3, salary);
            stmt.setInt(4, tutorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating tutor: " + e.getMessage(), e);
        }
    }

    // Lấy tutor_id dựa trên user_id
    public int getTutorIdByUserId(int userId) {
        String query = "SELECT tutor_id FROM tutors WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tutor_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutor_id by user_id: " + e.getMessage(), e);
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    public List<ScheduleTutorSalary> getTutorSalary(int tutorId){
        List<ScheduleTutorSalary> list = new ArrayList<>();
        String query = "SELECT " +
                "s.name," +
                "sch.subject, " +
                "t.salary " +
                "FROM schedule sch " +
                "INNER JOIN students s ON sch.student_id = s.student_id " +
                "INNER JOIN tutors t ON sch.tutor_id = t.tutor_id " +
                "WHERE t.tutor_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tutorId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ScheduleTutorSalary salary = new ScheduleTutorSalary();
                salary.setStudentName(rs.getString("name"));
                salary.setSalary(rs.getInt("salary"));
                salary.setSubjectName(rs.getString("subject"));
                list.add(salary);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }


}