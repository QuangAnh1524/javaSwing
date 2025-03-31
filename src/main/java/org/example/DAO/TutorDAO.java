package org.example.DAO;

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
        String query = "SELECT * FROM tutors"; // Không cần join với users nữa
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
        String query = "DELETE FROM tutors WHERE tutor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tutorId);
            return stmt.executeUpdate() > 0;
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
}