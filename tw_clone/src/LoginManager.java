import javax.swing.*;
import javax.swing.text.PasswordView;
import java.sql.*;

public class LoginManager {
    Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/metaland","root","theozkan1905");
    Statement myStat = myCon.createStatement();
    ResultSet myRes = myStat.executeQuery("select * from mydatabase.users");
    public JPanel messagePanel;
    public LoginManager() throws SQLException {}

    public void login(String username, String password) {

        try {
            while (myRes.next()) {
                if (myRes.getString("username").equals(username) && myRes.getString("password").equals(password)) {
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);
                }
                if (myRes.getString("username").equals(username) && !myRes.getString("password").equals(password)) {
                    JOptionPane.showMessageDialog(messagePanel, "Incorrect Password or Username!");
                }
                if (!myRes.getString("username").equals(username) && myRes.getString("password").equals(password)) {
                    JOptionPane.showMessageDialog(messagePanel, "Incorrect Username or Password!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
