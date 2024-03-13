package Background;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];


    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
    }

    public void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/player1/tiles/gnd1.png"));
            //setup(0, "/player1/tiles/gnd1", true);
            //setup(1, "/player1/tiles/gnd2", false);
            //            tile[0].collision = true;
            tile[0].setY(640);

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/player1/tiles/gnd2.png"));
           // tile[1].collision = true;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        for(int i = 0; i < 1024; i += 64) {
            g2.drawImage(tile[0].image, i, 640, gp.tileSize, gp.tileSize, null);
            g2.drawImage(tile[1].image, i, 704, gp.tileSize, gp.tileSize, null);
        }
    }
}
