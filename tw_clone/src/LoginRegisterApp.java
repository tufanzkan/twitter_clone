import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginRegisterApp() {
        setTitle("Login/Register App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Login Button Action Listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                LoginManager loginManager = new LoginManager();
                boolean loggedIn = loginManager.login(username, password);

                if (loggedIn) {
                    JOptionPane.showMessageDialog(LoginRegisterApp.this, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(LoginRegisterApp.this, "Invalid username or password!");
                }
            }
        });

        // Register Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                RegisterManager registerManager = new RegisterManager();
                boolean registered = registerManager.register(username, password);

                if (registered) {
                    JOptionPane.showMessageDialog(LoginRegisterApp.this, "Registration successfully completed!");
                } else {
                    JOptionPane.showMessageDialog(LoginRegisterApp.this, "Registration failed!");
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterApp();
            }
        });
    }
}