import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer.*;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;


public class MemoryMatching extends JFrame implements ActionListener{

    JPanel p, pSub, pError;
    JLabel title, prompt;
    JComboBox grid;
    HashMap<Integer, Integer> cards = new HashMap<Integer, Integer>();
    ArrayList<Integer> randomOrder = new ArrayList<Integer>();
    Random rand = new Random();
    int nCards;
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MemoryMatching();
            }
        });
    }

    public MemoryMatching() {
        this.setTitle("Memory Matching");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        DrawingPanel panel = new DrawingPanel();
        p = new JPanel();
        p.setPreferredSize(new Dimension(750, 500));
        p.setLayout(new BorderLayout(10,10));
    
        prompt = new JLabel("test");
        prompt.setPreferredSize(new Dimension(100, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(prompt, BorderLayout.PAGE_START);
        
        this.add(p);
        this.pack();
        this.setVisible(true);
    }

    private class DrawingPanel extends JPanel {
        DrawingPanel() {
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    

    }

    public void generateCards() {
        int sum = 0;
        int n;
        for (int i = 1; i <= nCards; i++) {
            cards.put(i, 2);
            sum += 2;        
        }

        while (sum != 0) {
            n = rand.nextInt(nCards) + 1;
            if ((cards.get(n) - 1) >= 0) {
                cards.put(n, cards.get(n) - 1);
                sum -= 1;
                randomOrder.add(n);
            } else continue;
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
