package Main;

import Entity.Entity;

import java.io.IOException;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public int checkBorder(int x) {
        if (x > -20 && x < 940)
            return 1;
        return 0;
    }

    public int checkGround(int y) {
        if (y < 514)
            return 1;
        return 0;
    }


//    public int checkEntity(Entity entity, Entity[] target) {                //NU MERGE CV
//        int index = 999;
//
//        if (entity.collisionOn == true) {       //in plus ca sa mearga
//            for (int i = 0; i < target.length; i++) {
//                if (target[i] != null) {
//                    // Get entity's solid area position
//                    entity.solidArea.x = entity.x + entity.solidArea.x;
//                    entity.solidArea.y = entity.y + entity.solidArea.y;
//                    // Get the target's solid area position
//                    target[i].solidArea.x = target[i].x + target[i].solidArea.x;
//                    target[i].solidArea.y = target[i].y + target[i].solidArea.y;
//
//                    switch (entity.direction) {
//                        case "up":
//                            entity.solidArea.y -= entity.speed;
//                            break;
//                        case "down":
//                            entity.solidArea.y += entity.speed;
//                            break;
//                        case "left":
//                            entity.solidArea.x -= entity.speed;
//                            break;
//                        case "right":
//                            entity.solidArea.x += entity.speed;
//                            break;
//                    }
//                }
//
//                if (entity.solidArea.intersects(target[i].solidArea)) {
//                    if (target[i] != entity) {
//                        entity.collisionOn = true;
//                        index = i;
//                    }
//                }
//
//                entity.solidArea.x = entity.solidAreaDefaultX;
//                entity.solidArea.y = entity.solidAreaDefaultY;
//                target[i].solidArea.x = target[i].solidAreaDefaultX;
//                target[i].solidArea.y = target[i].solidAreaDefaultY;
//            }
//        }           //in plus
//        return index;
//    }


    public boolean checkPlayer(Entity entity) {                    //merge perfect asta

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.x + entity.solidArea.x;
        entity.solidArea.y = entity.y + entity.solidArea.y;
        // Get the target's solid area position
        gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }




//
//
//    //---------------------------------------------------------------------------------
//    Cu aceasta metoda functioneaza atacul si coliziunea cumva, doar ca ramane player-ul blocat in inamic/npc
    // NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        if (entity.collisionOn == true) {
            for (int i = 0; i < target.length; i++) {
                if (target[i] != null) {
                    // Get entity's solid area position
                    entity.solidArea.x = entity.x + entity.solidArea.x;
                    entity.solidArea.y = entity.y + entity.solidArea.y;
                    // Get the target's solid area position
                    target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                    target[i].solidArea.y = target[i].y + target[i].solidArea.y;

                    switch (entity.direction) {
                        case "up":
                            entity.solidArea.y -= entity.speed;
                            if (!entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = false;
                                index = i;
                            }
                            break;
                        case "down":
                            entity.solidArea.y += entity.speed;
                            if (!entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = false;
                                index = i;
                            }
                            break;
                        case "left":
                            entity.solidArea.x -= entity.speed;
                            if (!entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = false;
                                index = i;
                            }
                            break;
                        case "right":
                            entity.solidArea.x += entity.speed;
                            if (!entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = false;
                                index = i;
                            }
                            break;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                if (target[i] != null) {
                    target[i].solidArea.x = target[i].solidAreaDefaultX;
                    target[i].solidArea.y = target[i].solidAreaDefaultY;
                }
            }
        } else {
            for (int i = 0; i < target.length; i++) {
                if (target[i] != null) {
                    // Get entity's solid area position
                    entity.solidArea.x = entity.x + entity.solidArea.x;
                    entity.solidArea.y = entity.y + entity.solidArea.y;
                    // Get the target's solid area position
                    target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                    target[i].solidArea.y = target[i].y + target[i].solidArea.y;

                    switch (entity.direction) {
                        case "up":
                            entity.solidArea.y -= entity.speed;
                            if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                            }
                            break;
                        case "down":
                            entity.solidArea.y += entity.speed;
                            if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                            }
                            break;
                        case "left":
                            entity.solidArea.x -= entity.speed;
                            if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                            }
                            break;
                        case "right":
                            entity.solidArea.x += entity.speed;
                            if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                            }
                            break;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                if (target[i] != null) {
                    target[i].solidArea.x = target[i].solidAreaDefaultX;
                    target[i].solidArea.y = target[i].solidAreaDefaultY;
                }
            }
        }

        return index;
    }
}

//    public void checkPlayer(Entity entity) {
//        if(entity.collisionOn == true) {
//            // Get entity's solid area position
//            entity.solidArea.x = entity.x + entity.solidArea.x;
//            entity.solidArea.y = entity.y + entity.solidArea.y;
//            // Get the target's solid area position
//            gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
//            gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
//
//            switch (entity.direction) {
//                case "up":
//                    entity.solidArea.y -= entity.speed;
//                    if (! entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = false;
//                    }
//                    break;
//                case "down":
//                    entity.solidArea.y += entity.speed;
//                    if (! entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = false;
//                    }
//                    break;
//                case "left":
//                    entity.solidArea.x -= entity.speed;
//                    if (! entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = false;
//                    }
//                    break;
//                case "right":
//                    entity.solidArea.x += entity.speed;
//                    if (! entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = false;
//                    }
//                    break;
//            }
//            entity.solidArea.x = entity.solidAreaDefaultX;
//            entity.solidArea.y = entity.solidAreaDefaultY;
//
//            if (gp.player != null) {
//                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
//                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
//            }
//        }
//        else {
//            // Get entity's solid area position
//            entity.solidArea.x = entity.x + entity.solidArea.x;
//            entity.solidArea.y = entity.y + entity.solidArea.y;
//            // Get the target's solid area position
//            gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
//            gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
//
//            switch (entity.direction) {
//                case "up":
//                    entity.solidArea.y -= entity.speed;
//                    if (entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = true;
//                    }
//                    break;
//                case "down":
//                    entity.solidArea.y += entity.speed;
//                    if (entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = true;
//                    }
//                    break;
//                case "left":
//                    entity.solidArea.x -= entity.speed;
//                    if (entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = true;
//                    }
//                    break;
//                case "right":
//                    entity.solidArea.x += entity.speed;
//                    if (entity.solidArea.intersects(gp.player.solidArea)) {
//                        entity.collisionOn = true;
//                    }
//                    break;
//            }
//            entity.solidArea.x = entity.solidAreaDefaultX;
//            entity.solidArea.y = entity.solidAreaDefaultY;
//
//            if (gp.player != null) {
//                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
//                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
//            }
//        }
//    }
//}
