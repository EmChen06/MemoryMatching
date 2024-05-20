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

    public boolean flip(Card c) {
        if (this.cardType == c.getCardType()) {
            return true;
        } else {
            return false;
        }
    }
    
    public int getCardType() {
        return this.cardType;
    }

    public BufferedImage getImage() {
        return this.image;
    }
    
}