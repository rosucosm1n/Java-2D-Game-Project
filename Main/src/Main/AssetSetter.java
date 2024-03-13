package Main;

import Entity.NPC_Warrior;
import Monster.MON_Giant;
import Monster.MON_Plant;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPC() {

        gp.npc[0] = new NPC_Warrior(gp);
        gp.npc[0].x = 28;
        gp.npc[0].y = 512;
    }

    public void setMonster() {
        gp.monster[0] = new MON_Plant(gp);
        gp.monster[0].x = 550;
        gp.monster[0].y = 512;

//        gp.monster[1] = new MON_Giant(gp);
//        gp.monster[1].x = 580;
//        gp.monster[1].y = 512;
    }

}
