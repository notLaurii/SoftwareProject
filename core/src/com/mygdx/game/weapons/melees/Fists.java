package com.mygdx.game.weapons.melees;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.world.GameMap.entitiesToAdd;

public class
Fists extends Melee {
    private float x;
    private float y;
    private GameMap map;
    private Entity wielder;
    private float cooldown=1;

    public Fists(GameMap map, Entity wielder) {
        super(wielder.getX(),wielder.getY(), map,0,wielder,10, 5,1);
        System.out.println(wielder);
    }
}
