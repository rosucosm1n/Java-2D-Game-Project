package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, death1, death2;
    public BufferedImage image, image2, image3;
    public BufferedImage attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);


    // STATE
    public int x, y;
    public String direction = "down";
    public boolean collisionOn = false;
    public int spriteNum = 1;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;

    //CHARACTER ATTRIBUTES
    public int speed;
    public String name;
    public int maxLife;
    public int life;
    public int type; // 0 = player, 1 = npc, 2 = monster



    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() { }

    public void damageReaction() { }

    public void update() {
        setAction();

        collisionOn = false;

        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if(collisionOn == false) {

            switch(direction) {
//                case "up":
//                    y -= speed;
//                    break;
//                case "down":
//                    y += speed;
//                    break;
                case "left":
                    if(x - speed > -20)
                    x -= speed;
                    else
                        direction = "up";
                    break;
                case "right":
                    x += speed;
                    break;
            }
        }

            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) { image = up1; }
                if(spriteNum == 2) { image = up2; } break;
            case "down":
                if(spriteNum == 1 ) { image = down1; }
                if(spriteNum == 2 ) { image = down2; } break;
            case "left":
                if(spriteNum == 1) { image = left1; }
                if(spriteNum == 2) { image = left2; } break;
            case "right":
                if(spriteNum == 1) { image = right1; }
                if(spriteNum == 2) { image = right2; } break;
        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        }
        if(dying == true) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, x, y, gp.tileSize*2, gp.tileSize*2, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;
        int i = 5;

        if(dyingCounter <= i) { changeAlpha(g2, 1f); }
        if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
        if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0f); }
        if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1f); }
        if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0f); }
        if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
        if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
        if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f); }
        if(dyingCounter > i*8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

}
