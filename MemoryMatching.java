import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer.*;
import java.util.Random;
import java.util.HashMap;


public class MemoryMatching implements ActionListener{

    JFrame f;
    JPanel p, pSub, pError;
    JLabel title, prompt;
    JComboBox grid;
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MemoryMatching();
            }
        });
    }

    public MemoryMatching() {
        f = new JFrame("Memory Matching");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        p = new JPanel();
        p.setPreferredSize(new Dimension(750, 500));
        p.setLayout(new BorderLayout(10,10));
        
        f.add(p);
        f.pack();
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
