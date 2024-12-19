import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        add(loginButton);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            for (User  user : EmployeeManagementSystem.getUsers()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    if (user.getRole().equals("manager")) {
                        new ManagerDashboard().setVisible(true);
                    } else {
                        new EmployeeDashboard(user).setVisible(true);
                    }
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(LoginPage.this, "Invalid credentials");
        }
    }
}