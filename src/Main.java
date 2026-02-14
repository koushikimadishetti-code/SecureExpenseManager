import dao.UserDAO;
import dao.ExpenseDAO;
import dao.IncomeDAO;
import model.User;
import model.Expense;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Secure Expense Manager =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = new User(username, password);

        // ================= REGISTER =================
        if (choice == 1) {
            UserDAO.register(user);
            sc.close();
            return;
        }

        // ================= LOGIN =================
        int userId = UserDAO.login(user);

    if (userId == -1) {
        System.out.println("Login failed!");
        sc.close();
        return;
    }


        

        // ================= MENU LOOP =================
        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Monthly Total");
            System.out.println("4. Delete Expense");
            System.out.println("5. Add Income");
            System.out.println("6. Budget Status");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            // -------- ADD EXPENSE --------
            if (ch == 1) {

                System.out.print("Enter amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                System.out.print("Enter note: ");
                String note = sc.nextLine();

                Expense exp = new Expense(userId, amount, category, note, java.time.LocalDate.now());

                ExpenseDAO.addExpense(exp);

                // Check budget automatically
                ExpenseDAO.checkBudgetAlert(userId);
            }

            // -------- VIEW --------
            else if (ch == 2) {
                ExpenseDAO.viewExpenses(userId);
            }

            // -------- MONTHLY TOTAL --------
            else if (ch == 3) {

                System.out.print("Enter year (YYYY): ");
                int year = sc.nextInt();

                System.out.print("Enter month (1-12): ");
                int month = sc.nextInt();
                sc.nextLine();

                ExpenseDAO.getMonthlyTotal(userId, year, month);
            }

            // -------- DELETE --------
            else if (ch == 4) {

                System.out.print("Enter Expense ID to delete: ");
                int id = sc.nextInt();
                sc.nextLine();

                ExpenseDAO.deleteExpense(id, username);
            }

            // -------- ADD INCOME --------
            else if (ch == 5) {

                System.out.print("Enter income amount: ");
                double income = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter source: ");
                String source = sc.nextLine();

                IncomeDAO.addIncome(userId, income, source);

                // Check budget after adding income
                ExpenseDAO.checkBudgetAlert(userId);
            }

            // -------- BUDGET STATUS --------
            else if (ch == 6) {
                ExpenseDAO.checkBudgetAlert(userId);
            }

            else if (ch == 7) {
                System.out.println("Goodbye 👋");
                break;
            }

            else {
                System.out.println("Invalid option!");
            }
        }

        sc.close();
    }
}
