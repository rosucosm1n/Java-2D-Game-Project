package Monster;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class MON_Plant extends Entity {
    GamePanel gp;

    public MON_Plant(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 2;
        String name = "Plant";
        //x = 400;
        //y = 512;
        speed = 1;
        maxLife = 4;
        life = maxLife;

        getImage();
    }

    public void getImage () {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player1/enemy/enemy1_idle.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player1/enemy/enemy1_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player1/enemy/enemy1_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player1/enemy/enemy1_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player1/enemy/enemy1_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(75)+1;      // pick up a number from 1 to 75

            if(i <= 50) {
                direction = "right";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }

            actionLockCounter = 0;
        }
    }
}