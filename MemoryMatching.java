import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer.*;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;


public class MemoryMatching extends JFrame implements ActionListener{

    JPanel p, pSub, pError;
    JLabel title, prompt;
    HashMap<Integer, Integer> cards = new HashMap<Integer, Integer>();
    ArrayList<Integer> randomOrder = new ArrayList<Integer>();
    ArrayList<Card> drawingCards = new ArrayList<Card>(); 
    BufferedImage[] images = new BufferedImage[10];
    Random rand = new Random();
    int nCards;
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Introduction();
                // new MemoryMatching(new GridLayout(2,5));
            }
        });
    }

    static class Introduction extends JFrame implements ActionListener{

        boolean introVisible;
        GridLayout gridFormat;
        ButtonGroup b;
        JPanel intro;

        public Introduction() {
            this.setTitle("Memory Matching Introduction");
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            b = new ButtonGroup();
            intro = new JPanel();
            intro.setPreferredSize(new Dimension(500, 250));
            intro.setLayout(new BoxLayout(intro, BoxLayout.LINE_AXIS));
            intro.add(Box.createRigidArea(new Dimension(125, 0)));
            
            JRadioButton easy = new JRadioButton("2 x 5");
            easy.setActionCommand("easy");
            easy.addActionListener(this);
            b.add(easy);
            intro.add(easy);

            JRadioButton medium = new JRadioButton("3 x 4");
            medium.setActionCommand("medium");
            medium.addActionListener(this);
            b.add(medium);
            intro.add(medium);

            JRadioButton hard = new JRadioButton("4 x 4");
            hard.setActionCommand("hard");
            hard.addActionListener(this);
            b.add(hard);
            intro.add(hard);

            JRadioButton hardest = new JRadioButton("4 x 5");
            hardest.setActionCommand("hardest");
            hardest.addActionListener(this);
            b.add(hardest);
            intro.add(hardest);
            
            this.add(intro);
            this.pack();
            this.setVisible(true);
            
            JButton close = new JButton("Start");
            close.setActionCommand("Start");
            close.addActionListener(this);
            intro.add(close);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String event = e.getActionCommand();
            if (event.equals("easy")) {
                gridFormat = new GridLayout(2,5);
            } else if (event.equals("medium")) {
                gridFormat = new GridLayout(3, 4);
            }else if (event.equals("hard")) {
                gridFormat = new GridLayout(4,4);
            } else if (event.equals("hardest")) {
                gridFormat = new GridLayout(4,5);
            } else if (event.equals("Start")) {
                this.setVisible(introVisible);
                new MemoryMatching(gridFormat);  
            }
        }
    }

    public MemoryMatching(GridLayout grid) {

        int x = grid.getColumns() * grid.getRows();
        generateRandom(x);
        addCard();

        this.setTitle("Memory Matching");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel();
        p = new JPanel();
        p.setPreferredSize(new Dimension(1500, 750));
        p.setLayout(new BorderLayout(5,10));
        gridPanel.setLayout(grid);
    
        prompt = new JLabel("Find the Matching Pairs!");
        prompt.setFont(new Font("Calibri", Font.BOLD, 20));
        prompt.setPreferredSize(new Dimension(100, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(prompt, BorderLayout.PAGE_START);

        //temp to get to draw everything
        // for (int i = 0; i < 10; i++) {
        //     gridPanel.add(new DrawingPanel(i));
        // }

        for (int i = 0; i < drawingCards.size(); i++) {
            gridPanel.add(new DrawingPanel(i));
        }

        p.add(gridPanel, BorderLayout.CENTER);
        
        this.add(p);
        this.pack();
        this.setVisible(true);
        addImages();
    }

    class DrawingPanel extends JPanel {
        int n;
        DrawingPanel(int draw) {
            n = draw;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage((drawingCards.get(n)).getImage(), 0,0,getWidth(), getHeight(),null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void generateRandom(int n) {
        int sum = 0;
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

    public void addCard() {
        for (Integer i: randomOrder) {
            drawingCards.add(new Card(i, images[i - 1], false));
        }
    }

    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
        img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An Image failed to load: " + filename, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return img;
    }

    public void addImages() {
        for (int i = 1; i < 11; i++) {
            images[i - 1] = loadImage("img" + String.valueOf(i) + ".png");
        }
    }
}
