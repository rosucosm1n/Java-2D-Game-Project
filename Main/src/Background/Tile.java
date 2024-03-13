package Background;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Tile {

    public BufferedImage image;
//    public boolean collision = false;

    public int x, y;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getX(int x) {
        return x;
    }

    public int getY(int y) {
        return y;
    }

}