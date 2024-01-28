package com.mygdx.game.weapons.rangedweapons;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.world.GameMap.entities;
import static com.mygdx.game.world.GameMap.entitiesToAdd;

public class Boomerang extends RangedWeapon{

    public Boomerang(float x, float y, GameMap map, Entity wielder) {
        super(x, y, 10, map, wielder, 1);
    }
    public void attack(float playerDamage) {
        if(this.getCanAttack()) {
            BoomerangProjectile boomerangProjectile = new BoomerangProjectile(getMap(), getWielder(), getAttackDamage());
            entitiesToAdd.add(boomerangProjectile);
            this.startAttackCooldown(this.getMaxCooldown());
        }
    }
}
