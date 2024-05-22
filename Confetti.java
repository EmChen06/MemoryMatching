import java.awt.*;
import java.util.Random;

public class Confetti {

    int x, y;
    double yy, vy;
    int r;
    Random rand = new Random();
    Color c;

    Confetti() {
        x = rand.nextInt(1500);
        y = 0;
        vy = 5.4;
        r = 15;
        c = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    void move() {
       yy += vy;
       y = (int)yy;
    }

}