import java.awt.*;
import java.util.Random;

/**
 * Confetti.java
 * May 22nd, 2024
 * Emilee Chen
 * Confetti object that is moves downwards
 */

public class Confetti {

    int x, y;
    double yy, vy;
    int r;
    Random rand = new Random();
    Color c;

    Confetti() {
        x = rand.nextInt(1500);//Creates a random space for the confetti to spawn
        y = 0; //Ensures the confetti starts at the top (and falls down)
        vy = 5.4;
        r = 15;
        c = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); //Creates a random colour for the confetti
    }

    void move() {
       yy += vy;
       y = (int)yy;
    }

}