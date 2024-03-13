package Main;

import Background.BackgroundManager;
import Background.TileManager;
import Entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    private long time;
    private boolean hasFinished;
    private DataBaseManager dataBaseManager = new DataBaseManager();

    //SETARILE FERESTREI
    final int originalTileSize = 64;    //64x64 tile
    public final int tileSize = originalTileSize;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;    //1024 pixeli
    public final int screenHeight = tileSize * maxScreenRow;   //768 pixeli

    private static BufferedImage backdrop = null;
    int FPS = 60;

    // SYSTEM
    BackgroundManager bgM = new BackgroundManager(this);
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this); // ...(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public AssetSetter aSetter = new AssetSetter(this);

    // PLAYER     NPC     MONSTER
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[2];
    public Entity monster[] = new Entity[5];
    ArrayList<Entity> entityList = new ArrayList<>();



    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    //public final int dialogueState = 4;



//    //Pozitia standard a player-ului
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setMonster();             //pt MONSTER
        aSetter.setNPC();
        gameState = titleState;
    }

    public TileManager getTileManager() {
        return tileM;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Aici cream Game Loop-ul
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;   // 0.01666 secunde
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        if(gameState == titleState) {
            time = System.currentTimeMillis();
            hasFinished = false;
        }

        if(gameState == playState) {
            // PLAYER
            player.update();

            //NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }

            // MONSTER
            for(int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if(monster[i].alive == true && monster[i].dying == false)
                        monster[i].update();
                    if(monster[i].alive == false) {
                        monster[i] = null;
                        this.gameState = this.gameOverState;
                    }
                }
            }
        }

        if(gameState == pauseState){
            //nothing
        }

        if(gameState == gameOverState && !hasFinished) {
            hasFinished = true;
            time = System.currentTimeMillis() - time;
            DataBaseManager.Insert("player", time/1000);
        }


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);

        }
        //OTHERS
        else {
            bgM.draw(g2);
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.y, e2.y);
                    return result;
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);
        }
        g2.dispose();
    }
}
