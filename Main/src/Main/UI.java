package Main;

import Objects.OBJ_Heart;

import Entity.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font alagard;
    BufferedImage full_heart, half_heart, empty_heart;
    public boolean messageOn = false;
    public String message = "";

    // FOR MENU
    public int commandNum = 0;
    public int titleScreenState = 0;    //  0: the first lvl, 1: second lvl, 2: third lvl

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/player1/font/alagard.ttf");
            alagard = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECTS
        Entity heart = OBJ_Heart.getInstance(gp);        // Singleton call
        full_heart = heart.image;
        half_heart = heart.image2;
        empty_heart = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(alagard);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();

        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
//        if(gp.gameState == gp.dialogueState) {
//            drawDialogueScreen();
//        }

        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }

    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // DRAW MAX LIFE
        while(i < gp.player.maxLife/2) {
            g2.drawImage(empty_heart, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // DRAW CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(half_heart, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(full_heart, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Emperors' Quest";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // CHARACTER IMAGE
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1) {
            // LEVEL SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select Level";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Level 1";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Level 2";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Level 3";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "Game Over";
        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        // Main text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
    }



            // DIALOGUE STUFF  - not implemented yet
//    public void drawDialogueScreen() {
//        // WINDOW
//        int x = gp.tileSize*2;
//        int y = gp.tileSize/2;
//        int width = gp.screenWidth - (gp.tileSize*4);
//        int height = gp.tileSize*5;
//
//        drawSubWindow(x, y, width, height);
//    }
//
//    public void drawSubWindow(int x, int y, int width, int height) {
//
//        Color c = new Color(0, 0, 0);
//        g2.setColor(c);
//        g2.fillRoundRect(x, y, width, height, 35, 35);
//
//    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
