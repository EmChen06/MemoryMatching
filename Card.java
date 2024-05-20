import java.awt.image.BufferedImage;

public class Card {

    int cardType;
    BufferedImage image;
    boolean isFlipped;

    Card(int cT, BufferedImage img, boolean flipped) {
        this.cardType = cT;
        this.image = img;
        this.isFlipped = flipped;
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