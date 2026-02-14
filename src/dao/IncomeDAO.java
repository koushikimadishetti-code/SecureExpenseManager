package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class IncomeDAO {

    public static void addIncome(int userId, double amount, String source) {

        String sql = "INSERT INTO income(user_id, amount, source, date) VALUES (?, ?, ?, CURDATE())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, source);

            ps.executeUpdate();

            System.out.println("Income Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
