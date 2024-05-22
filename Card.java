import java.awt.image.BufferedImage;

/**
 * Card.java
 * May 22nd, 2024
 * Emilee Chen
 * Card object to be matched and flipped over
 */

public class Card {

    private int cardType; //the identifier
    private BufferedImage image; //the image associated with the cardType
    private boolean isFlipped; //If it is currently flipped
    private boolean isMatched; //Is it matched?

    Card(int cT, BufferedImage img, boolean flipped, boolean matched) {
        this.cardType = cT;
        this.image = img;
        this.isFlipped = flipped;
        this.isMatched = matched;
    }

    /**
     * Changes if it is matched or not
     * @param matched   true or false
     */
    public void setMatched(boolean matched) {
        this.isMatched = matched;
    }

    /**
     * Returns the boolean isMatched
     * @return  isMatched, true or false
     */
    public boolean getMatched() {
        return this.isMatched;
    }

    /**
     * Changes if it is flipped or not
     * @param flip  true or false
     */
    public void setFlipped(boolean flip) {
        this.isFlipped = flip;
    }

    /**
     * Returns the boolean isFlipped
     * @return  isFlipped, true or false
     */
    public boolean getFlipped() {
        return this.isFlipped;
    }
    
    /**
     * Returns the identifier for matching
     * @return  int value of the card
     */
    public int getCardType() {
        return this.cardType;
    }

    /**
     * Returns the BufferedImage associated with the card
     * @return  BufferedImage image
    */
    public BufferedImage getImage() {
        return this.image;
    }
    
}