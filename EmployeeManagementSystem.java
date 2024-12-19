import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementSystem {
    private static List<User> users = new ArrayList<>();
    private static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {

        users.add(new User("admin", "admin", "manager"));

        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Employee> getEmployees() {
        return employees;
    }
}