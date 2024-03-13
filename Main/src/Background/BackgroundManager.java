package Background;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BackgroundManager {

    GamePanel gp;
    Tile[] background;

    public BackgroundManager(GamePanel gp) {
        this.gp = gp;
        background = new Tile[3];

        getBackgroundImage();
    }

    public void getBackgroundImage() {

        try {
            background[0] = new Tile();
            background[0].image = ImageIO.read(getClass().getResourceAsStream("/player1/backgrounds/battleground1.png"));
            background[1] = new Tile();
            background[1].image = ImageIO.read(getClass().getResourceAsStream("/player1/backgrounds/battleground2.png"));
            background[2] = new Tile();
            background[2].image = ImageIO.read(getClass().getResourceAsStream("/player1/backgrounds/battleground3.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {


        if(gp.ui.commandNum == 0)
            g2.drawImage(background[0].image,0, 0, gp.tileSize*16, gp.tileSize*12, null);
        if(gp.ui.commandNum == 1)
            g2.drawImage(background[1].image,0, 0, gp.tileSize*16, gp.tileSize*12, null);
        if(gp.ui.commandNum == 2)
            g2.drawImage(background[2].image,0, 0, gp.tileSize*16, gp.tileSize*12, null);
    }
}
