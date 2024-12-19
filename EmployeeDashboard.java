import javax.swing.*;
import java.awt.*;

public class EmployeeDashboard extends JFrame {
    private User user;
    private JLabel hoursWorkedLabel;
    private JLabel totalEarningsLabel;

    public EmployeeDashboard(User user) {
        this.user = user;
        setTitle("Employee Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        hoursWorkedLabel = new JLabel("Hours Worked: 0.00", SwingConstants.CENTER);
        totalEarningsLabel = new JLabel("Total Earnings: $0.00", SwingConstants.CENTER);
        infoPanel.add(hoursWorkedLabel);
        infoPanel.add(totalEarningsLabel);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton logHoursButton = new JButton("Log Hours");
        logHoursButton.addActionListener(e -> logHours());
        buttonPanel.add(logHoursButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateInfo();
    }

    private void updateInfo() {
        for (Employee employee : EmployeeManagementSystem.getEmployees()) {
            if (employee.getUser ().getUsername().equals(user.getUsername())) {
                hoursWorkedLabel.setText("Hours Worked: " + String.format("%.2f", employee.getHoursWorked()));
                totalEarningsLabel.setText("Total Earnings: $" + String.format("%.2f", employee.getTotalEarnings()));
                return;
            }
        }
        hoursWorkedLabel.setText("Hours Worked: 0.00");
        totalEarningsLabel.setText("Total Earnings: $0.00");
    }

    private void logHours() {
        JTextField hoursField = new JTextField();
        Object[] message = {
            "Enter hours worked:", hoursField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Log Hours", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double hours = Double.parseDouble(hoursField.getText());
                for (Employee employee : EmployeeManagementSystem.getEmployees()) {
                    if (employee.getUser ().getUsername().equals(user.getUsername())) {
                        employee.logHours(hours);
                        JOptionPane.showMessageDialog(this, "Hours logged successfully!");
                        updateInfo();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Employee not found.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid hours input. Please enter a number.");
            }
        }
    }
}