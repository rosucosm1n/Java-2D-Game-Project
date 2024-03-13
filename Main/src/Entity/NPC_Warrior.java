package Entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Warrior extends Entity {

    public NPC_Warrior(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
    }


    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_idle1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_idle2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_idle1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_idle2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player1/npc/npc_right2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(75)+1;      // pick up a number from 1 to 75

//            if(i <= 25) {
//                direction = "up";
//            }
//            if(i > 25 && i <= 50) {
//                direction = "down";
//            }
            if(i <= 50) {
                direction = "left";
            }
            if(i > 50 && i <= 75) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

}
