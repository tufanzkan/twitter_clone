import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends JFrame {

    private List<JPanel> textPanels;
    private List<Integer> likeCounts;

    public Homepage() {
        // JFrame ayarları
        setTitle("Texts with Likes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Text ve Like sayısını saklamak için listeler oluşturun
        textPanels = new ArrayList<>();
        likeCounts = new ArrayList<>();

        // JButton oluşturma ve ActionListener eklemek
        JButton button = new JButton("Yaz");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JTextArea'dan metin alın
                String inputText = JOptionPane.showInputDialog(Homepage.this, "Yazı ekleyin:");
                if (inputText != null && !inputText.trim().isEmpty()) {
                    // Metni yeni bir kutucukta göster
                    addTextPanel(inputText);
                }
            }
        });
        // JButton'u JFrame'e ekleme
        add(button);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Homepage frame = new Homepage();
                frame.setVisible(true);
            }
        });
    }
}
