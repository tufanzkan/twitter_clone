import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginRegisterApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginRegisterApp() {
        setTitle("Login/Register Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));

        JPanel topPanel= new JPanel(new GridLayout(2,2));
        JPanel bottomPanel= new JPanel(new GridLayout(3,1));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        topPanel.add(usernameLabel);
        topPanel.add(usernameField);
        topPanel.add(passwordLabel);
        topPanel.add(passwordField);

        loginButton = new JButton("Login");
        JLabel textField = new JLabel("Not a Member Yet..?");
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        registerButton = new JButton("Register");

        bottomPanel.add(loginButton);
        bottomPanel.add(textField);
        bottomPanel.add(registerButton);

        // Login Button Action Listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginManager loginManager = null;
                try {
                    loginManager = new LoginManager();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                loginManager.login(usernameField.getText(),password);
            }
        });

        // Register Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterManager registerManager = new RegisterManager();
                registerManager.register();
            }
        });

        add(topPanel);
        add(bottomPanel);
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