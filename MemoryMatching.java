import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.Math;

public class MemoryMatching extends JFrame implements ActionListener {

    JPanel p;
    JLabel title, prompt, count;
    HashMap<Integer, Integer> cards = new HashMap<Integer, Integer>();
    ArrayList<Integer> randomOrder = new ArrayList<Integer>();
    ArrayList<Card> drawingCards = new ArrayList<Card>(); 
    ArrayList<Confetti> confetti = new ArrayList<Confetti>();
    BufferedImage[] images = new BufferedImage[10];
    Random rand = new Random();
    Stack<Card> cardsFlipped = new Stack<Card>();
    Timer t, tWin;
    int nCards, matched = 0;
    final int max = 2;
    
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
            System.out.println(drawingCards.get(i).getCardType());
            DrawingPanel panel = new DrawingPanel(i);

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) { //it will work if you click it quickly, but not when you add a delay between it
                        (drawingCards.get(panel.getCardIndex())).setFlipped(true);
                        panel.repaint();

                        for (int j = 0; j < drawingCards.size(); j++) {
                            if (drawingCards.get(j).getFlipped() && !drawingCards.get(j).getMatched()) {
                                if ((cardsFlipped.size() == 0) || (cardsFlipped.size() > 0 && drawingCards.get(j) != cardsFlipped.peek())) {
                                    cardsFlipped.push(drawingCards.get(j));
                                }

                                if (cardsFlipped.size() == max) { 
                                    Card temp1 = cardsFlipped.pop();
                                    Card temp2 = cardsFlipped.peek();
                                    System.out.println(temp1);
                                    System.out.println(temp2);
                                    cardsFlipped.push(temp1);
                                    checkMatch(temp1, temp2);
                                    cardsFlipped.clear();
                                    panel.repaint();
                                    break;
                                }

                            } else continue;
                        }
                    }
                }
            });

            gridPanel.add(panel);

        }
    
        prompt = new JLabel("Find the Matching Pairs!");
        prompt.setFont(new Font("Calibri", Font.BOLD, 20));
        prompt.setPreferredSize(new Dimension(100, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(prompt, BorderLayout.PAGE_START);

        count = new JLabel("Matching Pairs Found: " + matched);
        count.setFont(new Font("Calibri", Font.BOLD, 20));
        count.setPreferredSize(new Dimension(100, 50));
        count.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(count, BorderLayout.PAGE_END);

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

        public int getCardIndex() {
            return n;
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
            for (Confetti c: confetti) {
                g2.setColor(c.c);
                g2.fillOval(c.x, c.y, c.r, c.r);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

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
            drawingCards.add(new Card(i, images[i - 1], false, false));
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

    public void checkMatch(Card c1, Card c2) {
        if ((c1.getCardType()) == (c2.getCardType())) yesMatch(c1, c2);
        else noMatch(c1, c2);
    }

    public void yesMatch(Card c1, Card c2) {
        System.out.println("yay!");
        matched++;
        count.setText("Matching Pairs Found: " + matched);
        c1.setMatched(true);
        c2.setMatched(true);
        if (matched == (drawingCards.size() / 2)) win();
    }

    public void noMatch(Card c1, Card c2) {
        System.out.println("womp womp");
        t = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.setFlipped(false);
                c2.setFlipped(false);
                repaint();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    public void win() {
        tWin = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Math.random()*100 > 65) confetti.add(new Confetti()); //35% chance
                for (int i = 0; i < confetti.size(); i++) {
                    confetti.get(i).move();
                    if (confetti.get(i).y > getHeight()) {
                        confetti.remove(confetti.get(i));
                    }
                }
                repaint();
            }
        });
        tWin.start();
    }
}
