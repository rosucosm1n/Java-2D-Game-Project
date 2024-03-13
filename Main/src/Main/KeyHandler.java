package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;   //pt GS

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public KeyHandler(GamePanel gp) {   //pt GS
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();  //returneaza nr tastei apasate

        // TITLE STATE
        if(gp.gameState == gp.titleState) {

            if(gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
//                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 1) {
                        // add later load game
                    }
                    if(gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            }
            else if(gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0) {
                        // lv 1 specific stuff


                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 1) {
                        // lv 2 specific stuff

                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 2) {
                        // lv 3 specific stuff

                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 0;
                        //
                    }
                }
            }


        }

        // PLAY STATE
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }



        // PAUSE STATE
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState)
                gp.gameState = gp.pauseState;
            else if (gp.gameState == gp.pauseState)
                gp.gameState = gp.playState;
        }

        // DIALOGUE STATE
        // to be continued...
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
