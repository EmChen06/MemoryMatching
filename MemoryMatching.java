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


public class MemoryMatching extends JFrame implements ActionListener, MouseListener {

    JPanel p, pSub, pError;
    JLabel title, prompt, count;
    HashMap<Integer, Integer> cards = new HashMap<Integer, Integer>();
    ArrayList<Integer> randomOrder = new ArrayList<Integer>();
    ArrayList<Card> drawingCards = new ArrayList<Card>(); 
    BufferedImage[] images = new BufferedImage[10];
    Random rand = new Random();
    int nCards, identify, max;
    boolean match;
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Introduction();
            }
        });
    }

    static class Introduction extends JFrame implements ActionListener {

        boolean introVisible;
        GridLayout gridFormat;
        ButtonGroup b;
        JPanel intro;

        public Introduction() {
            this.setTitle("Memory Matching Introduction");
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
            this.setLocationRelativeTo(null);
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
                gridFormat = new GridLayout(2,5, 5, 5);
            } else if (event.equals("medium")) {
                gridFormat = new GridLayout(3, 4, 5, 5);
            }else if (event.equals("hard")) {
                gridFormat = new GridLayout(4,4, 5, 5);
            } else if (event.equals("hardest")) {
                gridFormat = new GridLayout(4,5, 5, 5);
            } else if (event.equals("Start")) {
                this.setVisible(introVisible);
                new MemoryMatching(gridFormat);  
            }
        }
    }

    public MemoryMatching(GridLayout grid) {

        addImages();
        int x = grid.getColumns() * grid.getRows();
        generateRandom(x);
        addCard();

        this.setTitle("Memory Matching");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel();
        p = new JPanel();
        p.setPreferredSize(new Dimension(1500, 750));
        p.setLayout(new BorderLayout(5,0));
        gridPanel.setLayout(grid);
        for (int i = 0; i < drawingCards.size(); i++) {
            identify = i;
            gridPanel.add(new DrawingPanel(i));
            this.addMouseListener(this);
        }
    
        prompt = new JLabel("Find the Matching Pairs!");
        prompt.setFont(new Font("Calibri", Font.BOLD, 20));
        prompt.setPreferredSize(new Dimension(100, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(prompt, BorderLayout.PAGE_START);

        count = new JLabel("Matching Pairs Found: ");
        count.setFont(new Font("Calibri", Font.BOLD, 20));
        count.setPreferredSize(new Dimension(100, 50));
        count.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(count, BorderLayout.PAGE_END);

        //temp to get to draw everything
        // for (int i = 0; i < 10; i++) {
        //     gridPanel.add(new DrawingPanel(i));
        // }

        p.add(gridPanel, BorderLayout.CENTER);
        
        this.add(p);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
            if (drawingCards.get(n).getFlipped()) {
                g2.drawImage((drawingCards.get(n)).getImage(), 0,0,getWidth(), getHeight(),null);
            } else {
                g2.fillRect(0,0,getWidth(), getHeight());
            }
            //here to make me feel better about myself
            // g2.drawImage((drawingCards.get(n)).getImage(), 0,0,getWidth(), getHeight(),null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override  
    public void mouseClicked(MouseEvent e) {
        boolean backTrigger = true;
        if(e.getClickCount() > 0) {
            if(backTrigger) {
                (drawingCards.get(identify)).setFlipped(true);
            }
        }
        backTrigger = !backTrigger;

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void generateRandom(int x) {
        nCards = x / 2;
        int n;
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

    public boolean checkMatch(Card c1, Card c2) {
        if ((c1.getCardType()) == (c2.getCardType())) {
            match = true;
        } else match = false;
        return match;
    }

    public void yesMatch() {
        
    }

    public void noMatch() {
        //flipped = false
        //say womp womp :(
    }

}
