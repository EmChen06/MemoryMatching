import java.awt.image.BufferedImage;

public class Card {

    private int cardType;
    private BufferedImage image;
    private boolean isFlipped;
    private boolean isMatched;

    Card(int cT, BufferedImage img, boolean flipped, boolean matched) {
        this.cardType = cT;
        this.image = img;
        this.isFlipped = flipped;
        this.isMatched = matched;
    }

    public void setMatched(boolean matched) {
        this.isMatched = matched;
    }

    public boolean getMatched() {
        return this.isMatched;
    }

    public void setFlipped(boolean flip) {
        this.isFlipped = flip;
    }

    public boolean getFlipped() {
        return this.isFlipped;
    }
    
    public int getCardType() {
        return this.cardType;
    }

    public BufferedImage getImage() {
        return this.image;
    }
    
}