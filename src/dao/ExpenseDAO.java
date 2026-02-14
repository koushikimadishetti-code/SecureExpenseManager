package dao;

import db.DBConnection;
import model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExpenseDAO {

    public static void addExpense(Expense exp) {

        String query = "INSERT INTO expenses(user_id, amount, category, note, date) VALUES (?, ?, ?, ?, ?)";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, exp.getUserId());
            ps.setDouble(2, exp.getAmount());
            ps.setString(3, exp.getCategory());
            ps.setString(4, exp.getNote());
            ps.setDate(5, java.sql.Date.valueOf(exp.getDate()));

            ps.executeUpdate();

            System.out.println("Expense Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void viewExpenses(int userId) {

    String query = "SELECT expense_id, amount, category, note, date FROM expenses WHERE user_id=? ORDER BY date DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n----- Your Expenses -----");

        while (rs.next()) {
            System.out.println(
                rs.getInt("expense_id") + " | ₹" +
                rs.getDouble("amount") + " | " +
                rs.getString("category") + " | " +
                rs.getString("note") + " | " +
                rs.getDate("date")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void getMonthlyTotal(int userId, int year, int month) {

    String query = """
        SELECT COALESCE(SUM(amount),0) AS total
        FROM expenses
        WHERE user_id = ?
        AND YEAR(date) = ?
        AND MONTH(date) = ?
        """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, userId);
        ps.setInt(2, year);
        ps.setInt(3, month);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            double total = rs.getDouble("total");
            System.out.println("\nTotal Expense for " + month + "/" + year + " : ₹" + total);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void deleteExpense(int id, String username) {

    String query = "DELETE FROM expenses WHERE id = ? AND username = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, id);
        ps.setString(2, username);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense not found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void checkBudgetAlert(int userId) {

    String expenseQ = "SELECT SUM(amount) FROM expenses WHERE user_id=? AND MONTH(date)=MONTH(CURDATE())";
    String incomeQ  = "SELECT SUM(amount) FROM income WHERE user_id=? AND MONTH(date)=MONTH(CURDATE())";

    try (Connection conn = DBConnection.getConnection()) {

        PreparedStatement e = conn.prepareStatement(expenseQ);
        e.setInt(1, userId);
        var ers = e.executeQuery();
        ers.next();
        double expense = ers.getDouble(1);

        PreparedStatement i = conn.prepareStatement(incomeQ);
        i.setInt(1, userId);
        var irs = i.executeQuery();
        irs.next();
        double income = irs.getDouble(1);

        System.out.println("\nIncome: ₹" + income);
        System.out.println("Expense: ₹" + expense);

        if (expense > income)
            System.out.println("⚠ WARNING: You exceeded your monthly budget!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}


}