package dao;

import db.DBConnection;
import model.User;
import service.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static void register(User user) {
        String sql = "INSERT INTO users(username, password_hash) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String hashed = PasswordUtil.hashPassword(user.getPassword());

            ps.setString(1, user.getUsername());
            ps.setString(2, hashed);

            ps.executeUpdate();

            System.out.println("User Registered Successfully!");

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists! Try another.");
        } catch (Exception e) {
            System.out.println("Registration Failed due to system error.");
        }

    }

    public static int login(User user) {

    String sql = "SELECT user_id, password_hash FROM users WHERE username = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, user.getUsername());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String storedHash = rs.getString("password_hash");
            String enteredHash = PasswordUtil.hashPassword(user.getPassword());

            if (storedHash.equals(enteredHash)) {
                System.out.println("Login Successful!");
                return rs.getInt("user_id");  // ⭐ RETURN ID
            }
        }

        return -1;

    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
}

    public static int getUserId(String username) {

    String sql = "SELECT user_id FROM users WHERE username = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, username);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1; // user not found
}



}
