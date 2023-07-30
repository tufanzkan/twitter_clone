import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends JFrame {
    PreparedStatement stmt = null;
    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/mydatabase";
    String usrnm = "root";
    String password = "theozkan1905";
    JPanel contentPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(contentPanel);

    public Homepage(String username) throws SQLException {
        // JFrame ayarları
        setTitle("HomePage");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(scrollPane);

        conn = DriverManager.getConnection(url,usrnm,password);
        Statement stment = conn.createStatement();
        String sorg = "SELECT username, followers FROM users";
        ResultSet resultS = stment.executeQuery(sorg);
        JLabel namelabel = null;
        JLabel flwLabel = null;

        while (resultS.next()){
            String usname = resultS.getString("username");
            int flwrs = resultS.getInt("followers");
            if (usname.equals(username))
            {
                namelabel = new JLabel("    "+username);
                flwLabel = new JLabel("          Followers: "+flwrs);
            }
        }

        JButton button = new JButton("Tweet");
        JPanel panel= new JPanel(new GridLayout(1,3));
        panel.add(namelabel);
        panel.add(flwLabel);
        panel.add(button);
        contentPanel.add(panel);
        tweetshow();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = DriverManager.getConnection(url,usrnm,password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                // JTextArea'dan metin alın
                String inputText = JOptionPane.showInputDialog(Homepage.this, "Write Tweet..");
                if (inputText != null && !inputText.trim().isEmpty()) {
                    try {
                        String sql = "INSERT INTO tweets (username, tweet) VALUES (?, ?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, username);
                        stmt.setString(2, inputText);
                        // SQL sorgusunu çalıştır
                        stmt.executeUpdate();

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
        });
    }
    public void tweetshow() throws SQLException {

        conn = DriverManager.getConnection(url,usrnm,password);

        Statement statement = conn.createStatement();
        Statement statemen2 = conn.createStatement();
        String sorgu = "SELECT id, tweet, username, likecount FROM tweets";
        String sorg2 = "SELECT id, username, name, surname, email, password, followers FROM users";

        ResultSet resultSet = statement.executeQuery(sorgu);
        ResultSet resultSe2 = statemen2.executeQuery(sorg2);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String cumle = resultSet.getString("tweet");
            String yazar = resultSet.getString("username");
            final int[] begeniSayisi = {resultSet.getInt("likecount")};

            JPanel tpanel = new JPanel(new BorderLayout());
            JTextArea textArea = new JTextArea("  "+ yazar + "\n\n Tweet: " + cumle);
            textArea.setEditable(false);
            tpanel.add(textArea,BorderLayout.CENTER);

            JButton likeButton = new JButton("Like (" + begeniSayisi[0] + ")");
            JButton followButton = new JButton("Follow");

            JPanel likefollow = new JPanel(new GridLayout(1,2));
            LoginRegisterApp lgn = new LoginRegisterApp();
            String control = lgn.usernameField.getText();
            String cont = control.toLowerCase();

            if(!cont.equals(yazar)){
                likefollow.add(followButton);
                likefollow.add(likeButton);
            }
            else{
                likefollow.add(likeButton);
            }

            tpanel.add(likefollow,BorderLayout.SOUTH);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.add(tpanel);
            likeButton.addActionListener(new ActionListener() {
                private boolean isLiked = false;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isLiked) {
                        // Beğeni butonuna tıklandığında beğeni sayısını artır
                        begeniSayisi[0]++;
                        likeButton.setText("Like (" + begeniSayisi[0] + ")");

                        // Veritabanına bağlanarak beğeni sayısını güncelle
                        try {
                            String updateSorgu = "UPDATE tweets SET likecount = ? WHERE id = ?";
                            PreparedStatement preparedStatement = conn.prepareStatement(updateSorgu);
                            preparedStatement.setInt(1, begeniSayisi[0]);
                            preparedStatement.setInt(2, id);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        isLiked = true; // Buton artık tıklanamaz hale gelir
                    }
                }
            });

            followButton.addActionListener(new ActionListener() {
                private boolean isfollowed = false;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isfollowed) {
                        // Beğeni butonuna tıklandığında beğeni sayısını artır
                        try {
                            String updateSorg2 = "UPDATE users SET followers = followers+1 WHERE id = ?";
                            PreparedStatement preparedStatemen2 = conn.prepareStatement(updateSorg2);
                            preparedStatemen2.setInt(1, id);
                            preparedStatemen2.executeUpdate();
                            preparedStatemen2.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        followButton.setText("Followed");
                        isfollowed = true; // Buton artık tıklanamaz hale gelir
                    }
                }
            });

            revalidate();
            repaint();
        }
        resultSet.close();
        resultSe2.close();
        statement.close();
        statemen2.close();
    }

}
