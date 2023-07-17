import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterManager {
    String url = "jdbc:mysql://localhost:3306/mydatabase";
    String username = "root";
    String password = "theozkan1905";
    Connection conn = null;
    PreparedStatement stmt = null;
    public JTextField usernameField,nameField,surnameField,emailField,passwordField;
    public  JPanel messagePanel;
    public void register() {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Register Panel");
        frame.setSize(300, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        frame.add(panel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();

        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);

        frame.pack();
        panel.setVisible(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonActionPerformed(e);
            }
        });
    }

    private void registerButtonActionPerformed(ActionEvent e) {

        try {
            // Veritabanı bağlantısı oluştur
            conn = DriverManager.getConnection(url, username, password);

            // Kullanıcının girdiği değerleri al
            String username = usernameField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            int password = Integer.parseInt(passwordField.getText());

            // SQL sorgusu ve parametreleri
            String sql = "INSERT INTO tw (username, name, surname, email, password) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, email);
            stmt.setInt(5, password);

            // SQL sorgusunu çalıştır
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(messagePanel, "Register Succsessful!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Kaynakları serbest bırak
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}