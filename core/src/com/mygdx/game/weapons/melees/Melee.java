package com.mygdx.game.weapons.melees;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.management.LevelManager;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public abstract class Melee extends Weapon {
    protected float rangeX;
    protected float rangeY;
    public Melee(float x, float y, GameMap map, float weaponDamage, Entity wielder, float rangeX, float rangeY, float maxCooldown) {
        super(x, y, weaponDamage, map, wielder, maxCooldown);
        this.rangeX=rangeX;
        this.rangeY=rangeY;
    }

    public void attack(float damage) {
        ArrayList<Entity> entities = levelManager.getEntities();
        for(Entity entity : entities)
            if(entity != wielder && wielder.isEntityInRange(entity, rangeX, rangeY))
                entity.takeDamage(damage);
        startAttackCooldown(maxCooldown);
    }
}
