import javax.swing.*;
import java.io.*;
import java.awt.*;

public class ManagerDashboard extends JFrame {
    private JComboBox<Employee> employeeComboBox;
    private JButton detailsButton;
    private JPanel employeePanel;

    public ManagerDashboard() {
        setTitle("Manager Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JLabel welcomeLabel = new JLabel("Welcome to the Manager Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        
        employeePanel = new JPanel();
        employeePanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(employeePanel);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());

        employeeComboBox = new JComboBox<>();
        updateEmployeeComboBox();

        selectionPanel.add(new JLabel("Select Employee:"));
        selectionPanel.add(employeeComboBox);

        
        detailsButton = new JButton("Show Details");
        detailsButton.addActionListener(e -> showEmployeeDetails((Employee) employeeComboBox.getSelectedItem()));
        selectionPanel.add(detailsButton);

        add(selectionPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(e -> addEmployee());
        buttonPanel.add(addEmployeeButton);

        JButton financialReportButton = new JButton("Generate Financial Report");
        financialReportButton.addActionListener(e -> generateFinancialReport());
        buttonPanel.add(financialReportButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void updateEmployeeComboBox() {
        employeeComboBox.removeAllItems(); 
        for (Employee employee : EmployeeManagementSystem.getEmployees()) {
            employeeComboBox.addItem(employee); 
        }
        updateEmployeeList(); 
    }

    private void updateEmployeeList() {
        employeePanel.removeAll(); 
        for (Employee employee : EmployeeManagementSystem.getEmployees()) {
            String employeeInfo = String.format("%s - Hours Worked: %.2f - Total Earnings: $%.2f",
                    employee.getName(), employee.getHoursWorked(), employee.getTotalEarnings());
            JLabel employeeLabel = new JLabel(employeeInfo, SwingConstants.CENTER);
            employeePanel.add(employeeLabel);
        }
        employeePanel.revalidate();
        employeePanel.repaint();
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            String details = String.format("Name: %s\nPosition: %s\nHourly Wage: $%.2f\nHours Worked: %.2f\nTotal Earnings: $%.2f\nUsername: %s\nPassword: %s",
                    employee.getName(), employee.getPosition(), employee.getHourlyWage(), employee.getHoursWorked(),
                    employee.getTotalEarnings(), employee.getUser ().getUsername(), employee.getUser ().getPassword());

            JOptionPane.showMessageDialog(this, details, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        JTextField nameField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField wageField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Name:", nameField,
            "Position:", positionField,
            "Hourly Wage:", wageField,
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String position = positionField.getText();
            double hourlyWage;

            try {
                hourlyWage = Double.parseDouble(wageField.getText());
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User newUser  = new User(username, password, "employee");
                Employee newEmployee = new Employee(name, position, hourlyWage, newUser );
                EmployeeManagementSystem.getEmployees().add(newEmployee);
                EmployeeManagementSystem.getUsers().add(newUser );

                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                updateEmployeeComboBox(); 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid wage input. Please enter a number.");
            }
        }
    }

    private void generateFinancialReport() {
        if (EmployeeManagementSystem.getEmployees().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found to generate a report.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder reportBuilder = new StringBuilder();
        double totalPaid = 0.0;

        reportBuilder.append("Employee Financial Report\n");
        reportBuilder.append("--------------------------------------------------\n");
        reportBuilder.append(String.format("%-20s %-15s %-15s %-15s\n", 
            "Employee Name", "Hours Worked", "Total Earnings", "Overtime Hours"));

        for (Employee employee : EmployeeManagementSystem.getEmployees()) {
            double hoursWorked = employee.getHoursWorked();
            double totalEarnings = employee.getTotalEarnings();
            double overtimeHours = Math.max(0, hoursWorked - 40);

            reportBuilder.append(String.format("%-20s %-15.2f $%-14.2f %-15.2f\n",
                employee.getName(), hoursWorked, totalEarnings, overtimeHours));

            totalPaid += totalEarnings + (overtimeHours * employee.getHourlyWage() * 1.5);
        }

        reportBuilder.append("--------------------------------------------------\n");
        reportBuilder.append(String.format("Total Amount Paid to All Employees: $%.2f\n", totalPaid));

        try (PrintWriter writer = new PrintWriter("employeeFinancialReport.txt")) {
            writer.println(reportBuilder.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Financial report generated successfully!", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
    }
}
       