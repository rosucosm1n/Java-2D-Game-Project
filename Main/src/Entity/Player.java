package Entity;

import Main.CollisionChecker;
import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Key;

public class Player extends Entity {

    KeyHandler keyH;
    //public final int screenX;
    //public final int screenY;

    public CollisionChecker cChecker;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        cChecker = new CollisionChecker(gp);

        solidArea = new Rectangle(0, 0, 64, 64);
        solidArea.x = 0;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 64; //32;
        solidArea.height = 64; //32;

        attackArea.width = 50;
        attackArea.height = 50;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        x = 128;
        y = 512;
        speed = 5;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_idle1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_idle2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_idle1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_idle2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage() {
        try {
            attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_left_attack1.png"));
            attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_left_attack2.png"));
            attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_right_attack1.png"));
            attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player1/hero1/hero1_right_attack2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(attacking == true)
            attacking();
        else if (collisionOn == false) {
            if (keyH.upPressed == true || keyH.downPressed == true ||
                    keyH.leftPressed == true || keyH.rightPressed == true) { // || keyH.enterPressed == true) {

                if (keyH.upPressed == true) {
                    direction = "up";
                    y = y - speed;
                } else if (keyH.downPressed == true && cChecker.checkGround(y + speed) == 1) {
                    direction = "down";
                    y = y + speed;
                } else if (keyH.leftPressed == true && cChecker.checkBorder(x - speed) == 1) {
                    direction = "left";
                    x = x - speed;
                } else if (keyH.rightPressed == true && cChecker.checkBorder(x + speed) == 1) {
                    direction = "right";
                    x = x + speed;
                }
//                    else if (gp.keyH.enterPressed == true)
//                        attacking = true;
                else
                    direction = "up";
            }
        }

        //Check NPC Collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        //Check MONSTER Collision
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);


        //collisionOn = false;
//        if(collisionOn == false) {
//            switch(direction) {
//                case "up": y = y - speed;
//                    break;
//                case "down": y = y + speed;
//                    break;
//                case "left": x = x - speed;
//                    break;
//                case "right": x = x + speed;
//                    break;
//            }
//        }
        gp.keyH.enterPressed = false;

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // This needs to be outside of key if statement
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(life <= 0 ) {
            gp.gameState = gp.gameOverState;
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 10) {
            spriteNum = 2;

            //  Save the current x, y, solidArea
            int currentX = x;
            int currentY = y;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //  Adjust player's x/y for the attackArea
            switch(direction) {
                case "up": y -= attackArea.height; break;
                case "down": y += attackArea.height; break;
                case "left": x -= attackArea.width; break;
                case "right": x += attackArea.width; break;
            }

            //  attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //  Check monster collision with the updated X, Y and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //  After checking collision, restore the original data
            x = currentX;
            y = currentY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 10) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void interactNPC(int i) {

        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                //gp.gameState = gp.dialogueState;          //  pt implementare dialog
                //gp.npc[i].speak;
            } else {
                attacking = true;
            }
        }
    }


    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if(gp.monster[i].invincible == false) {
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) { image = up1; }
                if(spriteNum == 2) {  image = up2;  }
                break;
            case "down":
                if(spriteNum == 1 ) { image = down1; }
                if(spriteNum == 2) { image = down2; }
                break;
            case "left":
                if(attacking == false) {
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2) { image = left2; }
                }
                if(attacking == true) {
                    if(spriteNum == 1) { image = attackLeft1; }
                    if(spriteNum == 2) { image = attackLeft2; }
                }
                break;
            case "right":
                if(attacking == false) {
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2) { image = right2; }
                }
                if (attacking == true) {
                    if(spriteNum == 1) { image = attackRight1; }
                    if(spriteNum == 2) { image = attackRight2; }
                }
                break;
        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // Reset alpha (Character Opacity)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//        //DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
