import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends JFrame {

    PreparedStatement stmt = null;
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","root","theozkan1905");
    public JPanel messagePanel;
    public List<JPanel> textPanels;
    public List<Integer> likeCounts;

    public Homepage(String username) throws SQLException {
        // JFrame ayarları
        setTitle("Texts with Likes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Text ve Like sayısını saklamak için listeler oluşturun
        textPanels = new ArrayList<>();
        likeCounts = new ArrayList<>();

        JLabel namelabel = new JLabel("    "+username);
        JButton button = new JButton("Tweet");
        JPanel panel= new JPanel(new GridLayout(1,2));
        panel.add(namelabel);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JTextArea'dan metin alın
                String inputText = JOptionPane.showInputDialog(Homepage.this, "Write Tweet..");
                if (inputText != null && !inputText.trim().isEmpty()) {
                    // Metni yeni bir kutucukta göster
                    addTextPanel(inputText);

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
        // JButton'u JFrame'e ekleme
        add(panel);
    }

    private void addTextPanel(String text) {
        // Metin kutucuğunu içeren JPanel oluşturun
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // JTextArea oluşturun ve metni ekle
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);

        // Beğeni sayısını saklamak için değişken oluşturun ve 0 olarak ayarlayın
        int likeCount = 0;
        likeCounts.add(likeCount);

        // Beğeni butonunu oluşturun
        JButton likeButton = new JButton("Beğen: " + likeCount);
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Beğeni sayısını artır ve düğme metnini güncelle
                likeCounts.set(textPanels.indexOf(panel), likeCounts.get(textPanels.indexOf(panel)) + 1);
                likeButton.setText("Beğen: " + likeCounts.get(textPanels.indexOf(panel)));
            }
        });

        // JTextArea ve Beğeni butonunu panel'e ekle
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(likeButton, BorderLayout.SOUTH);

        // Paneli JFrame'e ekle ve güncelle
        textPanels.add(panel);
        add(panel);
        revalidate();
        repaint();
    }
}
