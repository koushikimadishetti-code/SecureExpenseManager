package model;

import java.time.LocalDate;

public class Expense {

    private int expenseId;
    private int userId;
    private double amount;
    private String category;
    private String note;
    private LocalDate date;

    // constructor for insert
    public Expense(int userId, double amount, String category, String note, LocalDate date) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    // constructor for fetch/display
    public Expense(int expenseId, int userId, double amount, String category, String note, LocalDate date) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    // getters
    public int getExpenseId() { return expenseId; }
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getNote() { return note; }
    public LocalDate getDate() { return date; }
}
