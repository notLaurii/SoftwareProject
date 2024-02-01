package com.mygdx.game.management;

import com.mygdx.game.entities.Entity;

import static com.mygdx.game.entities.EntityType.SLIME;
import static com.mygdx.game.world.GameMap.entities;

public class LevelManager {
    private static int level;
    public static boolean noEnemiesLeft() {

        for(Entity entity : entities)
            if (entity.getType()==SLIME)
                return false;
        return true;
    }

    public static int checkLevel() {
        if(noEnemiesLeft()) {
           level+=1;
        }
        return level;
    }
}
