package org.example.DAO;

import org.example.database.DatabaseConnection;
import org.example.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TutorDAO {

    private Connection connection;

    public TutorDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Cập nhật profile gia sư
    public boolean updateTutorProfile(int tutorId, String name, String phoneNumber) {
        String query = "UPDATE tutor SET name = ?, phone_number = ? WHERE tutor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setInt(3, tutorId);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật profile gia sư", e);
        }
    }

    //Lấy thông tin gia sư ddeer hiển thị
    public Tutor getTutorById(int tutorId) {
        String query = "SELECT * FROM tutor WHERE tutor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorId);
            try (java.sql.ResultSet resultSet = statement.executeQuery()) {
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
            throw new RuntimeException("Lỗi khi lấy thông tin gia sư", e);
        }
        return null;
    }

    //tinh luong

}